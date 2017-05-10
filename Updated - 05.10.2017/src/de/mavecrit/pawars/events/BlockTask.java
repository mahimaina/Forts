package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.util.ParticleEffect;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.EntityZombie;


public class BlockTask {
	
	public static HashMap<Location, Integer> blocklive = new HashMap<>();
	public static List<Block> blocks = new ArrayList<Block>();

	public static void damageBlocks(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for(Block b : blocks){
					if(blocklive.get(b.getLocation()) != null){
						World w = b.getWorld();
						for(Entity en : w.getEntities()){
							if(en instanceof Zombie){
								EntityZombie cz = ((CraftZombie) en).getHandle();
								if (cz instanceof CustomZombie) {
									if (cz.getCustomName().contains(Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))) {	
										if(en.getLocation().distance(b.getLocation()) < 2){
											if(blocklive.get(b.getLocation()) != null && blocklive.get(b.getLocation()) > 5){
												int current = blocklive.get(b.getLocation());
												blocklive.put(b.getLocation(), (current - 5));
												ParticleEffect.SMOKE_NORMAL.display(0.1F, 0.1F, 0.1F, 0.1F, 10,
														b.getLocation(), 20); 
											} else {
												blocklive.remove(b.getLocation());
												b.setType(Material.AIR);
												for(Player near : Utils_unsorted.getNearbyPlayers(b.getLocation(), 10)){
														Player p = near;
														p.playSound(b.getLocation(),Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1f, 0.3f);
														ParticleEffect.SMOKE_NORMAL.display(0.5F, 0.5F, 0.5F, 0.5F, 10,
																b.getLocation(), 20); 	
												}
											}
										} else {
											if(blocklive.get(b.getLocation()) != null){
											int current = blocklive.get(b.getLocation());
											blocklive.put(b.getLocation(), (current + 5));
											}
										  }
										}
									}
								}
							}
						}		
			    	}
			  }
			}, 0, 20L);
	}
}
