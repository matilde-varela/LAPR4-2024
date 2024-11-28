#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <regex.h>
#include <string.h>
void get_new_candidates(char *directory, int *current_number, int *total_max_diff)
{
    DIR *dir;
    struct dirent *entry;
    int last_number = *current_number; // Variable to store the last registered number
    int max_diff = 0; // Initialize max_diff for the current iteration

    // Open the directory
    dir = opendir(directory);
    if (!dir)
    {
        perror("opendir");
        exit(EXIT_FAILURE);
    }

    // Iterate over the directory entries
    while ((entry = readdir(dir)) != NULL)
    {
        // Check if the entry is a regular file
        if (entry->d_type == DT_REG)
        {
            // Extract the number from the filename
            char *num_str = strtok(entry->d_name, "-");
            if (num_str != NULL)
            {
                int num = atoi(num_str);

                // Check if this number is greater than the current max number
                if (num > *current_number)
                {
                    // Update max_diff and current_number
                    max_diff = num - last_number;
                    *current_number = num;
                }
            }
        }
    }
    closedir(dir);

    // Add max_diff to total_max_diff
    *total_max_diff += max_diff;
}