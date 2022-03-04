import java.io.*;
import java.util.*;

public class Main {
	static class Process {
		int cpu_burst;
		int arrival_time;
		int pid;
		int cpu_needed;

		Process(int cpu_burst, int arrival_time, int pid) {
			this.cpu_burst = cpu_burst;
			this.arrival_time = arrival_time;
			cpu_needed = cpu_burst;
			this.pid = pid;
		}
		
		public String toString() { return "{"+pid+","+cpu_needed+"}" ; }
	}

	static class Gantt {
		int pid, start, end;

		Gantt(int pid) {
			this.pid = pid;
			start = 0;
			end = 0;
		}

		public String toString() {
			return "{pid:" + pid + "\t start:" + start + "\t end:" + end + "}";
		}
	}

	public static void main(String args[]) throws IOException {
		System.setIn(new FileInputStream("D:\\program\\javaCPEclipse\\CodeForces\\src\\input.txt"));
		Scanner fs = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out, true);
		int n = fs.nextInt();
		Comparator<Process> tim = (p1, p2) -> Integer.compare(p1.arrival_time, p2.arrival_time);
		PriorityQueue<Process> a = new PriorityQueue<Process>(tim);
		for (int i = 0; i < n; i++) {
			int cpu_burst = fs.nextInt();
			int arrival_time = fs.nextInt();
			a.add(new Process(cpu_burst, arrival_time, i));
		}
		Comparator<Process> burst = (p1, p2) -> Integer.compare(p1.cpu_needed, p2.cpu_needed);
		PriorityQueue<Process> ready = new PriorityQueue<Process>(burst);
		int timer = 1;
		int cur_pid = -1;
		ArrayList<Gantt> chart = new ArrayList<>();
		while (!ready.isEmpty() || !a.isEmpty()) {
			while (!a.isEmpty()) {
				if (a.peek().arrival_time <= timer) {
					ready.add(a.poll());
				} else
					break;
			}
			Process min_burst = ready.peek();
			if (cur_pid == min_burst.pid) {
				if (chart.size() > 0) {
					chart.get(chart.size() - 1).end++;
				}
				timer++;
				min_burst.cpu_needed--;
				if (min_burst.cpu_needed == 0)
					ready.poll();
			} else {
				cur_pid = min_burst.pid;
				if (chart.size() > 0) {
					chart.get(chart.size() - 1).end++;
				}
				if (cur_pid != -1) {
					Gantt temp = new Gantt(min_burst.pid);
					temp.start = timer;
					temp.end = timer;
					chart.add(temp);
				}
				timer++;
				min_burst.cpu_needed--;
				if (min_burst.cpu_needed == 0)
					ready.poll();
			}
		}
		for (Gantt g : chart) {
			pw.println(g);
		}
		pw.close();
		fs.close();
	}
}