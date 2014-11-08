package tests;

import server.GameServer;
import client.GameClient;

public class ClientServerCommunicationTest {
	GameServer server = new GameServer();
	GameClient client = new GameClient("Tester1");
	
	Command command = new SendServerMessageCommand("Tester1: Test Message 1");
	client.sendCommand(command);
}
