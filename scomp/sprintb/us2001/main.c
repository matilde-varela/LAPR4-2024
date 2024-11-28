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
    else if (signo == SIGUSR2)
    {
        end_child = 1;
    }
}

// VERSION: 7
// 04/05/2024
// Group: 2DG2
int main(int argc, char *argv[])
{
    // GETS CONFIG INFORMATION FROM PARAMETERS
    //==Work done by: Rita Barbosa======================================================
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
    //===================================================================================

    // CREATES THE OUTPUT DIRECTORY IF IT DOESN'T EXIST
    //==Work done by: Ana Guterres======================================================
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

    int i, j, n, status, candidate = -1;
    pid_t child_p[config.num_worker_children + 1];
    struct sigaction act;
    int fd[config.num_worker_children][2];
    int shared_p[2];

    // SETS UP SIGNALS TO BE HANDLED
    //==Work done by: Rita Barbosa======================================================
    memset(&act, 0, sizeof(struct sigaction));
    act.sa_handler = handle_signal;
    act.sa_flags = SA_SIGINFO /*| SA_RESTART*/;

    if (sigaction(SIGUSR1, &act, NULL) == -1 || sigaction(SIGINT, &act, NULL) == -1 || sigaction(SIGUSR2, &act, NULL) == -1)
    {
        perror("sigaction");
        return 1;
    }

    if (pipe(shared_p) == -1)
    {
        perror("pipe");
        return 1;
    }
    //===================================================================================

    // CHILD WORKERS WILL COPY THE FILES FROM THE INPUT TO THE OUTPUT DIRECTORY
    //==Work done by: Ana Guterres======================================================
    for (i = 0; i < config.num_worker_children; i++)
    {
        if (pipe(fd[i]) == -1)
        {
            perror("pipe");
            return 1;
        }

        child_p[i + 1] = fork();

        if (child_p[i + 1] == 0)
        {
            close(fd[i][1]);    // Close write end of pipe in child worker
            close(shared_p[0]); // Close read end of shared pipe in child worker
            while (!end_child)
            {
                // Read from pipe to get candidate value
                n = read(fd[i][0], &candidate, sizeof(candidate));
                if (candidate == -1)
                {
                    break;
                }
                if (n != sizeof(candidate))
                {
                    perror("Error: child worker couldn't read from pipe.");
                    exit(i + 1);
                }
                copy_files(candidate, config.input_directory, config.output_directory); // Copies the files from the input directoty to the ouput directory
                n = write(shared_p[1], &i, sizeof(i));                                  // Write to shared pipe to notify parent that work is done
                if (candidate == -1)
                {
                    break;
                }
                if (n != sizeof(i))
                {
                    perror("Error: child worker couldn't write to pipe.");
                    exit(i + 1);
                }
                candidate = -1;
            }
            close(fd[i][0]);    // Close read end of pipe in child
            close(shared_p[1]); // Close write end of shared pipe in child
            exit(i);
        }
        else if (child_p[i + 1] == -1)
        {
            perror("ERROR: Couldn't create child worker.");
            return 1;
        }
    }
    //===================================================================================

    // CHILD THAT WILL PERIODICALY VERIFY IF THERE IS ANY NEW FILES IN THE INPUT DIRECTORY
    //==Work done by: Matilde Varela======================================================
    child_p[0] = fork();
    if (child_p[0] == 0)
    {
        // CHILD THAT WILL PERIODICALLY MONITOR THE INPUT DIRECTORY
        int newFileLine = 0;     // last line refering to a file
        int returnNumber = 0;    // line refering to a file after input directory check-in
        int status;
        int newFilePipe[2];            // pipe between last sub-child and parent-child
        int newFilePipeMidChildren[2]; // pipe between subchildren

        if (chdir(config.input_directory) == -1)
        {
            perror("chdir");
            exit(1);
        }

        while (!time_to_terminate)
        {
            if (pipe(newFilePipe) == -1)
            {
                perror("pipe");
                exit(1);
            }

            if (pipe(newFilePipeMidChildren) == -1)
            {
                perror("pipe");
                exit(1);
            }
            for (i = 0; i < 2; ++i)
            {
                pid_t pid = fork();
                if (pid == 0)
                {
                    if (i == 0)
                    {
                        check_files_child_ls(newFilePipe, newFilePipeMidChildren);
                    }
                    else
                    {
                        check_files_child_wc(newFilePipe, newFilePipeMidChildren);
                    }
                    perror("execlp");
                    exit(1);
                }
            }

            close(newFilePipeMidChildren[0]); // monitoring child will not write or read on sub-children pipe
            close(newFilePipeMidChildren[1]);
            close(newFilePipe[1]); // monitoring child will not write on new file pipe

            for (i = 0; i < 2; ++i) // waits for the termination of all children
            {
                wait(&status);
                if (!WIFEXITED(status) && WEXITSTATUS(status) != 0)
                {
                    printf("Something wrong happened with the new file sub-child process.\n");
                }
            }
            n = read(newFilePipe[0], &returnNumber, sizeof(returnNumber)); // reads number of files currently on the input directory
            if (n < 0)
            {
                perror("Couldn't read all the information.");
                exit(1);
            }

            // returnNumber = (int)returnNumberInChar - '0';

            if (returnNumber > newFileLine)
            {
                newFileLine = returnNumber;
                printf("INFO: Monitor child going to signal.\n");
                kill(getppid(), SIGUSR1);
            }
            else
            {
                printf("INFO: No new files in input folder.\n");
            }
            close(newFilePipe[0]); // monitoring child will no longer read from new file pipe
            sleep(config.time_interval);
        }
        exit(0);
    }
    else if (child_p[0] == -1)
    {
        perror("fork");
        return 1;
    }
    //===================================================================================

    // PARENT WILL DELEGATE WORK TO THE CHILD WORKERS
    //==Work done by: Rita Barbosa======================================================
    int availableChild = 0, lastCandidate = 0, copiedCandDiff = 0, reported_child = 0;
    close(shared_p[i]); // Close write end of shared pipe in parent
    while (!time_to_terminate)
    {
        printf("\nINFO: waiting for signal\n");
        pause();

        if (time_to_terminate)
        {
            printf("\n>>>Time to terminate<<<\n");
            break;
        }
        else
        {
            get_new_candidates(config.input_directory, &lastCandidate, &copiedCandDiff);
            int childworker = 0;

            while (copiedCandDiff != 0)
            {
                if (childworker < config.num_worker_children)
                {
                    close(fd[childworker][0]);                                          // Close read end of pipe for parent
                    delegate_candidate(lastCandidate, copiedCandDiff, childworker, fd); // Pass the pipe for this child worker
                    copiedCandDiff--;
                }
                else
                {
                    // Close write end of shared pipe
                    available_child(&availableChild, shared_p[0]);                         // Wait for an available child notice
                    delegate_candidate(lastCandidate, copiedCandDiff, availableChild, fd); // Pass the pipe for the available child
                    copiedCandDiff--;
                }
                childworker++;
            }
        }

        // GENERATES REPORT OF THE PROCESSED CANDIDATES
        //==Work done by: José Afonso======================================================
        generate_report(reported_child, config.output_directory);
        reported_child = lastCandidate;
        //===================================================================================
    }
    //===================================================================================

    // CLOSES PIPES; TERMINATES CHILDREN AND WAITS FOR THEM TO BE FINISHED
    //==Work done by: Rita Barbosa======================================================
    close(shared_p[0]);
    candidate = -1;
    for (j = 0; j < config.num_worker_children + 1; j++)
    {
        if (j < config.num_worker_children)
        {
            close(fd[j][1]);
        }
        kill(child_p[j], SIGUSR2);
        printf("ALERT: Parent sent signal to terminate child [%d]\n", child_p[j]);
    }
    for (j = 0; j < (config.num_worker_children + 1); j++)
    {
        waitpid(child_p[j], &status, 0);
        if (WIFEXITED(status))
        {
            printf("[Child %d] - Exit Status:%d \n", child_p[j], WEXITSTATUS(status));
        }
    }
    //===================================================================================

    closedir(dir_output);

    return 0;
}
