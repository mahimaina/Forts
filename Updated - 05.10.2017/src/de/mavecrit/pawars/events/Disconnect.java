package de.mavecrit.pawars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.mavecrit.pawars.booleans.GameBool;
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
				TeamList.blue.remove(p);}
			if(TeamList.green.contains(p)){
				TeamList.green.remove(p);}
			if(TeamList.red.contains(p)){
				TeamList.red.remove(p);}
			if(TeamList.yellow.contains(p)){
				TeamList.yellow.remove(p);}
		}
	}
	

}
