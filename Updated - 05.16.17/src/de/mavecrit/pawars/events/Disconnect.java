package de.mavecrit.pawars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.booleans.TeamsB;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.teams.TeamList;

public class Disconnect implements Listener{
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		if(GameBool.isStarted()){
			Alive.Alive.remove(p);
			if(Alive.Spectator.contains(p)){
				Alive.Spectator.remove(p);
			}
			if(TeamList.blue.contains(p)){
				TeamList.blue_alive.remove(p);
				if(TeamList.blue_alive.size() == 0){
					TeamsB.blue = false;
					TeamsB.state.remove("blue");
				}
				TeamList.blue.remove(p);}
			if(TeamList.green.contains(p)){
				TeamList.green_alive.remove(p);
				if(TeamList.green_alive.size() == 0){
					TeamsB.green = false;
					TeamsB.state.remove("green");
				}
				TeamList.green.remove(p);}
			if(TeamList.red.contains(p)){
				TeamList.red_alive.remove(p);
				if(TeamList.red_alive.size() == 0){
					TeamsB.red = false;
					TeamsB.state.remove("red");
				}
				TeamList.red.remove(p);}
			if(TeamList.yellow.contains(p)){
				TeamList.yellow_alive.remove(p);
				if(TeamList.yellow_alive.size() == 0){
					TeamsB.yellow = false;
					TeamsB.state.remove("yellow");
				}
				TeamList.yellow.remove(p);}
			
			if(TeamsB.state.size() == 1){
				GameInstance.end();
			}
		}
	}
	

}
