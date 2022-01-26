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

public class MqttSubscriber extends Artifact implements MqttCallback  {

	/** The broker url. */
	private static final String brokerUrl ="tcp://193.49.165.77";
    public String temperature;
	
	
	/** The client id. */
	private static final String clientId = "clientId";

	/** The topic. */
	private static final String topic = "emse/fayol/e4/S424/sensors/24a89ddc-23c8-4d9f-9f5e-cff4eba32fb5/metrics/TEMP";



	public void subscribe(String topic) {
		//	logger file name and pattern to log
		MemoryPersistence persistence = new MemoryPersistence();

		try
		{

			MqttClient sampleClient = new MqttClient(brokerUrl, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			System.out.println("checking");
			System.out.println("Mqtt Connecting to broker: " + brokerUrl);

			sampleClient.connect(connOpts);
			System.out.println("Mqtt Connected");

			sampleClient.setCallback(this);
			sampleClient.subscribe(topic);

			System.out.println("Subscribed");
			System.out.println("Listening");

		} catch (MqttException me) {
			System.out.println(me);
		}
	}

	 //Called when the client lost the connection to the broker
	public void connectionLost(Throwable arg0) {
		
	}

	//Called when a outgoing publish is complete
	

	public void messageArrived(String topic, MqttMessage message) throws Exception {

		System.out.println("| Topic:" + topic);
		System.out.println("| Message: " +message.toString());
		System.out.println("-------------------------------------------------");
		this.temperature=topic;

	}
	

	

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	void init(int initialValue) {
		System.out.println("Subscriber running");

		defineObsProperty("temperature", initialValue);
	}
	@OPERATION
	void inc() {
		new MqttSubscriber().subscribe(topic);
		ObsProperty prop = getObsProperty("temperature");
		
		signal("tick");
	}
	
	
	
	
	
}