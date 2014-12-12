package commands;

import client.GameClient;

public class ClientHPandMoney extends Command<GameClient>{
	
	private static final long serialVersionUID = 6895704955746837941L;
	int hp, money;
	boolean fromPlayer1;
		
	public ClientHPandMoney(int hp, int money, boolean fromPlayer1){
		this.hp = hp;
		this.money = money;
		this.fromPlayer1 = fromPlayer1;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		//TODO need to execute on something in the game client
		executeOn.updateHPandMoney(hp, money, fromPlayer1);
	}

}
