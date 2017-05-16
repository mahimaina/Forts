package de.mavecrit.pawars.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.item.Empty;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.util.GlowAPI;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.EntityZombie;

public class EntityDamage implements Listener{
	/*@EventHandler
	public void onClick(EntityDamageByEntityEvent e){
		Entity en = e.getEntity();
		if(e.getDamager() instanceof Player){
		Player p = (Player) e.getDamager();
		int slot = p.getInventory().getHeldItemSlot();
		if(!Interact.c.contains(p)){
		if(en instanceof Zombie){
			Zombie z = (Zombie) en;
			EntityZombie cz = ((CraftZombie) z).getHandle();
			if(cz instanceof CustomZombie){
				CustomZombie c = (CustomZombie) cz;
				if(cz.getCustomName().equals(Utils_unsorted.getTeamColor(p) + Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))){		
					for(int num = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + num + ".EggColor"); num++){
						if (p.getItemInHand().equals(SpawnZombies.Zombie(num)) || p.getItemInHand().equals(Empty.Empty(p))) {
							if(Main.getPlugin().getConfig().getString("Items.Zombies." + num + ".Assigned").equals("Attack")){
								if(Zombies.Units.get(p.getName() + ".Attack." + slot).contains(cz)){
									for(CustomZombie glow : Zombies.Units.get(p.getName() + ".Attack." + slot)){
									 if(!Interact.cooldown.contains(glow)){
								   	 Interact.cooldown.add(glow);
									 } else {
								     Interact.cooldown.remove(glow); 
									   }
									}
									 e.setCancelled(true);
									 if(!Interact.cooldown.contains(c)){
									   	 Interact.cooldown.add(c);
									   	 p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Deactivated")));
										 } else {
									     Interact.cooldown.remove(c); 
									   	 p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Activated")));
										 }
									
									 
								} else {
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.NotYours")));
								}
							} else if(Main.getPlugin().getConfig().getString("Items.Zombies." + num + ".Assigned").equals("Healer")){
								if(Zombies.Units.get(p.getName() + ".Healer." + slot).contains(cz)){
									for(CustomZombie glow : Zombies.Units.get(p.getName() + ".Healer." + slot)){
									 if(!Interact.cooldown.contains(glow)){
								   	 Interact.cooldown.add(glow);
									 } else {
								     Interact.cooldown.remove(glow); 
									   }
									}
									 e.setCancelled(true);
									 if(!Interact.cooldown.contains(c)){
									   	 Interact.cooldown.add(c);
									   	 p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Deactivated")));
										 } else {
									     Interact.cooldown.remove(c); 
									   	 p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Activated")));
										 }
									
									 
								} else {
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.NotYours")));
								}
							}
						}
					}
				 }
			  }
		    }
		  }
		}
	  }*/
	
}
