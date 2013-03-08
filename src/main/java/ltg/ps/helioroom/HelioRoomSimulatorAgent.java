/**
 * 
 */
package ltg.ps.helioroom;

import java.math.BigInteger;
import java.security.SecureRandom;

import ltg.commons.LTGEventHandler;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author tebemis
 *
 */
public class HelioRoomSimulatorAgent {
	
	public static String[] colors = {"red", "blue", "brown", "pink", "gray", "green", "yellow", "orange", "purple"};
	public static String[] planets = {"mercury", "venus", "earth", "mars", "jupiter", "saturn", "uranus", "neptune", "pluto"};

	private LTGEventHandler eh = null;
	private int sleep = 2000/2;


	public HelioRoomSimulatorAgent(String usernameAndPass, String chatAndDBId) { 
		
		// -------------------
		// Init network and DB
		// -------------------
		eh = new LTGEventHandler(usernameAndPass+"@54.243.60.48", usernameAndPass, chatAndDBId+"@conference.54.243.60.48");
		
		// -----------------
		//Register listeners
		// -----------------

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runAsynchronously();

		
		// Now we can simulate the main thread  and make ourselves busy
		while(true) {
			try {
				generateRandomEvent();
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				eh.close();
			}
		}
	}


	private void generateRandomEvent() {
		double r = Math.random();
		if (r<0.5)
			generateUpdateEvent();
		else if (r <0.75) 
			generateCreateEvent();
		else 
			generateDeleteEvent();
	}


	private void generateCreateEvent() {
		BigInteger r = new BigInteger(130, new SecureRandom());
		String destination = r.toString(32).substring(0, 10) + "@54.243.60.48";
		String event;
		String color;
		String anchor;
		if (Math.random()<0.5) {
			event = "new_observation";
			color = colors[(int) (Math.random()*9)];
			anchor = colors[(int) (Math.random()*9)];
		} else {
			event = "new_theory";
			color = colors[(int) (Math.random()*9)];
			anchor = planets[(int) (Math.random()*9)];
		}
		String reason = r.toString(32).substring(5, (int) (Math.random()*80));
		ObjectNode payload =  JsonNodeFactory.instance.objectNode();
		payload.put("anchor", anchor);
		payload.put("color", color);
		payload.put("reason", reason);
		eh.generateEvent(event, destination, payload);
	}


	private void generateDeleteEvent() {
		//eh.generateEvent(event, destination, payload);	
	}


	private void generateUpdateEvent() {
		//eh.generateEvent(event, destination, payload);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HelioRoomSimulatorAgent(args[0], args[1]);
	}

}
