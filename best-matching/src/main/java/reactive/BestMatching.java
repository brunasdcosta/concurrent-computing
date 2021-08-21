package reactive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Flux;

public class BestMatching {

	private String filePath;
	private LevenshteinDistance levenshtein;
	private Flux<String> data;
	private Map<String, Integer> distances;
	private Flux<String> result;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
		readFile();
	}

	private void readFile() {
		BufferedReader reader = null;
		FileReader file = null;
		String line;
		boolean first = true;
		try {
			file = new FileReader(filePath);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				if (first) {
					data = Flux.just(line);
					first = false;
				} else {
					data = Flux.merge(data, Flux.just(line));
				}
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
		distances = new HashMap<String, Integer>();
		data.subscribe(word -> {
			levenshtein.calculateLevenshteinDistance(word).subscribe(distance -> {
				distances.put(word, distance);
			});
		});
		data = null;
		boolean first = true;
		final int shortestDistance = levenshtein.getShortestDistance();
		for (String word : distances.keySet()) {
			if (distances.get(word) == shortestDistance) {
				if (first) {
					result = Flux.just(word);
					first = false;
				} else {
					result = Flux.merge(result, Flux.just(word));
				}
			}
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

	public Flux<String> getData() {
		return data;
	}

	public void setData(Flux<String> data) {
		this.data = data;
	}

	public Map<String, Integer> getDistances() {
		return distances;
	}

	public void setDistances(Map<String, Integer> distances) {
		this.distances = distances;
	}

	public Flux<String> getResult() {
		return result;
	}

	public void setResult(Flux<String> result) {
		this.result = result;
	}

}