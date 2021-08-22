package jcstress;

import java.util.Set;
//import java.util.concurrent.ForkJoinPool;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

import executor.BestMatching;
//import forkjoin.BestMatching;
//import parallelstream.BestMatching;

public class Test {

	@State
	public static class MyState extends BestMatching {
		public MyState() {
			super("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXfddyzXXX");
		}
	}

	@JCStressTest
	@Outcome(id = "0", expect = Expect.ACCEPTABLE, desc = "correct response")
	@Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "incorrect response")
	public static class StressTest {

		@Actor
		public void actor(MyState myState, I_Result r) {

//			new ForkJoinPool().invoke(myState); // forkjoin
			myState.runAlgorithm();

			Set<String> results = myState.getResult();
			if (results != null && results.size() == 3 && results.contains("e1gzfddyz2uj")
					&& results.contains("1lstfddyzets") && results.contains("4aryfddyz5ty")) {
				r.r1 = 0;
			} else {
				r.r1 = 1;
			}
		}

	}

}