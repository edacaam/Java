package methodReference;

import java.util.List;

public class Car {

	public Car(String brand, Integer power) {
		this.brand = brand;
		this.power = power;
	}

	public Car(String brand) {
		this.brand = brand;
	}

	private String brand;
	private Integer power;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	static void listCars(List<Car> cars) {
		cars.forEach(c -> System.out.println(c.getBrand()));
	}
}
