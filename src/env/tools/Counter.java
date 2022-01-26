package tools;


import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
public class Counter extends Artifact  {


	void init(int initialValue) {
		System.out.println("Subscriber running");

		defineObsProperty("temperature", initialValue);
	}
	@OPERATION
	void inc() {
		
		ObsProperty prop = getObsProperty("temperature");
		
		signal("tick");
	}
}
