// Agent sample_agent in project cps2_project

/* Initial beliefs and rules */

/* Initial goals */


!setup.

+!setup : true <-
	!setupCounter(Id);
	+mqttsubscriber(Id);
	!gettemperature.
	
/* Plans */

+!gettemperature :mqttsubscriber(Id) <-
            .wait(1000);
	for (.range(I,1,2)) {
		.wait(10000);
		
		subscribe("emse/fayol/e4/S424/sensors/24a89ddc-23c8-4d9f-9f5e-cff4eba32fb5/metrics/TEMP","S424")
	    
		}.

	
	
+!observe(Name) : true <-
                       lookupArtifact(Name,Id);
                       focus(Id).
-!observe(Name) <- .print("Error in looking for artifact ", Name).

//+temperatureS424(V) <- .println("Agent observed new value ", V).
//+temperatureS424(V)[artifact_id(Id)] : V>60 <- stopFocus(Id).   	
	
	
	
	
	
	
	
	
	
	
	
+!setupCounter(C) : true <-
	makeArtifact("mqttsubscriber","tools.MqttSubscriber",[17],C);
	 !observe(mqttsubscriber).
 
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
