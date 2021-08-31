package spark;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {

	public static void main(String[] args) {
		Logger.getLogger("org").setLevel(Level.OFF);
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi",
				"local[*]", 4);
		bm.calculateDistances();
		bm.calculateResult();
		bm.getResult().foreach(word -> System.out.println(word));
	}

}