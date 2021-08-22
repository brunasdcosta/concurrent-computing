package jmh;

//import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import executor.BestMatching;
//import forkjoin.BestMatching;
//import parallelstream.BestMatching;
//import reactive.BestMatching;

public class Testbench {

	@Benchmark
	@Fork(value = 1)
	@Warmup(iterations = 1)
	@Measurement(iterations = 1)
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.MINUTES)
	public void init(Blackhole bh) {

		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi");

//		new ForkJoinPool().invoke(bm); // forkjoin
		bm.runAlgorithm(); // others

		bh.consume(bm.getResult());
	}

}