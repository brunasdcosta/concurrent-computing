package executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LevenshteinDistance {

	private String searchWord;
	private ConcurrentHashMap<Integer, List<String>> distances;

	public LevenshteinDistance(String searchWord) {
		this.searchWord = searchWord;
		this.distances = new ConcurrentHashMap<Integer, List<String>>();
	}

	/**
	 * Método que calcula a Distância de Levenshtein entre a palavra armazenada no
	 * atributo "searchWord" e outra passada por parâmetro.
	 * 
	 * @param word Palavra a qual se quer calcular a Distância de Levenshtein em
	 *             relação a "searchWord".
	 */
	public int calculateLevenshteinDistance(String word) {

		int swlen = searchWord.length();
		int wlen = word.length();
		int x, y, condition;
		int[][] matrix = new int[swlen + 1][wlen + 1];

		for (x = 0; x < swlen + 1; x++) {
			for (y = 0; y < wlen + 1; y++) {
				if (x == 0) {
					matrix[x][y] = y;
				} else if (y == 0) {
					matrix[x][y] = x;
				} else {
					if (searchWord.charAt(x - 1) == word.charAt(y - 1)) {
						condition = 0;
					} else {
						condition = 1;
					}
					matrix[x][y] = Math.min(Math.min(matrix[x - 1][y] + 1, matrix[x][y - 1] + 1),
							matrix[x - 1][y - 1] + condition);
				}
			}
		}

		return matrix[swlen][wlen];
	}

	/**
	 * Método que adiciona uma palavra (valor) e sua respectiva distância (chave) no
	 * hashmap "distances".
	 * 
	 * @param distance O valor da distância.
	 * @param word     A palavra atrelada ao valor da distância.
	 */
	public void addDistance(int distance, String word) {
		List<String> list;
		if (distances.containsKey(distance)) {
			list = distances.get(distance);
		} else {
			list = new ArrayList<String>();
		}
		list.add(word);
		distances.put(distance, list);
	}

	/**
	 * Método que recupera o conjunto de palavras com a menor Distância de
	 * Levenshtein em relação a "searchWord".
	 * 
	 * @return Um conjunto de palavras.
	 */
	public List<String> getLowestsLevenshteinDistance() {
		if (distances.keySet().isEmpty()) {
			return null;
		}
		return distances.get(Collections.min(distances.keySet()));
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public ConcurrentHashMap<Integer, List<String>> getDistances() {
		return distances;
	}

	public void setDistances(ConcurrentHashMap<Integer, List<String>> distances) {
		this.distances = distances;
	}

}