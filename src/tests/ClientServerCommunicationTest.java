package tests;

import model.Command;
import model.DisconnectCommand;
import model.SendServerMessageCommand;
import server.GameServer;
import client.GameClient;

import org.junit.Test;
import org.junit.Assert.*;


public class ClientServerCommunicationTest {
	
	@Test
	// MUST HAVE A SERVER RUNNING LOCALLY TO WORK
	public void test(){
		
		GameClient client = new GameClient("Tester1");
		
		Command<GameServer> command = new SendServerMessageCommand("Tester1: Test Message 1");
		client.sendCommand(command);
		
		// Safely disconnect from server
		command = new DisconnectCommand();
		client.sendCommand(command);
		
	}

}
