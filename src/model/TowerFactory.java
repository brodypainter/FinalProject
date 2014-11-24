package model;

import client.Player;
import GUI.GameView.towerType;
import GameController.CeruleanGym;
import GameController.Tower;

public class TowerFactory {

	public static Tower generateTower(towerType type, Player player){
		Tower tower;
		switch(type){
		case NORMAL:
			tower = new CeruleanGym(player.getName());
		case 
		default:
			tower = new CeruleanGym(player.getName());
		}
		return tower;
	}
}
