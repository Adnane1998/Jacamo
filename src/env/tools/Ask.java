package tools;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;

public class Ask extends Artifact {

	void init(int ae) {

		
	
		defineObsProperty("room", "....");
		
	}
	@OPERATION
	void inc(String room,String number) {
		defineObsProperty("temperature"+number, "...");
		System.out.println(room);
		MqttSubscriber subscriber1 =new MqttSubscriber();
		  		// subscriber1.subscribe(room,number);

		ObsProperty prop = getObsProperty("temperature"+number);
	
		
       
       // prop.updateValue(MqttSubscriber.temperature);
        
       
	}
	
}
