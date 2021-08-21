package reactive;

import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Mono;

public class LevenshteinDistance {

	private String searchWord;
	private AtomicInteger shortestDistance;

	public LevenshteinDistance(String searchWord) {
		this.searchWord = searchWord;
		this.shortestDistance = new AtomicInteger(Integer.MAX_VALUE);
	}

	/**
	 * Método que calcula a Distância de Levenshtein entre a palavra armazenada no
	 * atributo "searchWord" e outra passada por parâmetro.
	 * 
	 * @param word Palavra a qual se quer calcular a Distância de Levenshtein em
	 *             relação a "searchWord".
	 * @return Um Mono que contém a distância calculada.
	 */
	public Mono<Integer> calculateLevenshteinDistance(String word) {

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

		int distance = matrix[swlen][wlen];
		matrix = null;

		if (distance < shortestDistance.get()) {
			shortestDistance.set(distance);
		}

		return Mono.just(distance);
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public AtomicInteger getShortestDistance() {
		return shortestDistance;
	}

	public void setShortestDistance(AtomicInteger shortestDistance) {
		this.shortestDistance = shortestDistance;
	}

}