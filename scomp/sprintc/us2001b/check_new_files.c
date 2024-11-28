#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>
#include <sys/stat.h>

int check_files_child(char *input_directory){
    DIR *dir;
    struct dirent *entry;
    int num_files = 0;

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

        num_files++;
    }

    closedir(dir);

    return num_files;

}