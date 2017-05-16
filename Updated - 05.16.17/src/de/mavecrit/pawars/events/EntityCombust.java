package de.mavecrit.pawars.events;

import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityCombust implements Listener{

	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		if (event.getEntity() instanceof Zombie) {
			event.setCancelled(true);
		}
	}
}
