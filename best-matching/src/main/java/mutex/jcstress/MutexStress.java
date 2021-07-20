package mutex.jcstress;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

import mutex.LevenshteinDistance;

@JCStressTest()
@State
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "get back n569crhichx3")
@Outcome(id = "1, 0", expect = Expect.FORBIDDEN, desc = "not get back n569crhichx3")
@Outcome(id = "2, 1", expect = Expect.ACCEPTABLE, desc = "get back 2lbguk4fsjx0")
@Outcome(id = "2, 0", expect = Expect.FORBIDDEN, desc = "not get back 2lbguk4fsjx0")
@Outcome(id = "3, 1", expect = Expect.ACCEPTABLE, desc = "get back ydd202oufypq")
@Outcome(id = "3, 0", expect = Expect.FORBIDDEN, desc = "not get back ydd202oufypq")
@Outcome(id = "4, 1", expect = Expect.ACCEPTABLE, desc = "get back 28wsfnifabcd")
@Outcome(id = "4, 0", expect = Expect.FORBIDDEN, desc = "not get back 28wsfnifabcd")
@Outcome(id = "5, 1", expect = Expect.ACCEPTABLE, desc = "get back 8mfv09ii6wzm")
@Outcome(id = "5, 0", expect = Expect.FORBIDDEN, desc = "not get back 8mfv09ii6wzm")
public class MutexStress {

	private static final String FILE_PATH = "/home/bruna/workspace/concurrent-computing/dataset/test.txt";
	private String[] words = { "n569crhichx3", "2lbguk4fsjx0", "ydd202oufypq", "28wsfnifabcd", "8mfv09ii6wzm" };

	private int randomizeIndex() {
		return new Random().nextInt(5);
	}

	@Actor
	void bestMatching(II_Result r) {
		LevenshteinDistance levenshtein = null;
		FileInputStream stream = null;
		Scanner scanner = null;
		String word = null;
		int index = -1;
		try {
			stream = new FileInputStream(FILE_PATH);
			scanner = new Scanner(stream, "UTF-8");
			index = randomizeIndex();
			word = words[index];
			levenshtein = new LevenshteinDistance(word);
			while (scanner.hasNextLine()) {
				levenshtein.addData(scanner.nextLine());
			}
			if (scanner.ioException() != null) {
				throw scanner.ioException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (scanner != null) {
				scanner.close();
			}
			levenshtein.runThreads(8);
		}
		List<String> result = levenshtein.getLowestsLevenshteinDistance();
		r.r1 = index + 1;
		if (index != -1 && result != null && result.size() == 1 & result.get(0).equals(word)) {
			r.r2 = 1;
		} else {
			r.r2 = 0;
		}
	}

}