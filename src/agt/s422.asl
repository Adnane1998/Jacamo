// Agent sample_agent in project cps2_project

/* Initial beliefs and rules */

/* Initial goals */


!setup.

+!setup : true <-
	!setupCounters(Id);
	+s422(Id);
	!gettemperature.
	
/* Plans */

+!gettemperature : s422(Id) <-
            .wait(1000);
	for (.range(I,1,2)) {
		.wait(10000);
		subscribe("emse/fayol/e4/Hall4Nord/sensors/757e0b46-0efe-4f36-bf2c-e8008e49d950/metrics/TEMP","S422");
		
		
		}.

	
	

	
+!observe(Name) : true <-
                       lookupArtifact(Name,Id);
                     
                       focus(Id).
-!observe(Name) <- .print("Error in looking for artifact ", Name).


+temperatureS422(V) <- .println("The temperature in S422 is ", V);
                       if(V<18.00) { .print("It's cold");}
                       if(V>18.00 & V<24.00) {.print("mild");}
                       if(V>24.00) {.print("hot");}.
           
                       
                     

+temperatureS422(V)[artifact_id(Id)] : V>60 <- stopFocus(Id).   

	
	
	
+!announce(v) :true <- .broadcast(tell,artifact_counter_is(mqttsubscriber)). 	
	
	
	
	
	
	
	
+!setupCounters(D) : true <-
	makeArtifact("s422","tools.MqttSubscriber",[12],D);
	 !observe(s422).
	/* .broadcast(tell,artifact_counter_is(mqttsubscriber)). */
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
