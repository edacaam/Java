package functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {

	public static void main(String[] args) {
		// Predicate example
		List<String> words = Arrays.asList("Turkcell", "Gelecegi", "Yazanlar", "Java", "Bootcamp");
		Predicate<String> wordStartsWithT = str -> str.startsWith("T");
		words.stream().filter(wordStartsWithT).forEach(System.out::println);

		// Supplier example
		Supplier<Double> doubleSupplier1 = () -> Math.random();
		DoubleSupplier doubleSupplier2 = Math::random;

		System.out.println(doubleSupplier1.get());
		System.out.println(doubleSupplier2.getAsDouble());

		// Consumer example
		Consumer<String> consumerString = s -> System.out.println(s);
		consumerString.accept("Eda");

		Consumer<String> first = x -> System.out.println(x.toLowerCase());
		Consumer<String> second = y -> System.out.println(y.toUpperCase());

		Consumer<String> result = first.andThen(second);
		result.accept("Eda Çam");

	}

}
