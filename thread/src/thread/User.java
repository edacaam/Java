package thread;

public class User {
	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	private String name;
	private volatile int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
