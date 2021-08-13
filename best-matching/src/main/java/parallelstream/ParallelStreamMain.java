package parallelstream;

public class ParallelStreamMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/10000.txt", "aaaaaaaaaaaa");
		bm.runAlgorith();
		bm.getResult().forEach(e -> System.out.println(e));
	}

}