package dependencyInjection;

public class DieselEngine implements Engine {

	@Override
	public String start() {
		 return "Diesel engine started.";
	}

	@Override
	public String stop() {
		 return "Diesel engine stopped.";
	}

}

