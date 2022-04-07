import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import static java.lang.Math.*;
import static java.lang.System.out;
import java.io.FileInputStream;
import java.util.*;
public class A {
static ArrayList<Integer> getSafeSeq() {
int work[] = Arrays.copyOf(available, m);
boolean finish[] = new boolean[n];
ArrayList<Integer> seq = new ArrayList<>();
int ans = -1;
for (int i = 0; i < n; i++) {
for (int j = 0; j < n; j++) {
boolean isless = true;
if (finish[j]) continue;
for (int k = 0; k < m; k++) {
isless &= need[j][k] <= work[k];
}
if (isless) {
ans = j;
finish[j] = true;
for (int k = 0; k < m; k++) {
work[k] += allocation[ans][k];
}
break;
}
}
if (ans == -1) throw null;
seq.add(ans);
}
return seq;
}
static volatile ReentrantLock rl;
static class ProcessExecution implements Runnable {
int t_no;
public ProcessExecution(int t_no) {
this.t_no = t_no;
}
@Override
public void run() {
rl.lock();
try {
out.println("Acquired the lock: " + t_no);
out.println("I am running");
try {
Thread.sleep(1000);
} catch (Exception e) {}
out.println("Now the available is");
for (int i = 0; i < m; i++) {
worke[i] += allocation[t_no][i];
}
out.println(Arrays.toString(worke));
} finally {
rl.unlock();
}
}
}
static int n, m;
static int allocation[][];
static int max_need[][];
static int need[][];
static int available[];
static int worke[];
static void Banker() throws Exception {
for (int i = 0; i < n; i++) {
for (int j = 0; j < m; j++) {
need[i][j] = max_need[i][j] - allocation[i][j];
}
}
out.println("Allocation");
for (int i = 0; i < n; i++) {
for (int j = 0; j < m; j++) {
out.printf("%d\t", allocation[i][j]);
}
out.println();
}
out.println("Need");
for (int i = 0; i < n; i++) {
for (int j = 0; j < m; j++) {
out.printf("%d\t", need[i][j]);
}
out.println();
}
out.println("Max");
for (int i = 0; i < n; i++) {
for (int j = 0; j < m; j++) {
out.printf("%d\t", max_need[i][j]);
}
out.println();
}
ArrayList<Integer> safe_sequence = getSafeSeq();
Thread th[] = new Thread[n];
ProcessExecution pr[] = new ProcessExecution[n];
worke = Arrays.copyOf(available, m);
out.println(safe_sequence);
for (int i = 0; i < n; i++) {
pr[i] = new
ProcessExecution(safe_sequence.get(i));
th[i] = new Thread(pr[i]);
th[i].setPriority(i + 1);
th[i].start();
th[i].join();
}
}
public static void main(String args[]) throws Exception {
rl = new ReentrantLock();
ExecutorService service =
Executors.newSingleThreadExecutor();
Scanner fs = new Scanner(System.in);
out.println("Enter the number of threads");
n = fs.nextInt();
out.println("Enter the number of dishes");
m = fs.nextInt();
allocation = new int[n][m];
for (int i = 0; i < n; i++) {
out.printf("Enter the allocation of %d\n", i + 1);
for (int j = 0; j < m; j++) {
allocation[i][j] = fs.nextInt();
}
}
max_need = new int[n][m];
for (int i = 0; i < n; i++) {
out.printf("Enter the max of thread %d\n", i + 1);
for (int j = 0; j < m; j++) {
max_need[i][j] = fs.nextInt();
}
}
out.printf("Enter the available of all the products\n");
available = new int[m];
for (int i = 0; i < m; i++) {
available[i] = fs.nextInt();
}
need = new int[n][m];
Banker();
int request[] = new int[m + 1];
out.println("Enter the thread no");
request[m] = fs.nextInt() - 1;
out.println("Enter the request");
for (int i = 0; i < m; i++) {
request[i] = fs.nextInt();
}
boolean canGrant = true;
for (int i = 0; i < m; i++) {
canGrant &= request[i] <= need[request[m]][i];
canGrant &= request[i] <= available[i];
}
if (!canGrant) {
out.println("Request Cannot be Granted");
} else {
out.println("Request Granted Recalculating the Safe Sequence");
for (int i = 0; i < m; i++) {
allocation[request[m]][i] += request[i];
}
for (int i = 0; i < m; i++) {
available[i] -= request[i];
}
Banker();
}
}
}
