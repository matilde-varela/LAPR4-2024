#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void check_files_child_ls(int *newFilePipe, int *newFilePipeMidChildren){
    //child executing ls command will not interact with the pipe indicating the existence of new files
    close(newFilePipe[0]);
    close(newFilePipe[1]);
    close(newFilePipeMidChildren[0]); // child ls will not read from wc pipe

    if (dup2(newFilePipeMidChildren[1], 1) == -1) { //copy write to stdout
        perror("Duplication of file descriptor failed");
        exit(1);
    }
    close(newFilePipeMidChildren[1]);  // child will no longer write for wc pipe

    execlp("ls","ls", (char *)NULL);
}

void check_files_child_wc(int *newFilePipe, int *newFilePipeMidChildren){
    close(newFilePipe[0]); //wc child will not read on new file pipe
    close(newFilePipeMidChildren[1]); // child 2 will not write from children pipe

    if (dup2(newFilePipeMidChildren[0], 0) == -1) { //copy read to stdin
        perror("Duplication of file descriptor failed");
        exit(1);
    }
    close(newFilePipeMidChildren[0]);  // child will no longer read from listing pipe

    if (dup2(newFilePipe[1], 1) == -1) { //copy write to stdout
        perror("Duplication of file descriptor failed");
        exit(1);
    }
    close(newFilePipe[1]);  // child will no longer write from new file pipe

    execlp("wc","wc", "-l", (char *)NULL);
}