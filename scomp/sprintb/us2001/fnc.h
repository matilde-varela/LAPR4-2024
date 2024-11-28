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
void delegate_candidate(int lastCandidate, int copiedCandDiff, int child, int fd[][2]);
void available_child(int *child, int fd);
void check_files_child_ls(int *newFilePipe, int *newFilePipeMidChildren);
void check_files_child_wc(int *newFilePipe, int *newFilePipeMidChildren);
void copy_files(int n, char *input_directory, char *output_directory);
void generate_report(int lastCandidate, char *output_directory);
void generate_candidate_report(FILE *reportFile, char *candidateName, char *candidateFolderPath, int candidateNumber);
DIR *open_output_directory(char *output_directory);
FILE *open_report_file();
int charToInt(char str);

#endif