#include <stdio.h>
#include <unistd.h>

void delegate_candidate(int lastCandidate, int copiedCandDiff, int child, int fd[][2])
{
    int candidate = lastCandidate - (copiedCandDiff - 1);
    printf("INFO: Parent will delegate candidate %d to Child[%d]\n", candidate, (child + 1));
    int n = write(fd[child][1], &candidate, sizeof(candidate));
    if (n != sizeof(candidate))
    {
        printf("ERROR: Parent couldn't write all information to PIPE[%d].\n", (child + 1));
    }
}

void available_child(int *child, int fd)
{
    int n = read(fd, child, sizeof(*child));
    if (n != sizeof(*child))
    {
        printf("ERROR: Parent couldn't read all information of shared pipe.\n");
    }
    printf("Parent received notice from child: %d\n", (*child + 1));
}