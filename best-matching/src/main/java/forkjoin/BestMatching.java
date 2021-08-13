package forkjoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class BestMatching extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private int threshold;

	private String filePath;
	private LevenshteinDistance levenshtein;
	private List<String> data;

	public BestMatching(String filePath, String searchWord, int threshold) {
		this.filePath = filePath;
		this.threshold = threshold;
		this.levenshtein = new LevenshteinDistance(searchWord);
		this.data = new ArrayList<String>();
		readFile();
	}

	public BestMatching(LevenshteinDistance levenshtein, List<String> data, int threshold) {
		this.levenshtein = levenshtein;
		this.data = data;
		this.threshold = threshold;
	}

	private void readFile() {
		BufferedReader reader = null;
		FileReader file = null;
		String line;
		try {
			file = new FileReader(filePath);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				data.add(line);
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
		}
	}

	private void runAlgorith() {
		for (String word : data) {
			levenshtein.calculateLevenshteinDistance(word);
		}
	}

	protected void compute() {
		if (data.size() <= threshold) {
			runAlgorith();
		} else {
			int mid = data.size() / 2;
			BestMatching firstSubtask = new BestMatching(this.levenshtein, this.data.subList(0, mid), this.threshold);
			BestMatching secondSubtask = new BestMatching(this.levenshtein, this.data.subList(mid, data.size()),
					this.threshold);
			invokeAll(firstSubtask, secondSubtask);
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LevenshteinDistance getLevenshtein() {
		return levenshtein;
	}

	public void setLevenshtein(LevenshteinDistance levenshtein) {
		this.levenshtein = levenshtein;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}