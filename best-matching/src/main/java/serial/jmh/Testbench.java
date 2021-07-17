package serial.jmh;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import serial.BestMatching;

public class Testbench {

	@Benchmark
	@Warmup(iterations = 3)
	@Measurement(iterations = 7)
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void init(Blackhole bh) {
		BestMatching bm = new BestMatching("/home/bruna/ufrn/concorrente/archive/titles.csv", "The");
		try {
			bm.run_algorithm();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> result = bm.getLevenshtein().getLowestsLevenshteinDistance();
		bh.consume(result);
	}

}