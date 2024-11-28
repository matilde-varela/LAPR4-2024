#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <dirent.h>

FILE *open_report_file(char *output_directory)
{
    char reportFileName[200];
    time_t rawtime;
    FILE *reportFile;

    time(&rawtime);
    snprintf(reportFileName, sizeof(reportFileName), "%s/candidates-report-%ld.txt", output_directory, rawtime);

    reportFile = fopen(reportFileName, "w");
    if (reportFile == NULL)
    {
        printf("Error: Unable to create report file.\n");
    }
    return reportFile;
}

DIR *open_output_directory(char *output_directory)
{
    DIR *outputDir = opendir(output_directory);
    if (outputDir == NULL)
    {
        printf("Error: Unable to open output directory.\n");
    }
    return outputDir;
}

void generate_candidate_report(FILE *reportFile, char *candidateName, char *candidateFolderPath, int candidateNumber)
{
    fprintf(reportFile, "»»» Candidate - %d «««\n", candidateNumber);
    fprintf(reportFile, "»» Folder - %s\n", candidateFolderPath);
    fprintf(reportFile, "»» Files\n");

    DIR *candidateDir = opendir(candidateFolderPath);
    if (candidateDir)
    {
        struct dirent *fileEntry;
        while ((fileEntry = readdir(candidateDir)) != NULL)
        {
            if (strcmp(fileEntry->d_name, ".") == 0 || strcmp(fileEntry->d_name, "..") == 0)
            {
                continue;
            }
            fprintf(reportFile, "[> %s\n", fileEntry->d_name);
        }
        closedir(candidateDir);
    }
    else
    {
        fprintf(reportFile, "Error: Unable to open subfolder for %s\n", candidateName);
    }

    fprintf(reportFile, "\n");
}

void generate_report(int lastCandidate, char *output_directory)
{
    int candidateNumber = 0;
    FILE *reportFile = open_report_file(output_directory);
    if (reportFile == NULL)
    {
        return;
    }

    DIR *outputDir = open_output_directory(output_directory);
    if (outputDir == NULL)
    {
        fclose(reportFile);
        return;
    }

    struct dirent *entry;
    while ((entry = readdir(outputDir)) != NULL)
    {
        if (entry->d_type == DT_DIR && strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
        {
            candidateNumber = atoi(entry->d_name + strlen("candidate"));
            if (candidateNumber > lastCandidate)
            {
                char candidateFolderPath[300];
                snprintf(candidateFolderPath, sizeof(candidateFolderPath), "%s/%s", output_directory, entry->d_name);
                generate_candidate_report(reportFile, entry->d_name, candidateFolderPath, candidateNumber);
            }
        }
    }
    lastCandidate = candidateNumber;

    closedir(outputDir);
    fclose(reportFile);
}
