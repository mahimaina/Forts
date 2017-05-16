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
import de.mavecrit.pawars.item.mapcreator.SetPou;
import de.mavecrit.pawars.item.mapcreator.SetShops;

public class InteractEventPou implements Listener{

	@EventHandler
	public void SetPou(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Location location = p.getLocation();
	    if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
	    	if(p.getItemInHand().equals(SetPou.BluePou())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".pou.blue", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				e.setCancelled(true);
				p.sendMessage("§9Setted blue pou");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	} else if(p.getItemInHand().equals(SetPou.RedPou())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".pou.red", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				e.setCancelled(true);
				p.sendMessage("§cSetted red pou");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	}else if(p.getItemInHand().equals(SetPou.GreenPou())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".pou.green", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				e.setCancelled(true);
				p.sendMessage("§aSetted green pou");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	}else if(p.getItemInHand().equals(SetPou.YellowPou())){
	    		Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".pou.yellow", location.getWorld().getName() + "," + location.getBlockX()
				+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
				
				+ location.getYaw() + "," + location.getPitch());
				Main.locations.saveConfig();
				e.setCancelled(true);
				p.sendMessage("§eSetted yellow pou");
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
				
	    	}else if(p.getItemInHand().equals(CreaterItems.Finish()) && p.getInventory().contains(Material.STAINED_CLAY)){
	    		if(Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".pou.yellow") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".pou.red") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".pou.blue") != null &&
	    		   Main.locations.get("arena_" + LocationGetter.Arenas.size() + ".pou.green") != null){
	    		
	    			
	    		p.sendMessage("§aAlright, there you go!");
	    	    p.sendMessage("§7Now please set the shop location");
	    		   
	    		p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
	    		
	    		p.getInventory().clear();
				p.getInventory().addItem(SetShops.BlueShop());
				p.getInventory().addItem(SetShops.RedShop());
				p.getInventory().addItem(SetShops.GreenShop());
				p.getInventory().addItem(SetShops.YellowShop());
				p.getInventory().addItem(CreaterItems.Finish());
	    		} else {
	    			p.sendMessage("§cAt least one location isn't setted");
	    		}
	    	}
	    }
	}
	
}
