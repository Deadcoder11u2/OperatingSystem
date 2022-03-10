/*
Write a program to simulate the preemptive  SJF scheduling algorithms to find  average turnaround time and waiting time 
and completion time. Draw gantt chart as well. 
Read no of process, their cpu burst, arrival time from user.
*/
#include<stdio.h>
int findShortestJob(int *cpuTime,int *arrivalTime, int n,int time){
	int res = 0;
	for(int i=0;i<n;i++){
		if(arrivalTime[i]>time || cpuTime[i] < 1)
			continue;
		if(cpuTime[i] < cpuTime[res])
			res = i;
		else if(cpuTime[res] == 0)
			res = i;
	}
	return res;
}
int checkIfFinish(int *cpuTime,int n){
	for(int i=0;i<n;i++){
		if(cpuTime[i]>0)
			return 1;
	}
	return 0;
}
void sjfPre(int *cpu,int *arr,int *exit1,int n){
	int cpuTime[n],arrivalTime[n];
	int time,sj;
	for(time=0;time<n;time++){
		cpuTime[time] = cpu[time];
		arrivalTime[time] = arr[time];
	}
	for(time=0;checkIfFinish(cpuTime,n);time++){
		sj = findShortestJob(cpuTime,arrivalTime,n,time);
		cpuTime[sj]--;
		if(cpuTime[sj] < 1)
			exit1[sj] = time;
		printf("Time: %d \tProcess No:%d\n",time,sj+1);
	}
}
int main(){
	int n,i;
	scanf("%d",&n);
	int cpuTime[n],arrivalTime[n],exitTime[n];
	for(i=0;i<n;i++)
		scanf("%d %d",cpuTime+i,arrivalTime+i);
	sjfPre(cpuTime,arrivalTime,exitTime,n);
	return 0;
}