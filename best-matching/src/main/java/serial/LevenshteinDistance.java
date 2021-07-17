package serial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevenshteinDistance {

	private String search_word;

	private Map<Integer, List<String>> distances;

	public LevenshteinDistance(String search_word) {
		this.distances = new HashMap<Integer, List<String>>();
		this.search_word = search_word;
	}

	/**
	 * Método que calcula a Distância de Levenshtein entre a palavra armazenada no
	 * atributo "search_word" e outra passada por parâmetro, adicionando o resultado
	 * na lista de distâncias.
	 * 
	 * @param word Palavra a qual se quer calcular a Distância de Levenshtein em
	 *             relação a "search_word".
	 */
	public void calculateLevenshteinDistance(String word) {

		int swlen = search_word.length();
		int wlen, x, y, condition, distance;
		int[][] matrix;
		List<String> list;

		wlen = word.length();
		matrix = new int[swlen + 1][wlen + 1];

		for (x = 0; x < swlen + 1; x++) {
			for (y = 0; y < wlen + 1; y++) {
				if (x == 0) {
					matrix[x][y] = y;
				} else if (y == 0) {
					matrix[x][y] = x;
				} else {
					if (search_word.charAt(x - 1) == word.charAt(y - 1)) {
						condition = 0;
					} else {
						condition = 1;
					}
					matrix[x][y] = Math.min(Math.min(matrix[x - 1][y] + 1, matrix[x][y - 1] + 1),
							matrix[x - 1][y - 1] + condition);
				}
			}
		}

		distance = matrix[swlen][wlen];

		if (distances.containsKey(distance)) {
			list = distances.get(distance);
		} else {
			list = new ArrayList<String>();
		}

		list.add(word);
		distances.put(distance, list);

	}

	public String getSearch_word() {
		return search_word;
	}

	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}

	public Map<Integer, List<String>> getDistances() {
		return distances;
	}

	public void setDistances(Map<Integer, List<String>> distances) {
		this.distances = distances;
	}

	/**
	 * Método que recupera o conjunto de palavras com a menor Distância de
	 * Levenshtein em relação a "search_word".
	 * 
	 * @return Um conjunto de palavras.
	 */
	public List<String> getLowestsLevenshteinDistance() {
		return distances.get(Collections.min(distances.keySet()));
	}

}