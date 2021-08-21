package executor;

public class ExecutorMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi", 4);
		bm.runAlgorithm();
		bm.getResult().forEach(e -> System.out.println(e));
	}

}