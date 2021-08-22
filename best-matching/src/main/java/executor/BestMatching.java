package executor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import common.LevenshteinDistance;

public class BestMatching {

	public static final int NUMBER_OF_THREADS = 8;

	private String filePath;
	private LevenshteinDistance levenshtein;
	private Set<String> result;

	public BestMatching(String filePath, String searchWord) {
		this.filePath = filePath;
		this.levenshtein = new LevenshteinDistance(searchWord);
	}

	public void runAlgorithm() {
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		List<Callable<Info>> callables = new ArrayList<>();
		FileReader file = null;
		BufferedReader reader = null;
		String line;
		try {
			file = new FileReader(filePath);
			reader = new BufferedReader(file);
			while ((line = reader.readLine()) != null) {
				final String finalLine = line;
				callables.add(new Callable<Info>() {
					@Override
					public Info call() throws Exception {
						return new Info(finalLine, levenshtein.calculateLevenshteinDistance(finalLine));
					}
				});
			}
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
				List<Future<Info>> futures = executorService.invokeAll(callables);
				result = new HashSet<String>();
				final int shortestDistance = levenshtein.getShortestDistance().get();
				for (Future<Info> future : futures) {
					if (future.get().getDistance() == shortestDistance) {
						result.add(future.get().getWord());
					}
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

	public LevenshteinDistance getLevenshtein() {
		return levenshtein;
	}

	public void setLevenshtein(LevenshteinDistance levenshtein) {
		this.levenshtein = levenshtein;
	}

	public Set<String> getResult() {
		return result;
	}

	public void setResult(Set<String> result) {
		this.result = result;
	}

}