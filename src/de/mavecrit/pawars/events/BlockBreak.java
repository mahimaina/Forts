package de.mavecrit.pawars.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.events.custom.PouDestroyEvent;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.location.LocationGetter;

public class BlockBreak {
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(e.getBlock().getType() != Material.valueOf(Main.getPlugin().getConfig().getString("General.PouBlock"))){
			e.setCancelled(true);
		} else {
				Location yellow = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.yellow");
				Location blue = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.blue");
				Location green = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.green");
				Location red = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".pou.red");
			if(e.getBlock().getLocation() == yellow){
				  Bukkit.getServer().getPluginManager().callEvent(new PouDestroyEvent(e.getPlayer(), "yellow"));
			} else if(e.getBlock().getLocation() == blue){
				  Bukkit.getServer().getPluginManager().callEvent(new PouDestroyEvent(e.getPlayer(), "blue"));
			} else if(e.getBlock().getLocation() == green){
				  Bukkit.getServer().getPluginManager().callEvent(new PouDestroyEvent(e.getPlayer(), "green"));
			} else if(e.getBlock().getLocation() == red){
				  Bukkit.getServer().getPluginManager().callEvent(new PouDestroyEvent(e.getPlayer(), "red"));
			}
		}
	}
}
