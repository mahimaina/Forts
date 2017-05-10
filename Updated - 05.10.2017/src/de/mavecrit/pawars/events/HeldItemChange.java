package de.mavecrit.pawars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.util.GlowAPI;

public class HeldItemChange implements Listener{
	
	
	@EventHandler
	public void onChange(PlayerItemHeldEvent e){
		int slot = e.getNewSlot();
		int old_slot = e.getPreviousSlot();
		Player p = e.getPlayer();

		    if(Zombies.Units.get(p.getName() + ".Attack." + slot) != null){
		    	for(CustomZombie cz :Zombies.Units.get(p.getName() + ".Attack." + slot)){
				 	 GlowAPI.setGlowing(cz.getBukkitEntity(), GlowAPI.Color.PURPLE, p);
			     }
		    } else if(Zombies.Units.get(p.getName() + ".Healer." + slot) != null){
		    	for(CustomZombie cz :Zombies.Units.get(p.getName() + ".Healer." + slot)){
				 	 GlowAPI.setGlowing(cz.getBukkitEntity(), GlowAPI.Color.PURPLE, p);
			     }
		    }

		    if(Zombies.Units.get(p.getName() + ".Attack." + old_slot) != null){
		    	for(CustomZombie cz :Zombies.Units.get(p.getName() + ".Attack." + old_slot)){
				 	 GlowAPI.setGlowing(cz.getBukkitEntity(), false, p);
			     }
		    } else if(Zombies.Units.get(p.getName() + ".Healer." + old_slot) != null){
		    	for(CustomZombie cz :Zombies.Units.get(p.getName() + ".Healer." + old_slot)){
				 	 GlowAPI.setGlowing(cz.getBukkitEntity(), false, p);
			     }
		    }
			
		
	}

}
