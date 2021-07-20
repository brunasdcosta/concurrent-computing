package serial;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class BestMatching {

	private LevenshteinDistance levenshtein;
	private String filePath;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
	}

	public void runAlgorithm() throws IOException {
		FileInputStream stream = null;
		Scanner scanner = null;
		try {
			stream = new FileInputStream(filePath);
			scanner = new Scanner(stream, "UTF-8");
			while (scanner.hasNextLine()) {
				levenshtein.calculateLevenshteinDistance(scanner.nextLine());
			}
			if (scanner.ioException() != null) {
				throw scanner.ioException();
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	public LevenshteinDistance getLevenshtein() {
		return levenshtein;
	}

	public void setLevenshtein(LevenshteinDistance levenshtein) {
		this.levenshtein = levenshtein;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}