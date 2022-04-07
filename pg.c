#include<stdio.h>
int main(){
	unsigned int LA,P;
	printf("Enter LA and page size:");
	scanf("%d %d",&LA,&P);
	printf("Page number: %d\nPage offest: %d\n",LA/P,LA%P);
	return 0;
}
