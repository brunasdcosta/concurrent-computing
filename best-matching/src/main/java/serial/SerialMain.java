package serial;

import java.util.List;

public class SerialMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi");
		bm.runAlgorithm();
		List<String> result = bm.getLevenshtein().getLowestsLevenshteinDistance();
		for (String word : result) {
			System.out.println(word);
		}
	}

}