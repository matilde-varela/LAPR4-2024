#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <regex.h>
#include <string.h>
void get_new_candidates(char *directory, int *current_number, int *max_diff)
{
    DIR *dir;
    struct dirent *entry;
    int last_number = *current_number; // Variable to store the last registered number
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
                    *max_diff = num - last_number;
                    *current_number = num;
                }
            }
        }
    }

    // Close the directory
    closedir(dir);
}

// void get_new_candidates(char *directory, int *current_number, int *max_diff)
// {
//     DIR *dir;
//     struct dirent *entry;
//     regex_t regex;
//     int reti;
//     int last_number = *current_number; // Variable to store the last registered number
//     printf("1\n");
//     // Compile the regular expression pattern
//     reti = regcomp(&regex, "^([0-9]+)-[^\\.]+\\..+$", REG_EXTENDED);
//     if (reti)
//     {
//         fprintf(stderr, "Could not compile regex\n");
//         exit(EXIT_FAILURE);
//     }

//     printf("2\n");
//     // Open the directory
//     dir = opendir(directory);
//     if (!dir)
//     {
//         perror("opendir");
//         exit(EXIT_FAILURE);
//     }
//     printf("3\n");
//     // Iterate over the directory entries
//     while ((entry = readdir(dir)) != NULL)
//     {
//         // Match the filename with the regular expression
//         reti = regexec(&regex, entry->d_name, 0, NULL, 0);
//         printf("4\n");
//         if (!reti)
//         {
//             // Extract the number from the filename
//             char *num_str = strtok(entry->d_name, "-");
//             int num = atoi(num_str);
//             printf("5\n");
//             // Check if this number is greater than the current max number
//             if (num > *current_number)
//             {
//                 // Update max_diff and current_number
//                 *max_diff = num - last_number;
//                 *current_number = num;
//             }
//             printf("6\n");
//         }
//         else if (reti != REG_NOMATCH)
//         {
//             fprintf(stderr, "Regex match failed\n");
//             exit(EXIT_FAILURE);
//         }
//     }
//     printf("7\n");
//     // Close the directory
//     closedir(dir);

//     // Free the compiled regex
//     regfree(&regex);
// }