package jmh;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import spark.BestMatching;

public class Testbench {

	@Benchmark
	@Fork(value = 1)
	@Warmup(iterations = 5)
	@Measurement(iterations = 10)
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.MINUTES)
	public void init(Blackhole bh) {

		Logger.getLogger("org").setLevel(Level.OFF);

		final String filePath = "/home/bruna/workspace/concurrent-computing/dataset.txt";
		final String searchWord = "XXXXXXX2t8mi";
		final int resultCount = 7;

		new BestMatching(filePath, searchWord, "local[*]", 4, resultCount, bh);
	}

}