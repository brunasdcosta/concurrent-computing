package executor;

import java.util.List;

public class ExecutorMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/10000.txt", "pjro2kn94jvn", 4);
		bm.runAlgorithm();
		List<String> result = bm.getLevenshtein().getLowestsLevenshteinDistance();
		for (String word : result) {
			System.out.println(word);
		}
	}

}