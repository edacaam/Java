package optional;

import java.util.Optional;

public class Main {

	public static void main(String[] args) {
		Optional<String> empty = Optional.empty();
		System.out.println("Empty optional:" + empty);

		String name = "Eda";
		Optional<String> optional = Optional.of(name);
		System.out.println("Name: " + optional);

		String lastName = null;
		Optional<String> nullableOptional = Optional.ofNullable(lastName);
		System.out.println("Nullable Name: " + nullableOptional);

		String assignDefault = Optional.ofNullable(lastName).orElse("Default");
		String assignDefault2 = Optional.ofNullable(lastName).orElseGet(() -> "Default");

		System.out.println("Default name: " + assignDefault);
		System.out.println("Second default name: " + assignDefault2);

		try {
			System.out.println("Nullable Name: " + nullableOptional.orElseThrow(IllegalArgumentException::new));
		} catch (IllegalArgumentException e) {
			System.out.println("orElseThrow example nullableOptional throw exception");
		}

		Student student = new Student("Eda", "Cam");

		Optional<Student> optionalStudent = Optional.of(student);
		System.out.println("Student's name is: " + optionalStudent.get().getName());
		System.out.println("Student's surname is: " + optionalStudent.get().getSurname());

		Optional<Student> optionalStudent2 = Optional.empty();

		if (optional.isPresent()) {
			System.out.println("Optional is present");
		}

		if (!nullableOptional.isPresent()) {
			System.out.println("Optional is not present");
		}

		if (!optional.isEmpty()) {
			System.out.println("Optional is not empty");
		}

		if (nullableOptional.isEmpty()) {
			System.out.println("Optional is empty");
		}

		optionalStudent.ifPresent(g -> System.out.println("Student exists."));
		optionalStudent2.ifPresent(g -> System.out.println("Second student exists."));
	}

}
