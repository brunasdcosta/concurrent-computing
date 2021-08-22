package parallelstream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import common.LevenshteinDistance;

public class BestMatching {

	private String filePath;
	private LevenshteinDistance levenshtein;
	private Set<String> data;
	private Map<String, Integer> distances;
	private Set<String> result;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
		readFile();
	}

	private void readFile() {
		BufferedReader reader = null;
		FileReader file = null;
		String line;
		this.data = new HashSet<String>();
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

	public void runAlgorithm() {
		distances = data.parallelStream()
				.collect(Collectors.toMap(i -> i, i -> levenshtein.calculateLevenshteinDistance(i)));
		final int shortestDistance = levenshtein.getShortestDistance().get();
		result = distances.keySet().parallelStream().filter((key) -> distances.get(key) == shortestDistance)
				.collect(Collectors.toSet());
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

	public Set<String> getData() {
		return data;
	}

	public void setData(Set<String> data) {
		this.data = data;
	}

	public Map<String, Integer> getDistances() {
		return distances;
	}

	public void setDistances(Map<String, Integer> distances) {
		this.distances = distances;
	}

	public Set<String> getResult() {
		return result;
	}

	public void setResult(Set<String> result) {
		this.result = result;
	}

}