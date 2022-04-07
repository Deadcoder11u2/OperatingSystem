#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <time.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdbool.h>
int main(){
key_t key = 1234;
int sh_id = shmget(key, 11 * sizeof(int), IPC_CREAT | 0777);
int *sh = (int *)shmat(sh_id, NULL, 0);
sem_t *q = sem_open("q",0);
sem_t *r = sem_open("r",0);
while (1){
	sem_wait(q);
	scanf("%d", &sh[sh[10]++]);
	if(sh[10] == 10){
		sem_post(r);
		break;
	}
	sem_post(q);
}
return 0;
}
