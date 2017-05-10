package de.mavecrit.pawars.events.mapcreator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.item.CreaterItems;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.item.mapcreator.SetShops;
import de.mavecrit.pawars.item.mapcreator.SetWaypoints;
public class InteractEventShop implements Listener{
	
	
	@EventHandler
	public void SetShop(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Location location = p.getLocation();
	    if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
	    	if(p.getItemInHand().getItemMeta().getDisplayName().equals(SetShops.BlueShop().getItemMeta().getDisplayName())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".shop.blue", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				
				p.sendMessage("§9Setted blue shop");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	} else if(p.getItemInHand().getItemMeta().getDisplayName().equals(SetShops.RedShop().getItemMeta().getDisplayName())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".shop.red", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				
				p.sendMessage("§cSetted red shop");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	}else if(p.getItemInHand().getItemMeta().getDisplayName().equals(SetShops.GreenShop().getItemMeta().getDisplayName())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".shop.green", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				
				p.sendMessage("§aSetted green shop");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	}else if(p.getItemInHand().getItemMeta().getDisplayName().equals(SetShops.YellowShop().getItemMeta().getDisplayName())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".shop.yellow", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
			
				p.sendMessage("§eSetted yellow shop");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	}else if(p.getItemInHand().equals(CreaterItems.Finish()) && p.getInventory().contains(Material.POTION)){
	    		if(Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".shop.yellow") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".shop.red") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".shop.blue") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".shop.green") != null){
	    		
	    			
	    		p.sendMessage("§aAlright, there you go!");
	    	    p.sendMessage("§7Now please set the waypoints");
	    		   
	    		p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
	    		
	    		p.getInventory().clear();
			    p.getInventory().addItem(SetWaypoints.Waypoint());
			    p.getInventory().addItem(SetWaypoints.Waypoint2());
				p.getInventory().addItem(CreaterItems.Finish());
	    		} else {
	    			p.sendMessage("§cAt least one location isn't setted");
	    		}
	    	}
	    }
	}
	
}
