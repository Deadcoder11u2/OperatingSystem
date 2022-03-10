
import static java.lang.Math.*;
import java.time.*;
import java.util.Random;

public class A {
	static int global[] = new int[10_000_000];
	static int ans[];

	static class FindLargest implements Runnable {
		int l, r, t_no;

		FindLargest(int l, int r, int t_no) {
			this.l = l;
			this.r = r;
			this.t_no = t_no;
		}

		public void run() {
			for (int i = l; i < r; i++) {
				ans[t_no] = max(ans[t_no], global[i]);
			}
		}
	}

	static void assign() {
		Random rand = new Random();
		for (int i = 0; i < 10_000_000; i++) {
			global[i] = rand.nextInt();
		}
	}

	public static void main(String args[]) throws Exception {
		int n_threads = Integer.parseInt(args[0]);
		ans = new int[n_threads];
		FindLargest objs[] = new FindLargest[n_threads];
		int left = 0, right = 10_000_000 / n_threads;
		int x = right;
		for (int i = 0; i < n_threads; i++) {
			objs[i] = new FindLargest(left, right, i);
			left = right + 1;
			right += x;
			right = min(right, 10_000_000);
		}
		assign();
		Thread t[] = new Thread[n_threads];
		for (int i = 0; i < n_threads; i++) {
			t[i] = new Thread(objs[i]);
		}
		Instant start = Instant.now();
		for (int i = 0; i < n_threads; i++) {
			t[i].start();
		}
		for (int i = 0; i < n_threads; i++) {
			t[i].join();
		}
		int fin_ans = 0;
		for (int i = 0; i < n_threads; i++)
			fin_ans = max(fin_ans, ans[i]);
		System.out.println(fin_ans);
		Instant end = Instant.now();
		System.out.println("Time Taken: " + Duration.between(start, end).toMillis());
		System.out.println();
	}
}