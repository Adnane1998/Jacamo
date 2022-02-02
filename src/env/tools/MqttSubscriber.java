package tools;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.ObsProperty;
public class MqttSubscriber extends Artifact implements MqttCallback   {

	/** The broker url. */
	private static final String brokerUrl ="tcp://193.49.165.77";

	/** The client id. */
	private static final String clientId = "clientId";
    public int temperature;
	
  public String number ="";


	/** The topic. */
	private static final String topic = "emse/fayol/e4/S424/sensors/24a89ddc-23c8-4d9f-9f5e-cff4eba32fb5/metrics/TEMP";
   int i=1;

	void init(int ae) {

		
		
		defineObsProperty("room", "....");
		
	}
	@OPERATION
	public void subscribe(String topic,String number) {

		this.number=number;
		//	logger file name and pattern to log
		MemoryPersistence persistence = new MemoryPersistence();

		try
		{

			MqttClient sampleClient = new MqttClient(brokerUrl, clientId);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

		

			sampleClient.connect(connOpts);
			
			sampleClient.setCallback(this);
			sampleClient.subscribe(topic);

			
          
		} catch (MqttException me) {
			System.out.println(me);
		}
	}

	 //Called when the client lost the connection to the broker
	public void connectionLost(Throwable arg0) {
		
	}

	//Called when a outgoing publish is complete
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {

        if(i==1)
        {  
        	


		defineObsProperty("temperature"+this.number, Float.parseFloat(message.toString()));

      i++;
       
	
        }

	}

}
