package tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.ibm.icu.impl.duration.TimeUnit;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
import jason.stdlib.list;
public class MqttSubscriber extends Artifact implements MqttCallback   {

	/** The broker url. */
	private static final String brokerUrl ="tcp://localhost";

	/** The client id. */
	private static final String clientId = "clientId";
    public int temperature;
	
  public String number ="";
	List <String> isalreadyprocessed =new ArrayList<String>();
 public  int isdone =0;
	/** The topic. */
	private static final String topic = "temperature";

	   int i=1;
	void init(String topic,String number) {
		defineObsProperty("temperature"+this.number,2.00 );
       
	
		
	}
	@OPERATION
	public void subscribe(String topic,String number) {
	    
    
        i=1;
		this.number=number;
	
		
		if( !isalreadyprocessed.contains(number))
		{
			this.isdone= 0;
		}
		
		
		//	logger file name and pattern to log
		MemoryPersistence persistence = new MemoryPersistence();

		try
		{

			MqttClient sampleClient = new MqttClient(brokerUrl,number,persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

		

			sampleClient.connect(connOpts);
			
			sampleClient.setCallback(this);
			sampleClient.subscribe(topic);
		
		  if(this.isdone == 1 && number != isalreadyprocessed.get(0))
		  {
		   sampleClient.disconnect();
			
		  }
          
		} catch (MqttException me) {
			
		}
	}

	 //Called when the client lost the connection to the broker
	public void connectionLost(Throwable arg0) {
		
	}

	//Called when a outgoing publish is complete
	public void deliveryComplete(IMqttDeliveryToken arg0) {

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
         
       
          float v=Float.parseFloat(message.toString());
   
         defineObsProperty("temperature"+this.number,v );

         this.isdone = 1;
         isalreadyprocessed.add(number);
        
       
	
        

	}

}