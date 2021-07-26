package atomic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BestMatching {

	private LevenshteinDistance levenshtein;
	private String filePath;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
	}

	public void runAlgorithm() {
		BufferedReader reader = null;
		FileReader file = null;
		String line;
		try {
			file = new FileReader(filePath);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				levenshtein.addData(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (file != null) {
					file.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			levenshtein.runThreads(8);
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