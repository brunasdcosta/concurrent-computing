package atomic;

import java.util.List;

public class SimpleThread extends Thread {

	private List<String> data;
	private LevenshteinDistance levenshtein;

	public SimpleThread(String str, List<String> data, LevenshteinDistance levenshtein) {
		super(str);
		this.data = data;
		this.levenshtein = levenshtein;
	}

	public void run() {
		for (String word : data) {
			if (isInterrupted()) {
				return;
			}
			levenshtein.calculateLevenshteinDistance(word);
		}
	}

}