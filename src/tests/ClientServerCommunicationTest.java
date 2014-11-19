package tests;

import server.GameServer;
import client.GameClient;

import org.junit.*;

import commands.Command;
import commands.DisconnectCommand;
import commands.SendServerMessageCommand;

/**
 * *MUST RUN A SERVER SEPARATELY*
 * 
 * @author brodypainter
 *
 */
//TODO: Find a way to test for ticks. Possibly something to do with a tester version of GameClient?
public class ClientServerCommunicationTest {
	
	@Test
	// MUST HAVE A SERVER RUNNING LOCALLY TO WORK
	public void test(){
		
		GameClient client1 = new GameClient("Tester1");
		GameClient client2 = new GameClient("Tester2");
		
		Command<GameServer> command1 = new SendServerMessageCommand("Tester1: Test Message 1");
		client1.sendCommand(command1);
		
		Command<GameServer> command2 = new SendServerMessageCommand("Tester2: Test Message 2");
		client2.sendCommand(command2);
	
		// Safely disconnect from server
		command1 = new DisconnectCommand("Tester1");
		command2 = new DisconnectCommand("Tester2");
		client1.sendCommand(command1);
		client2.sendCommand(command2);
		
	}

}
