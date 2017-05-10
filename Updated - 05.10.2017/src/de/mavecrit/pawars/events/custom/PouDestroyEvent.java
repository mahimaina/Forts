package de.mavecrit.pawars.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class PouDestroyEvent extends Event{

	Player p;
	String team;
	
    private static final HandlerList handlers = new HandlerList();
	
	public PouDestroyEvent(Player p, String team){
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

