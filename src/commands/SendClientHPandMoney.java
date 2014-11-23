package commands;

import client.GameClient;

public class SendClientHPandMoney extends Command<GameClient>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895704955746837941L;
	int hp, money;
		
	public SendClientHPandMoney(int hp, int money){
		this.hp = hp;
		this.money = money;
	}
	
	@Override
	public void execute(GameClient executeOn) {
		//TODO need to execute on something in the game client
		//executeOn.updateHPandMoney(hp, money);
	}

}
