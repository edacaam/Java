package exceptioNHandling;

public class Main {

	public static void main(String[] args) {

		try {
			String[] cities = { "Amasya", "İstanbul", "İzmir" };
			cities[3] = "Eskişehir";
			System.out.println(cities[2]);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			System.out.println("This code always runs.");
		}

		ProductManager productManager = new ProductManager();
		try {
			productManager.add("Apple");
			productManager.add("a");
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}

	}

}
