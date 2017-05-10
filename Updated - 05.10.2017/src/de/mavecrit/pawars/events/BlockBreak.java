package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.events.custom.PouDestroyEvent;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.location.LocationGetter;
import net.md_5.bungee.api.ChatColor;

public class BlockBreak implements Listener{
	
	public static List<Material> breakable = new ArrayList<Material>();
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		Location y = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.yellow");
		Location b = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.blue");
		Location g = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.green");
		Location r = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.red");
		
		Location yellow = new Location(y.getWorld(),y.getX(),y.getY(),y.getZ(),0,0);
		Location blue = new Location(b.getWorld(),b.getX(),b.getY(),b.getZ(),0,0);
		Location red = new Location(r.getWorld(),r.getX(),r.getY(),r.getZ(),0,0);
		Location green = new Location(g.getWorld(),g.getX(),g.getY(),g.getZ(),0,0);
		
		if(!breakable.contains(e.getBlock().getType())){
			if(!e.getPlayer().hasPermission("Server.Admin")){
				if(e.getBlock().getType() != Material.valueOf(Main.getPlugin().getConfig().getString("General.PouBlock"))){
				    e.setCancelled(true);
				   }	
		    } 
		}

			if(e.getBlock().getLocation().equals(yellow)){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.PouDestroyed").replace("{team}", "Yellow")));
				GameBool.destroyed_yellow = true;	
			} else if(e.getBlock().getLocation().equals(blue)){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.PouDestroyed").replace("{team}", "Blue")));
				GameBool.destroyed_blue = true;
			} else if(e.getBlock().getLocation().equals(green)){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.PouDestroyed").replace("{team}", "Green")));
				GameBool.destroyed_green = true;
			} else if(e.getBlock().getLocation().equals(red)){
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.PouDestroyed").replace("{team}", "Red")));
				GameBool.destroyed_red = true;
			}
		
	}
}
