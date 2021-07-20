package atomic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevenshteinDistance {

	private String searchWord;

	// private ConcurrentHashMap<Integer, List<String>> distances;
	private Map<Integer, List<String>> distances;

	private List<String> data;

	public LevenshteinDistance(String searchWord) {
		this.distances = new HashMap<Integer, List<String>>();
		this.data = new ArrayList<String>();
		this.searchWord = searchWord;
	}

	/**
	 * Método que cria as "quantity" threads que irão realizar os cálculos nas
	 * palavras armazenadas no atributo "data".
	 * 
	 * @param quantity A quantidade de threads que devem ser criadas.
	 */
	public void runThreads(int quantity) {

		SimpleThread[] threads = new SimpleThread[quantity];
		int linesPerThread = Math.floorDiv(data.size(), quantity);
		int linesLastThread = data.size() - (linesPerThread * (quantity - 1));
		int init = 0;

		for (int i = 0; i < quantity; i++) {
			if (i == quantity - 1) {
				threads[i] = new SimpleThread("thread " + i, data.subList(init, init + linesLastThread), this);
			} else {
				threads[i] = new SimpleThread("thread " + i, data.subList(init, init + linesPerThread), this);
			}
			init += linesPerThread;
			threads[i].start();
		}

		for (SimpleThread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que calcula a Distância de Levenshtein entre a palavra armazenada no
	 * atributo "searchWord" e outra passada por parâmetro, adicionando o resultado
	 * na lista de distâncias.
	 * 
	 * @param word Palavra a qual se quer calcular a Distância de Levenshtein em
	 *             relação a "searchWord".
	 */
	public void calculateLevenshteinDistance(String word) {

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

		addDistance(matrix[swlen][wlen], word);
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

	/**
	 * Método que adiciona uma palavra na lista de palavras.
	 * 
	 * @param word A palavra que deve ser adicionada na lista "data".
	 */
	public void addData(String word) {
		data.add(word);
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public Map<Integer, List<String>> getDistances() {
		return distances;
	}

	public void setDistances(Map<Integer, List<String>> distances) {
		this.distances = distances;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}