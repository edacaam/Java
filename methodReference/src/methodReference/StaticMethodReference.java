package methodReference;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Addition {
	int add(int a, int b);
}

public class StaticMethodReference {
	static int sum(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {

		Addition addition = StaticMethodReference::sum;
		int result = addition.add(8, 12);
		System.out.println("Addition is : " + result);

		List<Integer> numbers = Arrays.asList(1, 9, 15, 14, 78, 5);

		numbers.stream().sorted((a, b) -> a.compareTo(b));
		numbers.stream().sorted(Integer::compareTo);

		CarComparator powerComparator = new CarComparator();
		List<Car> carList = Arrays.asList(new Car("BMW", 100), new Car("Mercedes", 200), new Car("Nissan", 150));
		carList.stream().sorted((a, b) -> powerComparator.compare(a, b));
		carList.stream().sorted(powerComparator::compare);

		List<String> carBrands = Arrays.asList("BMW", "Mercedes", "Toyota", "Volkswagen");

		carBrands.stream().map(Car::new).toArray(Car[]::new);

		// Static method example
		Car.listCars(carList);
	}

}
