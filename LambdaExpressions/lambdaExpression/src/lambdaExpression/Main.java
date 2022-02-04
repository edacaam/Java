package lambdaExpression;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		// Lambda expression is used to print the elements in the list.
		ArrayList<String> informations = new ArrayList<>();
		informations.add("Eda");
		informations.add("Computer Science");
		informations.add("Eskiþehir");
		informations.add("1999");

		informations.forEach((n) -> System.out.println(n));

		// Lambda expression can have zero or any number of arguments.
		Message message = () -> {
			return "Hello! Nice to see you.";
		};
		System.out.println(message.greeting());

		// with type declaration
		MathOperation addition = (int a, int b) -> a + b;

		// with out type declaration
		MathOperation subtraction = (a, b) -> a - b;

		// with return statement along with curly braces
		MathOperation multiplication = (int a, int b) -> {
			return a * b;
		};

		// without return statement and without curly braces
		MathOperation division = (int a, int b) -> a / b;

		System.out.println("20 + 10 = " + operate(20, 10, addition));
		System.out.println("20 - 10 = " + operate(20, 10, subtraction));
		System.out.println("20 x 10 = " + operate(20, 10, multiplication));
		System.out.println("20 / 10 = " + operate(20, 10, division));

	}

	private static int operate(int a, int b, MathOperation mathOperation) {
		return mathOperation.operation(a, b);
	}

}
