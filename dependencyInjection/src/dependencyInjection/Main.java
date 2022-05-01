package dependencyInjection;

public class Main {

	public static void main(String[] args) {
		System.out.println(sum(1,2));
		System.out.println(sum(1.5,2.5));
		System.out.println(sum(1,2,3));
		System.out.println(sum(1L,2L));
	}
	
	private static int sum(int a, int b)
	{
		return a+b;
	}
	
	private static double sum(double a, double b)
	{
		return a+b;
	}
	
	private static int sum(int a, int b, int c)
	{
		return a+b+c;
	}
	
	private static String sum(long a, long b)
	{
		return "Sum of the numbers is : "+ (a+b);
	}
}


