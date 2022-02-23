package tools;

import java.util.ArrayList;
import java.util.List;

import cartago.Artifact;
import cartago.OPERATION;

public class Announce extends Artifact {

	List<String> History_calls= new ArrayList<String>();
	
public void init(int ae)
{
	defineObsProperty("room", "....");
}
@OPERATION
public void announce(String Sender,String V) {
	
	
	List<String> Room_list= new ArrayList<String>();
	List<String> Activity_stream= new ArrayList<String>();
	History_calls.add(Sender);



	Room_list.add("S423");
	Room_list.add("S424");
	
	Room_list.add("S422");
	Activity_stream.add("as:Offer");
	Activity_stream.add("as:Reject");
	Activity_stream.add("as:Accept");
/*
 * 
	Room_list.add("S431H");
	Room_list.add("S421");
	Room_list.add("S425");
	Room_list.add("S431F");
	Room_list.add("S405");
	Room_list.add("S424");
	Room_list.add("S416");
	Room_list.add("S432");
	Room_list.add("S408");
	*/
	
	//Choose random room
	int max_room = 1;
	int min_room = 0;
	int range = max_room - min_room + 1;
	
	int index_to_remove =Room_list.indexOf(Sender);
	Room_list.remove(index_to_remove);
	
	int rand_room = (int)(Math.random() * range) + min_room;
	
	
	
	Room_list.add(Sender);
	
	//Choose random Activity Stream
	
	int max_activity = 1;
	int min_activity = 0;
	int range_activity = max_activity - min_activity + 1;
	
	
	
	
	int rand_activity = (int)(Math.random() * range_activity) + min_activity;
	

	

      System.out.println("Hey "+Room_list.get(rand_room)+" What's your temperature?");
      
	defineObsProperty("subscribe"+Room_list.get(rand_room),V );
	
		
	}


		
}
