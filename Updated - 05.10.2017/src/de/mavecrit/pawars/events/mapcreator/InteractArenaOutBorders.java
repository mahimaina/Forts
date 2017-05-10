package de.mavecrit.pawars.events.mapcreator;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.item.CreaterItems;
import de.mavecrit.pawars.item.mapcreator.SetArenaWalls;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.util.Utils_unsorted;

public class InteractArenaOutBorders implements Listener {
	
	public static boolean pos1 = false;
	public static Location pos1_loc = null;
	
	public static boolean pos2 = false;
	public static Location pos2_loc = null;
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getItemInHand().equals(SetArenaWalls.Waypoint())){
			if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(pos1 == true){
					pos1 = false;
				}
				pos1 = true;
				pos1_loc = e.getClickedBlock().getLocation();
				
				p.sendMessage("§aSelected Pos1");
			} else {
				p.sendMessage("§cPlease hit the target block");
			}
		}
		if(p.getItemInHand().equals(SetArenaWalls.Waypoint2())){
			if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				if(pos2 == true){
					pos2 = false;
				}
				pos2 = true;
				pos2_loc = e.getClickedBlock().getLocation();
				
				p.sendMessage("§aSelected Pos2");
			} else {
				p.sendMessage("§cPlease hit the target block");
			}
		}
		if(p.getItemInHand().equals(CreaterItems.Finish())){
			if(pos1 == true && pos2 == true){
				List<Block> allblocks = Utils_unsorted.select(pos1_loc, pos2_loc, pos1_loc.getWorld());
				
				for(Block b: allblocks){
					if(b.getType().equals(Material.valueOf(Main.getPlugin().getConfig().getString("General.WallBlock")))){
						if(Main.locations.getString("arena_" + LocationGetter.Arenas.size() + ".wallblocks") != null){
						String current = Main.locations.getString("arena_" + LocationGetter.Arenas.size() + ".wallblocks");				
						Location location = b.getLocation();
						Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".wallblocks", current + location.getWorld().getName() + "," + location.getBlockX()
						+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
						+ location.getYaw() + "," + location.getPitch() + ";");
						} else {
						Location location = b.getLocation();
						Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".wallblocks", location.getWorld().getName() + "," + location.getBlockX()
						+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
						+ location.getYaw() + "," + location.getPitch() + ";");
						}
						Main.locations.saveConfig();
					}
				}
				Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".Enabled", true);	
	    		Main.locations.saveConfig();
	    		p.sendMessage("§aAlright, you've setted up the arena!");		    		   
	    		p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
	    		
	    		p.getInventory().clear();
			} else {
				p.sendMessage("§cOne location is not setted");
			}
		}
	}
}
