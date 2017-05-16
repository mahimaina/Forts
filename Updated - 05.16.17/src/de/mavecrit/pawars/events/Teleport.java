package de.mavecrit.pawars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class Teleport implements Listener{

	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e){
		TeleportCause tc = e.getCause();
		if(tc.equals(TeleportCause.ENDER_PEARL)){
			if(Interact.c1.contains(e.getPlayer())){
				e.setCancelled(true);
			}
		}
	}
}
