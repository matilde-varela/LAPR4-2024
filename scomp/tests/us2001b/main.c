#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include "../unity.h"
#include "fnc.h" // Include the header file containing your functions

void call_func(void (*)(const char *, int *, int *), const char *directory, int *current_number, int *max_diff);

void call_func_copy_files(void (*)(int, char *, char *), int n, char *input_directory, char *output_directory);

void call_func_generate_report(void  (*)(int, char*),int lastCandidate, char *output_directory);

// Define setup and teardown functions if needed
void setUp(void)
{
    // Set up code here
}

void tearDown(void)
{
    // Tear down code here
}

int count_files(const char *directory)
{
    DIR *dir;
    struct dirent *entry;
    int file_count = 0;

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
            file_count++;
        }
    }

    // Close the directory
    closedir(dir);

    return file_count;
}

char *find_report_file(const char *output_directory) {
    DIR *outputDir = opendir(output_directory);
    if (outputDir == NULL) {
        printf("Failed to open output directory\n");
        return NULL;
    }

    // Static buffer to store the full path of the report file
    static char reportPath[512]; // Adjust the buffer size as needed

    // Initialize the buffer to an empty string
    reportPath[0] = '\0';

    // Scan the directory for the report file
    struct dirent *entry;
    while ((entry = readdir(outputDir)) != NULL) {
        if (entry->d_type == DT_REG) { // Check if it's a regular file
            // Create the full path of the report file
            snprintf(reportPath, sizeof(reportPath), "%s/%s", output_directory, entry->d_name);
            break; // Exit the loop after finding the first file
        }
    }

    closedir(outputDir); // Close the output directory

    return reportPath;
}

void test_get_new_candidates(void)
{
    int current_number = 0;
    int max_diff = 0;

    call_func(get_new_candidates, "test_input", &current_number, &max_diff);

    TEST_ASSERT_EQUAL_INT(2, current_number);
    TEST_ASSERT_EQUAL_INT(2, max_diff);
}

void test_copy_files()
{
    char *output_directory;
    int actual_count;
    call_func_copy_files(copy_files, 1, "test_input", "test_output");
    call_func_copy_files(copy_files, 2, "test_input", "test_output");
    sleep(1); // so that it has time to copy files to verify after

    output_directory = "test_output/candidate1";
    actual_count = count_files(output_directory); // Call the function to count files
    TEST_ASSERT_EQUAL_INT(2, actual_count);

    output_directory = "test_output/candidate2";
    actual_count = count_files(output_directory); // Call the function to count files
    TEST_ASSERT_EQUAL_INT(1, actual_count);
}

void test_generate_report()
{
    // Call the function to generate the report
    call_func_generate_report(generate_report, 0, "test_output");

    char *reportFilename = find_report_file("test_output");
    if (reportFilename == NULL) {
        printf("Failed to find report file\n");
        TEST_FAIL();
        return;
    }
    
    // Open the generated report file for reading
    FILE *reportFile = fopen(reportFilename, "r");
    if (reportFile == NULL)
    {
        printf("Failed to open report file for reading\n");
        TEST_FAIL();
        return;
    }

    // Buffer to read each line of the report file
    char line[500]; // Assuming maximum line length is 500 characters
    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("»»» Candidate - 1 «««\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("»» Folder - test_output/candidate1\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("»» Files\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("[> 1-candidate-data.txt\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("[> 1-cv.txt\n", line);
    fgets(line, sizeof(line), reportFile);
    // Read each line from the report file and verify its content
    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("»»» Candidate - 2 «««\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("»» Folder - test_output/candidate2\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("»» Files\n", line);

    fgets(line, sizeof(line), reportFile);
    TEST_ASSERT_EQUAL_STRING("[> 2-cv.txt\n", line);

    fclose(reportFile); // Close the report file
    remove(reportFilename);
}

int main(void)
{
    UNITY_BEGIN();
    printf("\n»»» START TESTS «««\n");
    RUN_TEST(test_get_new_candidates);
    RUN_TEST(test_copy_files);
    RUN_TEST(test_generate_report);
    printf("\n»»» END TESTS «««\n");
    return UNITY_END();
}