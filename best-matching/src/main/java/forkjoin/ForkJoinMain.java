package forkjoin;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi");
		new ForkJoinPool().invoke(bm);
		bm.getResult().forEach(e -> System.out.println(e));
	}

}