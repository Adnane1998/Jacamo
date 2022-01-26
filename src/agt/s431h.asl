// Agent sample_agent in project cps2_project

/* Initial beliefs and rules */

/* Initial goals */


!setup.

+!setup : true <-
	!setupCounter(Id);
	+mqttsubscriber(Id);
	.print(Id)
	!increment.
/* Plans */

+!increment : ready[source(Ag)] & mqttsubscriber(Id) <-
            .wait(1000);
	for (.range(I,1,100)) {
		.wait(1000);
		inc[artifact_id(Id)];
		.print("Subscribe request");
		}.
+!increment : not ready[source(Ag)] <-
	!increment.
+!setupCounter(C) : true <-
	makeArtifact("mqttsubscriber","tools.MqttSubscriber",[10],C);
	.broadcast(tell,artifact_counter_is(mqttsubscriber)).
{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
