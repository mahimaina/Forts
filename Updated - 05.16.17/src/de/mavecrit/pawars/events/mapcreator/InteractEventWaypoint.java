package de.mavecrit.pawars.events.mapcreator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.commands.ArenaCreate;
import de.mavecrit.pawars.item.CreaterItems;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.item.mapcreator.SetWaypoints;
import de.mavecrit.pawars.location.LocationGetter;

public class InteractEventWaypoint implements Listener {
		
		
		@EventHandler
		public void SetWaypoint(PlayerInteractEvent e){
			Player p = e.getPlayer();
			Location location = p.getLocation();
		    if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
		    	if(p.getItemInHand().equals(SetWaypoints.Waypoint())){
		            
		    		if(Main.locations.getString("arena_" + LocationGetter.Arenas.size() + ".waypoint_1") != null){
		    	    
		    		int fix = LocationGetter.waypoints.size();
		    		for (int i = 1; Main.locations.contains("arena_" + LocationGetter.Arenas.size() + ".waypoint_" + i); i++) {
		    			if(i + 1 == (fix + 1)){
		    			Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".waypoint_" + (i + 1), location.getWorld().getName() + "," + location.getBlockX()
						+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
						+ location.getYaw() + "," + location.getPitch()+ "," + "diamond");
						Main.locations.saveConfig();
						LocationGetter.waypoints.add(1);
		    			}
		    		}
		    		} else {
		    			Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".waypoint_" + 1, location.getWorld().getName() + "," + location.getBlockX()
						+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
						+ location.getYaw() + "," + location.getPitch()+ "," + "diamond");
						Main.locations.saveConfig();
						LocationGetter.waypoints.add(1);
		    		}
		    		
				    p.sendMessage("§aWaypoint " + LocationGetter.waypoints.size() + " added! §7Type: §adiamond");
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
		    	} else if(p.getItemInHand().equals(SetWaypoints.Waypoint2())){
		    		if(Main.locations.getString("arena_" + LocationGetter.Arenas.size() + ".waypoint_1") != null){
			    	    
			    		int fix = LocationGetter.waypoints.size();
			    		for (int i = 1; Main.locations.contains("arena_" + LocationGetter.Arenas.size() + ".waypoint_" + i); i++) {
			    			if(i + 1 == (fix + 1)){
			    			Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".waypoint_" + (i + 1), location.getWorld().getName() + "," + location.getBlockX()
							+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
							+ location.getYaw() + "," + location.getPitch()+ "," + "emerald");
							Main.locations.saveConfig();
							LocationGetter.waypoints.add(1);
			    			}
			    		}
			    		} else {
			    			Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".waypoint_" + 1, location.getWorld().getName() + "," + location.getBlockX()
							+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
							+ location.getYaw() + "," + location.getPitch()+ "," + "emerald");
							Main.locations.saveConfig();
							LocationGetter.waypoints.add(1);
			    		}
		    		  p.sendMessage("§aWaypoint " + LocationGetter.waypoints.size() + " added! §7Type: §aemerald");
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
		    	}else if(p.getItemInHand().equals(CreaterItems.Finish()) && p.getInventory().contains(Material.DIAMOND)){
		    		if(Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".waypoint_1") != null){
		    		    InteractArenaOutBorders.OutBorder.add(p);
		    			p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
		    		    p.getInventory().clear();
						p.getInventory().addItem(de.mavecrit.pawars.item.mapcreator.SetArenaWalls.Waypoint());
						p.getInventory().addItem(de.mavecrit.pawars.item.mapcreator.SetArenaWalls.Waypoint2());
						p.getInventory().addItem(CreaterItems.Finish());
						
						  p.sendMessage("§aPlease outborder the arena now");
		    		LocationGetter.waypoints.clear();
		    
		    		} else {
		    			p.sendMessage("§cAt least one location isn't setted");
		    		}
		    	}
		    }
		}
		
}
