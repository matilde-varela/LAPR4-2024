#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>
#include <sys/stat.h>

void copy_files(int n, char *input_directory, char *output_directory)
{
    DIR *dir;
    struct dirent *entry;
    char subdirectory[15];
    pid_t pid;

    sprintf(subdirectory, "candidate%d", n);

    char output_subdirectory[215];
    sprintf(output_subdirectory, "%s/%s", output_directory, subdirectory);
    mkdir(output_subdirectory, 0777);

    // Open the directory
    dir = opendir(input_directory);
    if (!dir)
    {
        perror("opendir");
        exit(EXIT_FAILURE);
    }

    // Iterate over the directory entries
    while ((entry = readdir(dir)) != NULL)
    {
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0)
        {
            continue;
        }

        if (entry->d_name[0] - '0' == n)
        {
            pid = fork();

            if (pid == 0)
            {
                char source_file_path[257];
                sprintf(source_file_path, "%s/%s", input_directory, entry->d_name);

                char dest_file_path[257];
                sprintf(dest_file_path, "%s/%s", output_directory, subdirectory);

                execlp("cp", "cp", source_file_path, dest_file_path, (char *)NULL);
                perror("execlp");
                exit(EXIT_FAILURE);
            }

            printf("«----» [ File %s copied to %s ] «----»\n", entry->d_name, subdirectory);
        }
    }

    // Close the directory
    closedir(dir);
}