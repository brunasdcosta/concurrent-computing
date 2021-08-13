package parallelstream;

public class LevenshteinDistance {

	private String searchWord;
	private int shortestDistance;

	public LevenshteinDistance(String searchWord) {
		this.searchWord = searchWord;
		this.shortestDistance = Integer.MAX_VALUE;
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

		int distance = matrix[swlen][wlen];

		if (distance < shortestDistance) {
			shortestDistance = distance;
		}

		return distance;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public int getShortestDistance() {
		return shortestDistance;
	}

	public void setShortestDistance(int shortestDistance) {
		this.shortestDistance = shortestDistance;
	}

}