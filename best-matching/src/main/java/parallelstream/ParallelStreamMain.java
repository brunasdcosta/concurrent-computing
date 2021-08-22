package parallelstream;

public class ParallelStreamMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi");
		bm.runAlgorithm();
		bm.getResult().forEach(e -> System.out.println(e));
	}

}