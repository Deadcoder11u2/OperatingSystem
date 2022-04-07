#include <stdio.h>
#include <semaphore.h>
#include <sys/wait.h>
#include <sys/shm.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <error.h>
#include <unistd.h>
#define SHMSZ 27
void bubble_sort(int arr[], int n){
int i, j;
printf("\nSorting the nums\n\n");
for (i = 0; i < n - 1; i++){
for (j = 0; j < n - i - 1; j++){
if (arr[j] > arr[j + 1]){
int temp = arr[j];
arr[j] = arr[j + 1];
arr[j + 1] = temp;
}
}
}
}
int main(){
key_t key = 1234;
sem_unlink("r");
sem_unlink("q");
sem_t *r = sem_open("r", O_CREAT | O_EXCL, 0660, 0);
sem_t *q = sem_open("q", O_CREAT | O_EXCL, 0660, 1);
if (r == SEM_FAILED || q == SEM_FAILED ){
perror("ERROR !! \n");
exit(EXIT_FAILURE);
}
int sh_id = shmget(key, 11 * sizeof(int), IPC_CREAT | 0777);
int *sh = (int *)shmat(sh_id, NULL, 0);
sem_wait(r);
bubble_sort(sh, sh[10]);
printf("The sorted sequence after getting numbers from 2 diffrent programs: \n");
for (int i = 0; i < 10; i++)
printf("%d ", sh[i]);
printf("\n");
int ans = 0;
for(int i = 0 ; i < 10 ; i++)
ans += sh[i];
printf("Sum of the array is: %d", ans);
sem_close(r);
sem_close(q);
}
