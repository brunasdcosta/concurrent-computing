package reactive;

public class ReactiveMain {

	public static void main(String[] args) {
		BestMatching bm = new BestMatching("/home/bruna/workspace/concurrent-computing/dataset.txt", "XXXXXXX2t8mi");
		bm.runAlgorithm();
		bm.getResult().subscribe(word -> System.out.println(word));
	}

}