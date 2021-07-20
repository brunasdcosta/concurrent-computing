package serial;

import java.io.IOException;
import java.util.List;

public class SerialMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset/dataset.txt", "rs313lj1kxy0");
		try {
			bm.runAlgorithm();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> result = bm.getLevenshtein().getLowestsLevenshteinDistance();
		for (String word : result) {
			System.out.println(word);
		}
	}

}