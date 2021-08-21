package forkjoin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveAction;

import common.LevenshteinDistance;

public class BestMatching extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private int threshold;

	private String filePath;
	private LevenshteinDistance levenshtein;
	private List<String> data;
	private ConcurrentHashMap<String, Integer> distances;
	private Set<String> result;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
		this.data = new ArrayList<String>();
		readFile();
	}

	public BestMatching(LevenshteinDistance levenshtein, List<String> data,
			ConcurrentHashMap<String, Integer> distances, int threshold) {
		this.levenshtein = levenshtein;
		this.data = data;
		this.distances = distances;
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
			this.distances = new ConcurrentHashMap<String, Integer>();
			this.threshold = Math.floorDiv(data.size(), 4);
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
		data.forEach(word -> {
			distances.put(word, levenshtein.calculateLevenshteinDistance(word));
		});
	}

	protected void compute() {
		if (data.size() <= threshold) {
			runAlgorith();
			this.data = null;
		} else {
			int mid = data.size() / 2;
			BestMatching firstSubtask = new BestMatching(this.levenshtein, this.data.subList(0, mid), this.distances,
					this.threshold);
			BestMatching secondSubtask = new BestMatching(this.levenshtein, this.data.subList(mid, data.size()),
					this.distances, this.threshold);
			invokeAll(firstSubtask, secondSubtask);
		}
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
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

	public ConcurrentHashMap<String, Integer> getDistances() {
		return distances;
	}

	public void setDistances(ConcurrentHashMap<String, Integer> distances) {
		this.distances = distances;
	}

	public Set<String> getResult() {
		if (result == null) {
			data = null;
			result = new HashSet<String>();
			final int shortestDistance = levenshtein.getShortestDistance().get();
			for (String word : distances.keySet()) {
				if (distances.get(word) == shortestDistance) {
					result.add(word);
				}
			}
		}
		return result;
	}

	public void setResult(Set<String> result) {
		this.result = result;
	}

}