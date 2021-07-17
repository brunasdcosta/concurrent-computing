package serial;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class BestMatching {

	private LevenshteinDistance levenshtein;
	private String file_path;

	public BestMatching(String file_path, String search_word) {
		this.file_path = file_path;
		this.levenshtein = new LevenshteinDistance(search_word);
	}

	public void run_algorithm() throws IOException {
		FileInputStream stream = null;
		Scanner scanner = null;
		try {
			stream = new FileInputStream(file_path);
			scanner = new Scanner(stream, "UTF-8");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				line = line.replace("\"", "");
				line = line.replace(".", "");
				levenshtein.calculateLevenshteinDistance(line);
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

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

}