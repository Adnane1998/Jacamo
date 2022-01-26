+artifact_counter_is(Name)[source(Sender)] : true <-
                                           .println("Ready")
                                           .println(Sender)
                                           .send(Sender,tell,ready);
                                           !observe(Name);
                                           .
+!observe(Name) : true <-
                       lookupArtifact(Name,Id);
                       focus(Id).
-!observe(Name) <- .print("Error in looking for artifact ", Name).

+mqttsubscriber(V) : V<=60 <- .println("observed new value ", V).
+mqttsubscriber(V)[artifact_id(Id)] : V>60 <- stopFocus(Id).   

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
                                                         