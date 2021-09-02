package spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {

	public static void main(String[] args) {

		final String filePath = "/home/bruna/workspace/concurrent-computing/dataset.txt";
		final String searchWord = "XXXXXXX2t8mi";
		final int resultCount = 7;

		Logger.getLogger("org").setLevel(Level.OFF);

		BestMatching bm = new BestMatching(filePath, searchWord, "local[*]", 4);

		bm.calculateDistances();

		bm.calculateResult();
		while (bm.getResult().count() != resultCount) {
			/* wait */
		}

		bm.getResult().foreach(word -> System.out.println(word));
	}

}