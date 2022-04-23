package thread;

public class Synchronized {
	private int counter = 0;

	public synchronized void incrementCounter() {
		counter += 1;
	}

	public int getCounter() {
		return counter;
	}
}
