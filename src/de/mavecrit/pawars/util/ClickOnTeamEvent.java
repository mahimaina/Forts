package de.mavecrit.pawars.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClickOnTeamEvent extends Event {
	
	Player p;
	String team;
	
    private static final HandlerList handlers = new HandlerList();
	
	public ClickOnTeamEvent(Player p, String team){
		this.p = p;
		this.team = team;
		
	}
	
	public Player getPlayer(){
		return this.p;
	}
	
	
	public String getTeam(){
		return this.team;
	}
  
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
