#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <stdbool.h>
#include <dirent.h>
#include <errno.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <semaphore.h>
#include "fnc.h"

volatile sig_atomic_t time_to_terminate = 0;
volatile sig_atomic_t end_child = 0;

void handle_signal(int signo)
{
    if (signo == SIGUSR1)
    {
        write(STDOUT_FILENO, "\nALERT: New file detected.\n", 27);
    }
    else if (signo == SIGINT)
    {
        // write(STDOUT_FILENO, "\nOrder to terminate.\n", 22);

        time_to_terminate = 1;
    }
}

// VERSION: 9
// 07/06/2024
// Group: 2DG2
int main(int argc, char *argv[])
{
    // GETS CONFIG INFORMATION FROM PARAMETERS
    Configuration config;
    if (argc < 5)
    {
        fprintf(stderr, "Usage: %s <input_dir> <output_dir> <num_workers> <time_interval>\n", argv[0]);
        return 1;
    }
    sscanf(argv[1], "%s", config.input_directory);
    sscanf(argv[2], "%s", config.output_directory);
    sscanf(argv[3], "%d", &config.num_worker_children);
    sscanf(argv[4], "%d", &config.time_interval);

    pid_t child_p[config.num_worker_children + 1];
    struct sigaction act;
    int fd, status;
    sem_t *sem1, *sem2, *sem3;
    BufferCircular *shared_memory;
    //==================================================================================

    // CREATES THE OUTPUT DIRECTORY IF IT DOESN'T EXIST
    DIR *dir_output;
    dir_output = opendir(config.output_directory);
    if (!dir_output)
    {
        printf("The %s directory doesn't exist!\nCreating the directory!\n", config.output_directory);
        if (errno == ENOENT)
        {
            // If the output directory doesn't exist, it will be created
            if (mkdir(config.output_directory, 0777) == -1)
            {
                perror("mkdir");
                exit(EXIT_FAILURE);
            }

            dir_output = opendir(config.output_directory);
            if (!dir_output)
            {
                perror("opendir");
                exit(EXIT_FAILURE);
            }
        }
        else
        {
            perror("opendir");
            exit(EXIT_FAILURE);
        }
    }
    //===================================================================================

    // SETS UP SIGNALS TO BE HANDLED
    memset(&act, 0, sizeof(struct sigaction));
    act.sa_handler = handle_signal;
    act.sa_flags = SA_SIGINFO /*| SA_RESTART*/;

    if (sigaction(SIGUSR1, &act, NULL) == -1 || sigaction(SIGINT, &act, NULL) == -1)
    {
        perror("sigaction");
        return 1;
    }
    //==================================================================================

    // CREATES THE SHARED MEMORY SPACE AND WRITES THE INFORMATION ON THE ARRAY ON THE SHARED MEMORY
    if ((fd = shm_open("/shm_us2001b", O_CREAT | O_RDWR, S_IRUSR | S_IWUSR)) == -1)
    {
        perror("shm_open");
        exit(1);
    }
    int size = sizeof(BufferCircular);
    if (ftruncate(fd, size) == -1)
    {
        perror("ftruncate");
        close(fd);
        exit(2);
    }
    shared_memory = mmap(0, size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (shared_memory == MAP_FAILED) // Corrected mmap
    {
        perror("mmap");
        close(fd);
        exit(3);
    }
    for (int l = 0; l < LENGTH_BUFFER; l++)
    {
        shared_memory->array[l] = 0;
    }
    shared_memory->head = 0;
    shared_memory->tail = 0;
    shared_memory->size = 0;
    //==================================================================================

    // CREATES SEMAPHORES
    if (((sem1 = sem_open("/sem1", O_CREAT, 0644, 0)) == SEM_FAILED) || ((sem2 = sem_open("/sem2", O_CREAT, 0644, 1)) == SEM_FAILED) || ((sem3 = sem_open("/sem3", O_CREAT, 0644, 0)) == SEM_FAILED))
    {
        perror("sem_open");
        exit(4);
    }
    //==================================================================================

    // CREATES CHILD THAT WILL CHECK IF THERE ARE NEW FILES
    child_p[0] = fork();
    if (child_p[0] == 0)
    {
        // CHILD THAT WILL PERIODICALLY MONITOR THE INPUT DIRECTORY
        int newFileLine = 0;  // last line refering to a file
        int returnNumber = 0; // line refering to a file after input directory check-in
        while (!time_to_terminate)
        {
            returnNumber = check_files_child(config.input_directory);
            if (returnNumber > newFileLine)
            {
                newFileLine = returnNumber;
                printf("» New file found «\n");
                if ((sem_post(sem3) == -1) && !time_to_terminate)
                {
                    perror("sem_post");
                    exit(5);
                }
            }
            else
            {
                printf("» New file not found «\n");
            }
            sleep(config.time_interval);
        }
        if (munmap(shared_memory, size) == -1)
        {
            perror("munmap");
            close(fd);
            exit(6);
        }
        if (close(fd) == -1)
        {
            perror("close");
            exit(7);
        }
        exit(10);
    }
    //==================================================================================

    // CHILD WORKERS
    for (int i = 1; i <= config.num_worker_children; i++)
    {
        child_p[i] = fork();
        if (child_p[i] == 0)
        {
            while (!time_to_terminate)
            {
                if ((sem_wait(sem1) == -1) && !time_to_terminate)
                {
                    perror("sem_wait 11");
                    exit(6);
                }
                if ((sem_wait(sem2) == -1) && !time_to_terminate)
                {
                    perror("sem_wait 22");
                    exit(6);
                }
                    if (!time_to_terminate)
                    {
                        copy_files(shared_memory->array[shared_memory->tail], config.input_directory, config.output_directory); // Copies the files from the input directoty to the ouput directory
                        printf("»»» New candidate processed: %d\n", shared_memory->array[shared_memory->tail]);
                        shared_memory->tail = (shared_memory->tail + 1) % LENGTH_BUFFER;
                        shared_memory->size--;
                    }   
                if ((sem_post(sem2) == -1) && !time_to_terminate)
                {
                    perror("sem_post");
                    exit(5);
                }
            }
            if (munmap(shared_memory, size) == -1)
            {
                perror("munmap");
                exit(7);
            }
            if (close(fd) == -1)
            {
                perror("close");
                exit(8);
            }
            exit(10 + i);
        }
    }
    //==================================================================================
    int lastCandidate = 0, copiedCandDiff = 0, reported_child = 0, delegated_cand = 0, diff, cand = 0;

    while (!time_to_terminate)
    {
        if ((sem_wait(sem3) == -1) && !time_to_terminate)
        {
            perror("sem_wait 333");
            exit(6);
        }
        get_new_candidates(config.input_directory, &lastCandidate, &copiedCandDiff);
        diff = copiedCandDiff;
        while (diff > 0)
        {

            for (int i = 0; i < copiedCandDiff; i++)
            {
                if (shared_memory->size != LENGTH_BUFFER)
                {
                    cand = (lastCandidate - (diff - 1));
                    shared_memory->array[shared_memory->head] = cand;
                    shared_memory->head = (shared_memory->head + 1) % LENGTH_BUFFER;
                    printf("»» New candidate delegated: %d\n", cand);

                    if ((sem_wait(sem2) == -1) && !time_to_terminate)
                    {
                        perror("sem_wait 2222");
                        exit(6);
                    }
                    shared_memory->size++;
                    if ((sem_post(sem2) == -1) && !time_to_terminate)
                    {
                        perror("sem_wait 22222");
                        exit(5);
                    }
                    delegated_cand++;
                    diff--;
                }
                else
                {
                    diff = copiedCandDiff - delegated_cand;
                    break;
                }
            }
            for (int i = 0; i < delegated_cand; i++)
            {
                if ((sem_post(sem1) == -1) && !time_to_terminate)
                {
                    perror("sem_post");
                    exit(5);
                }
            }
            delegated_cand = 0;
            copiedCandDiff = diff;
        }
    }
    //==================================================================================

    // GENERATE REPORT
    generate_report(reported_child, config.output_directory);
    //==================================================================================

    // REMOVE THE SHARE MEMORY AND THE SEMAPHORES AND CLOSE CHILDREN
    if (close(fd) == -1)
    {
        perror("close");
        exit(8);
    }
    if (munmap(shared_memory, size) == -1)
    {
        perror("munmap");
        exit(7);
    }
    if ((shm_unlink("/shm_us2001b") == -1) || (sem_unlink("/sem1") == -1) || (sem_unlink("/sem2") == -1) || (sem_unlink("/sem3") == -1))
    {
        perror("unlink");
        exit(9);
    }

    for (int j = 0; j < (config.num_worker_children + 1); j++)
    {
        waitpid(child_p[j], &status, 0);
        if (WIFEXITED(status))
        {
            printf("[Child %d] - Exit Status:%d \n", child_p[j], WEXITSTATUS(status));
        }
    }

    //===================================================================================

    return 0;
}
