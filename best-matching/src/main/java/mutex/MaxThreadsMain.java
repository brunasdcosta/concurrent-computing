package mutex;

import java.util.ArrayList;
import java.util.List;

public class MaxThreadsMain {
	public static final int BATCH_SIZE = 4000;

	public static void main(String[] args) {
		List<Thread> threads = new ArrayList<Thread>();
		try {
			for (int i = 0; i < 100 * 1000; i += BATCH_SIZE) {
				addThread(threads, BATCH_SIZE);
				Thread.sleep(1000);
				System.out.printf("%,d threads%n", threads.size(), BATCH_SIZE);
			}
		} catch (Throwable e) {
			System.err.printf("After creating %,d threads, ", threads.size());
			e.printStackTrace();
		}
	}

	private static void addThread(List<Thread> threads, int num) {
		for (int i = 0; i < num; i++) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						while (!Thread.interrupted()) {
							Thread.sleep(1000);
						}
					} catch (InterruptedException ignored) {
						//
					}
				}
			});
			t.setDaemon(true);
			t.setPriority(Thread.MIN_PRIORITY);
			threads.add(t);
			t.start();
		}
	}
}
