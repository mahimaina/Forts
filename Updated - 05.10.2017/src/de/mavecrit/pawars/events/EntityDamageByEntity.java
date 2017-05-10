package de.mavecrit.pawars.events;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import net.minecraft.server.v1_11_R1.EntityZombie;

public class EntityDamageByEntity implements Listener{
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){
		Entity e1 = e.getEntity();
		Entity e2 = e.getDamager();
		
		if(e1 instanceof Zombie && e2 instanceof Zombie){
			EntityZombie cz1 = ((CraftZombie) e1).getHandle();
			EntityZombie cz2 = ((CraftZombie) e2).getHandle();
			if(cz1 instanceof CustomZombie && cz2 instanceof CustomZombie){
				if(cz2.getCustomName().contains(Main.getPlugin().getConfig().getString("Mobs.Zombies.Healer.Displayname"))) {	
					e.setCancelled(true);
				}
			}
		}
	}

}
