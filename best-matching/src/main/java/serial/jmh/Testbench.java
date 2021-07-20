package serial.jmh;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import serial.BestMatching;

public class Testbench {

	@Benchmark
	@Fork(value = 1)
	@Warmup(iterations = 5)
	@Measurement(iterations = 10)
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.MINUTES)
	public void init(Blackhole bh) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset/dataset.txt", "rs313lj1kxy0");
		try {
			bm.runAlgorithm();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bh.consume(bm.getLevenshtein().getLowestsLevenshteinDistance());
	}

}