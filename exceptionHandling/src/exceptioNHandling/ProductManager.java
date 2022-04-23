package exceptioNHandling;

public class ProductManager {

	public void add(String name) throws BusinessException {
		if (name.length() < 2) {
			throw new BusinessException(name + " is invalid product name.");
		}
		System.out.println(name + " product added successfully.");
	}
}
