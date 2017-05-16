package de.mavecrit.pawars.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.spectator.SpectatorInv;

public class InventoryClose implements Listener{

	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		Player p = (Player) e.getPlayer();
		Inventory i = e.getInventory();
		
		if(i.getName().equals(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GUI.Spectator.Name")))){
			if(!Main.getAPI().isSpectating(p)){
				 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
				        @Override
				        public void run() {
				SpectatorInv.openInv(p);
				        }
				 },2l);
			}
		}
	}
}
