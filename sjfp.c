#include<stdio.h>
int findShortestJob(int *cpuTime,int *arrTime,int n,int time){
int i,res = 0;
for(i=0;i<n;i++){
if(arrTime[i] > time || cpuTime[i] == 0)
continue;
else if(cpuTime[res] == 0)
res = i;
else if(cpuTime[i] < cpuTime[res])
res = i;
}
return res;
}
int checkIfFinished(int *cpuTime,int n){
for(int i=0;i<n;i++){
if(cpuTime[i] != 0)
return 1;
}
return 0;
}
void sjfp(int *cpuTime,int *arrTime,int *exitTime,int n){
int cpu[n],arr[n];
int i,time = 0,sj,prev=-1;
for(i=0;i<n;i++){
cpu[i] = cpuTime[i];
arr[i] = arrTime[i];
}
for(time = 0;checkIfFinished(cpu,n);time++){
sj = findShortestJob(cpu,arr,n,time);
if(prev != sj){
printf("%d--((%d))--",time,sj+1);
prev = sj;
}
cpu[sj]--;
if(cpu[sj] == 0)
exitTime[sj] = time+1;
}
printf("%d\n",time+1);
}
int main(){
int n,i;
scanf("%d",&n);
int cpuTime[n],arrTime[n],exitTime[n],wt[n],tat[n];
float avgwt=0,avgtat=0;
for(i = 0;i<n;i++)
scanf("%d %d",cpuTime+i,arrTime+i);
sjfp(cpuTime,arrTime,exitTime,n);
printf("***************************************************************************************************************\n");
printf("Process No \tArrivalTime\tBurstTime\tWaitingTime\tCompletionTime\tTurnAroundTime\n");
printf("***************************************************************************************************************\n");
for(i = 0;i<n;i++){
tat[i] = exitTime[i] - arrTime[i];
wt[i] = tat[i] - cpuTime[i];
avgtat += tat[i];
avgwt += wt[i];
printf("%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n",i+1,arrTime[i],cpuTime[i],wt[i],exitTime[i],tat[i]);
}
avgtat =(float)avgtat/n;
avgwt =(float)avgwt/n;
printf("***************************************************************************************************************\n");
printf("Average Waiting Time: %f\nAverage Turn Around Time:%f\n",avgwt,avgtat);
return 0;
}
