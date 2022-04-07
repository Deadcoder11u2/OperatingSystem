#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include <sys/wait.h>
int main(int argc,char *argv[]){
char *a=argv[1];
int n= atoi(a);
int sleep_time,seed;
printf("Inside function\n");
while(n--){
if(fork() == 0){
seed = getpid();
printf("Inside child with pid: %d\n",seed);
sleep_time = rand_r(&seed) % 15;
printf("sleeping for time %d\n",sleep_time);
sleep(sleep_time);
printf("%d exitting\n",getpid());
exit(0);
}
}
wait(NULL);
return 0;
}

