package threads;

public class TestingThread {
	public static void main(String... args) {

		new Thread(() -> System.out.println("Running in paralelo!"))
			.start();
		System.out.println("Finished to run main");
	}
}