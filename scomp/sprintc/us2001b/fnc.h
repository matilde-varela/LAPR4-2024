#ifndef FNC_H
#define FNC_H

#define LENGTH_BUFFER 3

typedef struct
{
    char input_directory[100];
    char output_directory[100];
    int num_worker_children;
    int time_interval;
} Configuration;

typedef struct
{
    int array[LENGTH_BUFFER];
    int head;
    int tail;
    int size;
} BufferCircular;

void get_new_candidates(char *directory, int *current_number, int *max_diff);
int check_files_child(char *input_directory);
void copy_files(int n, char *input_directory, char *output_directory);
void generate_report(int lastCandidate, char *output_directory);

#endif