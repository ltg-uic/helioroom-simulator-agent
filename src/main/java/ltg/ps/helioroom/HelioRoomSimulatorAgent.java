/**
 * 
 */
package ltg.ps.helioroom;

import ltg.commons.LTGEventHandler;

/**
 * @author tebemis
 *
 */
public class HelioRoomSimulatorAgent {

	private LTGEventHandler eh = null;
	private int sleep = 50;


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
		// TODO Auto-generated method stub
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new HelioRoomSimulatorAgent(args[0], args[1]);
	}

}
