package tools;

import java.util.ArrayList;
import java.util.List;

import cartago.Artifact;
import cartago.OPERATION;

public class Response extends Artifact{
	List<String> Room_list= new ArrayList<String>();
	List<String> Activity_stream= new ArrayList<String>();
	List<String> temperature= new ArrayList<String>();
	public void init(int ae)
	{
		defineObsProperty("room", "....");
	}@OPERATION
	public void response(String Sender,String V,String Reciever) {
		

		Room_list.add("S423");
		Room_list.add("S424");
		
		Room_list.add("S422");
		Activity_stream.add("as:Reject");
		Activity_stream.add("as:Accept");
		temperature.add("hot");
		temperature.add("mild");
		temperature.add("cold");
		int max_activity = 1;
		int min_activity = 0;
		int range_activity = max_activity - min_activity + 1;
		
		
		
		
		int rand_activity = (int)(Math.random() * range_activity) + min_activity;
		int index_to_remove_sender =Room_list.indexOf(Sender);
		int index_to_remove_reciever =Room_list.indexOf(Reciever.toUpperCase());

		Room_list.remove(index_to_remove_sender);
		Room_list.remove(index_to_remove_reciever);
	
		int max_room = Room_list.size()-1;
		int min_room = 0;
		int range = max_room - min_room + 1;
		int rand_room = (int)(Math.random() * range) + min_room;
	   
		
		if(Activity_stream.get(rand_activity) == "as:Accept")
		{   System.out.println("Offer Accepted");
			System.out.println("Hey"+Room_list.get(rand_room)+"What is your Temperature?");
			
			defineObsProperty("subscribe"+Room_list.get(rand_room),V );
			
		}
		
		
		if(Activity_stream.get(rand_activity) == "as:Reject")
		{    
			
			
			System.out.println(Room_list.get(rand_room));
			System.out.println("Offer Rejected");
			if(V !="cold" )
			{
			System.out.println("is it cold?");
			defineObsProperty("subscribe"+Room_list.get(rand_room),"cold");
			}
			else if(V !="mild" )
			{
			System.out.println("is it mild?");
			defineObsProperty("subscribe"+Room_list.get(rand_room),"mild" );
			}
			else
			{
			System.out.println("is it hot?");
			defineObsProperty("subscribe"+Room_list.get(rand_room),"hot" );
			}
			
		}
		
			
		}

}
