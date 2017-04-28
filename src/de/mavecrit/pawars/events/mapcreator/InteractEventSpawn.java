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

public class InteractEventSpawn implements Listener{
	
	@EventHandler
	public void SetSpawn(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Location location = p.getLocation();
	    if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
	    	if(p.getItemInHand().equals(de.mavecrit.pawars.item.mapcreator.SetSpawn.BlueSpawn())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".spawn.blue", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				p.sendMessage("§9Setted blue spawn");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
	    	} else if(p.getItemInHand().equals(de.mavecrit.pawars.item.mapcreator.SetSpawn.RedSpawn())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".spawn.red", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				p.sendMessage("§cSetted red spawn");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
	    	}else if(p.getItemInHand().equals(de.mavecrit.pawars.item.mapcreator.SetSpawn.GreenSpawn())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".spawn.green", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				p.sendMessage("§aSetted green spawn");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
	    	}else if(p.getItemInHand().equals(de.mavecrit.pawars.item.mapcreator.SetSpawn.YellowSpawn())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".spawn.yellow", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				p.sendMessage("§eSetted yellow spawn");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
	    	}else if(p.getItemInHand().equals(CreaterItems.Finish()) && p.getInventory().contains(Material.WOOL)){
	    		if(Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".spawn.yellow") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".spawn.red") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".spawn.blue") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".spawn.green") != null){
	    		   p.sendMessage("§aAlright, there you go!");
	    		   p.sendMessage("§7Now please set the Pou location");
	    		   
	    			p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
	    		    p.getInventory().clear();
					p.getInventory().addItem(de.mavecrit.pawars.item.mapcreator.SetPou.BluePou());
					p.getInventory().addItem(de.mavecrit.pawars.item.mapcreator.SetPou.RedPou());
					p.getInventory().addItem(de.mavecrit.pawars.item.mapcreator.SetPou.GreenPou());
					p.getInventory().addItem(de.mavecrit.pawars.item.mapcreator.SetPou.YellowPou());
					p.getInventory().addItem(CreaterItems.Finish());
	    		} else {
	    			p.sendMessage("§cAt least one location isn't setted");
	    		}
	    	}
	    }
	}
	
}
