package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.lists.Zombies;
import net.minecraft.server.v1_11_R1.EntityZombie;

public class EntityDeath implements Listener{
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		if(e.getEntity() instanceof Zombie){
			List<Entity> temporary = new ArrayList<>();
			for(Entity en : e.getEntity().getNearbyEntities(2, 2, 2)){		
				if(en instanceof Zombie)
				temporary.add(en);
			}
			Entity ent = temporary.get(0);
			Zombie z = (Zombie) e.getEntity();
			Zombie z2 = (Zombie) ent;
			if(z.getTarget() != null){
				z.setTarget(null);
			}
			if(ent instanceof Zombie){
			if(z2.getTarget() != null){
				z2.setTarget(null);
			    temporary.clear();
			   }
			}
			
			EntityZombie cz = ((CraftZombie) e.getEntity()).getHandle();
			if(cz instanceof CustomZombie){
		
				for(Player p : Bukkit.getOnlinePlayers()){
					 for(int i = 0; i < 30; i++){
					if(Zombies.Units.get(p.getName() + ".Attack." + i) != null && Zombies.Units.get(p.getName() + ".Attack." + i).contains(cz)){
						if(Zombies.Units.get(p.getName() + ".Attack." + i).size() == 1){
							Zombies.Units.get(p.getName() + ".Attack." + i).clear();
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Spawning").replace("{type}", "Attack")));
						} else {
							Zombies.Units.get(p.getName() + ".Attack." + i).remove(cz);
						}
					}
					if(Zombies.Units.get(p.getName() + ".Healer." + i) != null && Zombies.Units.get(p.getName() + ".Healer." + i).contains(cz)){
						if(Zombies.Units.get(p.getName() + ".Healer." + i).size() == 1){
							Zombies.Units.get(p.getName() + ".Healer." + i).clear();
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Spawning").replace("{type}", "Healer")));
						    } else {
							Zombies.Units.get(p.getName() + ".Healer." + i).remove(cz);
						    }
					     }
					 }
				}
			}
		}
	}
}
