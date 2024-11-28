#ifndef FNC_H
#define FNC_H

typedef struct
{
    char input_directory[100];
    char output_directory[100];
    int num_worker_children;
    int time_interval;
} Configuration;

void get_new_candidates(const char *directory, int *current_number, int *max_diff);
void copy_files(int n, char *input_directory, char *output_directory);
void generate_report(int lastCandidate, char *output_directory);

#endif
