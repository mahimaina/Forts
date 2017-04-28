package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.util.Utils_unsorted;

public class BlockTask {
	
	public static HashMap<Location, Integer> blocklive = new HashMap<>();
	public static List<Block> blocks = new ArrayList<Block>();
	
	public static void damageBlocks(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for(Block b : blocks){
					if(blocklive.get(b.getLocation()) != null){
						for(Entity en : Utils_unsorted.getNearbyEntities(b.getLocation(), 2)){
							if(en instanceof Zombie){
								if(blocklive.get(b.getLocation()) > 5){
									int current = blocklive.get(b.getLocation());
									blocklive.put(b.getLocation(), (current - 5));
								} else {
									blocklive.remove(b.getLocation());
									b.setType(Material.AIR);
								}
								
							} else {
								blocklive.put(b.getLocation(), 100);
							}
						}
					}
				}
			  }
			}, 0, 20L);
	}
}
