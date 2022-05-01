package dependencyInjection;

public class Automobile implements Car {
	Engine engine;
	
	 public Automobile(Engine engine) {
	        this.engine = engine;
	    }
	 
	@Override
	public void drive() {
		System.out.println(engine.start()); 
	}

}


