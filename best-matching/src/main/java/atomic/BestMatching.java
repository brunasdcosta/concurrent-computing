package atomic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BestMatching {

	private LevenshteinDistance levenshtein;
	private String file_path;

	public BestMatching(String file_path, String search_word) {
		this.file_path = file_path;
		this.levenshtein = new LevenshteinDistance(search_word);
	}

	public void run_algorithm() throws IOException {
		FileInputStream stream = null;
		Scanner scanner = null;
		List<String> list = new ArrayList<String>(); // Lista que armazena as palavras lidas.
		SimpleThread[] threads = new SimpleThread[8]; // Array que contém as threads.
		Map<Integer, List<String>> threads_lists = new HashMap<Integer, List<String>>(7); // Map utilizado para as cópias profundas da lista de palavras lidas.
		int line_count = 0, thread_count = 0; // Contadores.
		try {
			stream = new FileInputStream(file_path);
			scanner = new Scanner(stream, "UTF-8");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				line = line.replace("\"", "");
				line = line.replace(".", "");
				list.add(line);
				line_count++;
				if (thread_count < 7 && line_count == 4418580) { // Se ainda não existem 7 threads rodando e a quantidade de linhas lidas é 4418580.
					threads_lists.put(thread_count, new ArrayList<String>(list.size())); // Criando a cópia profunda.
					for (String word : list) {
						threads_lists.get(thread_count).add(word); // Copiando o conteúdo para a cópia. String é um objeto imutável.
					}
					threads[thread_count] = new SimpleThread("thread " + thread_count, threads_lists.get(thread_count), levenshtein); // Criando a thread.
					threads[thread_count].start(); // Iniciando a execução da thread.
					list.clear(); // Limpando a lista de palavras lidas.
					line_count = 0;
					thread_count++;
				} else if (thread_count == 7 && line_count == 4418598) { // Se for a última thread a ser criada e quantidade de linhas lidas é 4418598.
					threads[thread_count] = new SimpleThread("thread " + thread_count, list, levenshtein); // Podemos usar a lista de linhas lidas diretamente, pois não vamos mais manipulá-la.
					threads[thread_count].start(); // Iniciando a execução da última thread.
				}
			}
			if (scanner.ioException() != null) {
				throw scanner.ioException();
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
			if (scanner != null) {
				scanner.close();
			}
			for (SimpleThread thread : threads) { // Esperando as threads finalizarem.
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public LevenshteinDistance getLevenshtein() {
		return levenshtein;
	}

	public void setLevenshtein(LevenshteinDistance levenshtein) {
		this.levenshtein = levenshtein;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

}