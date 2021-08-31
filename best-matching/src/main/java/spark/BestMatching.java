package spark;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class BestMatching implements Serializable {

	private static final long serialVersionUID = -7506599179494374984L;

	private String filePath;
	private LevenshteinDistance levenshtein;
	private JavaRDD<String> words;
	private JavaPairRDD<String, Integer> distances;
	private JavaRDD<String> result;

	public BestMatching(String filePath, String searchWord, String coresConfig, int minPartitions) {
		this.filePath = filePath;
		levenshtein = new LevenshteinDistance(searchWord);
		SparkConf sparkConf = new SparkConf().setAppName("best-matching").setMaster(coresConfig);
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
		words = sparkContext.textFile(filePath, minPartitions);
	}

	public void calculateDistances() {
		distances = words.mapToPair(new PairFunction<String, String, Integer>() {
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Integer> call(String word) {
				return new Tuple2<String, Integer>(word, levenshtein.calculateLevenshteinDistance(word));
			}
		});
	}

	public void calculateResult() {
		JavaRDD<Integer> distancesRDD = distances.map(a -> a._2).distinct();
		final int shortestDistance = distancesRDD.reduce((i, j) -> i < j ? i : j);
		result = distances.filter(tuple -> tuple._2 == shortestDistance)
				.flatMap(tuple -> Arrays.asList(tuple._1).iterator());
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

	public JavaRDD<String> getWords() {
		return words;
	}

	public void setWords(JavaRDD<String> words) {
		this.words = words;
	}

	public JavaPairRDD<String, Integer> getDistances() {
		return distances;
	}

	public void setDistances(JavaPairRDD<String, Integer> distances) {
		this.distances = distances;
	}

	public JavaRDD<String> getResult() {
		return result;
	}

	public void setResult(JavaRDD<String> result) {
		this.result = result;
	}

}