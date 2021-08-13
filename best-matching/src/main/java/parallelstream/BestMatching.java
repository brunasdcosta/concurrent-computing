package parallelstream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BestMatching {

	private String filePath;
	private LevenshteinDistance levenshtein;
	private List<String> data;
	private Map<String, Integer> distances;
	private List<String> result;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
		readFile();
	}

	private void readFile() {
		BufferedReader reader = null;
		FileReader file = null;
		String line;
		this.data = new ArrayList<String>();
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

	public void runAlgorith() {
		distances = data.parallelStream()
				.collect(Collectors.toMap(i -> i, i -> levenshtein.calculateLevenshteinDistance(i)));
		int lowest = levenshtein.getShortestDistance();
		result = distances.keySet().parallelStream().filter(key -> distances.get(key) == lowest)
				.collect(Collectors.toList());
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

	public Map<String, Integer> getDistances() {
		return distances;
	}

	public void setDistances(Map<String, Integer> distances) {
		this.distances = distances;
	}

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

}