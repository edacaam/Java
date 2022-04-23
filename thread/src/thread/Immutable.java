package thread;

public class Immutable {

	private final String message;

	public Immutable(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
