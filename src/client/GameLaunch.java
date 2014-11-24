package client;

import GUI.GameView;
import server.GameServer;

/**
 * 
 * @author Max Justice
 * This method here will start the client and the server
 * 
 *  Also you could create a GameLaunch class
 * that will create a Server, create a client, connect them, and let the player actually play the game once that is ready.
 */

public class GameLaunch {
	
	GameServer myServer;
	GameClient myClient;
	
	public static void main (String argv[]){
		 new GameServer();
		// new GameView();
		 new GameClient();
		
	}
}
