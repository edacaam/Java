package thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomic {

	private final AtomicInteger counter = new AtomicInteger();

	public void incrementCounter() {
		counter.incrementAndGet();
	}

	public int getCounter() {
		return counter.get();
	}
}
