package forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/10000.txt", "pjro2kn94jvn", 1000);
		new ForkJoinPool().invoke(bm);
		List<String> result = bm.getLevenshtein().getLowestsLevenshteinDistance();
		for (String word : result) {
			System.out.println(word);
		}
	}

}