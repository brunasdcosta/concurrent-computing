package executor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class BestMatching {

	private String filePath;
	private int numThreads;
	private LevenshteinDistance levenshtein;

	public BestMatching(String filePath, String searchWord, int numThreads) {
		this.filePath = filePath;
		this.numThreads = numThreads;
		this.levenshtein = new LevenshteinDistance(searchWord);
	}

	public void runAlgorithm() {
		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
		List<Callable<Info>> callables = new ArrayList<>();
		FileReader file = null;
		BufferedReader reader = null;
		Stream<String> lines = null;
		try {
			file = new FileReader(filePath);
			reader = new BufferedReader(file);
			lines = reader.lines();
			lines.forEach(line -> callables.add(new Callable<Info>() {
				@Override
				public Info call() throws Exception {
					return new Info(line, levenshtein.calculateLevenshteinDistance(line));
				}
			}));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null) {
					file.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (lines != null) {
					lines.close();
				}
				List<Future<Info>> futures = executorService.invokeAll(callables);
				for (Future<Info> future : futures) {
					levenshtein.addDistance(future.get().getDistance(), future.get().getWord());
				}
				executorService.shutdown();
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	public LevenshteinDistance getLevenshtein() {
		return levenshtein;
	}

	public void setLevenshtein(LevenshteinDistance levenshtein) {
		this.levenshtein = levenshtein;
	}

}