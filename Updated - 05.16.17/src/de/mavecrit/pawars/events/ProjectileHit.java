package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.item.Empty;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.teams.ZombieEnum;
import de.mavecrit.pawars.util.Schedular;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.World;

public class ProjectileHit implements Listener {
	
	public int countdown = Main.getPlugin().getConfig().getInt("General.ItemCooldown");
	
	
	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		ProjectileSource ps = e.getEntity().getShooter();
		Projectile pro = e.getEntity();
		
		if(pro instanceof Egg){
		if (ps instanceof Player) {
			Player p = (Player) ps;
			int slot = p.getInventory().getHeldItemSlot();
			for(int num = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + num + ".EggColor"); num++){
			if (p.getItemInHand().equals(SpawnZombies.Zombie(num))) {
				
				if(Main.getPlugin().getConfig().getString("Items.Zombies." + num + ".Assigned") !=null)
				if(Main.getPlugin().getConfig().getString("Items.Zombies." + num + ".Assigned").equals("Attack")){
				
				if(Zombies.Units.get(p.getName() + ".Attack." + slot) == null || Zombies.Units.get(p.getName() + ".Attack." + slot).size() == 0){
				Location loc = e.getEntity().getLocation();
				World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
				List<CustomZombie> list = new ArrayList<>();
				
				final int numerial = num;				
				for (int i = 0; i < Main.getPlugin().getConfig().getInt("General.MobGroupSize"); i++) {
					CustomZombie skele = new CustomZombie(nmsWorld);
				
					skele.setPosition(loc.getX(), loc.getY(), loc.getZ());
					skele.h(p.getLocation().getYaw());
					skele.i(p.getLocation().getYaw());		
					skele.setCustomName(Utils_unsorted.getTeamColor(p) + Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"));
					skele.setCustomNameVisible(true);
					skele.setSilent(true);				
					nmsWorld.addEntity(skele);							
			//		skele.goalSelector.a(3, new PathfinderGoalWalkToLoc(skele, 0.28d, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()));			
					list.add(skele);
					if(Utils_unsorted.getTeamColor(p).equals("§9")){
						Zombies.teams.put(skele, ZombieEnum.BLUE);
					}
					if(Utils_unsorted.getTeamColor(p).equals("§2")){
						Zombies.teams.put(skele, ZombieEnum.GREEN);
					}
					if(Utils_unsorted.getTeamColor(p).equals("§4")){
						Zombies.teams.put(skele, ZombieEnum.RED);
					}
					if(Utils_unsorted.getTeamColor(p).equals("§6")){
						Zombies.teams.put(skele, ZombieEnum.YELLOW);		
					}
					Zombies.location.put(skele, loc);
					if(i == (Main.getPlugin().getConfig().getInt("General.MobGroupSize") -1)){
				
						 Zombies.zombies.put(loc, list);	
						 Zombies.Units.put(p.getName() + ".Attack." + slot, list);
					   }
				     }	 
			
				 p.getInventory().setItem(slot, Empty.Empty(p));
				 p.getInventory().getItem(slot).getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.Empty.Displayname").replace("{time}", "20")));
				 p.getInventory().getItem(slot).setItemMeta(p.getInventory().getItem(slot).getItemMeta());
				 new Schedular(Main.getPlugin(), countdown, p, slot, numerial).runTaskTimer(Main.getPlugin(), 0, 20);
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.Units").replace("{count}", "" + Zombies.Units.get(p.getName() + ".Attack." + slot).size())));
				}
				   } else if(Main.getPlugin().getConfig().getString("Items.Zombies." + num + ".Assigned").equals("Healer")){
						if(Zombies.Units.get(p.getName() + ".Healer." + slot) == null || Zombies.Units.get(p.getName() + ".Healer." + slot).size() == 0){
							Location loc = e.getEntity().getLocation();
							World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
							List<CustomZombie> list = new ArrayList<>();
							
							final int numerial = num;				
							for (int i = 0; i < Main.getPlugin().getConfig().getInt("General.MobGroupSize"); i++) {
								CustomZombie skele = new CustomZombie(nmsWorld);
							
								skele.setPosition(loc.getX(), loc.getY(), loc.getZ());
								skele.h(p.getLocation().getYaw());
								skele.i(p.getLocation().getYaw());		
								skele.setCustomName(Utils_unsorted.getTeamColor(p) + Main.getPlugin().getConfig().getString("Mobs.Zombies.Healer.Displayname"));
								skele.setCustomNameVisible(true);
								skele.setSilent(true);				
								nmsWorld.addEntity(skele);							
						//		skele.goalSelector.a(3, new PathfinderGoalWalkToLoc(skele, 0.28d, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()));			
								list.add(skele);
								if(Utils_unsorted.getTeamColor(p).equals("§9")){
									Zombies.teams.put(skele, ZombieEnum.BLUE);
								}
								if(Utils_unsorted.getTeamColor(p).equals("§2")){
									Zombies.teams.put(skele, ZombieEnum.GREEN);
								}
								if(Utils_unsorted.getTeamColor(p).equals("§4")){
									Zombies.teams.put(skele, ZombieEnum.RED);
								}
								if(Utils_unsorted.getTeamColor(p).equals("§6")){
									Zombies.teams.put(skele, ZombieEnum.YELLOW);		
								}
								Zombies.location.put(skele, loc);
								if(i == (Main.getPlugin().getConfig().getInt("General.MobGroupSize") -1)){
									 Zombies.zombies.put(loc, list);	
									 Zombies.Units.put(p.getName() + ".Healer." + slot, list);
								   }
							     }	 
						
							 p.getInventory().setItem(slot, Empty.Empty(p));
							 p.getInventory().getItem(slot).getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.Empty.Displayname").replace("{time}", "20")));
							 p.getInventory().getItem(slot).setItemMeta(p.getInventory().getItem(slot).getItemMeta());
							 new Schedular(Main.getPlugin(), countdown, p, slot, numerial).runTaskTimer(Main.getPlugin(), 0, 20);
							} else {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.Units").replace("{count}", "" + Zombies.Units.get(p.getName() + ".Healer." + slot).size())));
							}    
				      }
			    }
			  }
		   }
		}
	}

}
