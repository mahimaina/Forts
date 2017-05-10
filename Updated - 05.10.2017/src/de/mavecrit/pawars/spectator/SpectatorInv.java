package de.mavecrit.pawars.spectator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.util.InventoryUI;
import de.mavecrit.pawars.util.Utils_unsorted;

public class SpectatorInv implements Listener {
	
	public static void openInv(Player p) {
	    int lines = 0;
	    Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
	    while (lines * 9 < players.length) {
	      lines++;
	    }
	    if (lines > 6) {
	      lines = 6;
	    }
	    InventoryUI uii = new InventoryUI();
	    uii.setTitle(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GUI.Spectator.Name"))).setSlots(lines * 9).createInventory();
	    int slot = 0;
	    
	    for (int i = 0; i < players.length; i++) {
	      Player p_ = players[i];
	      if (p_ != p) {
	    	  if(!Alive.Spectator.contains(p_)){
	    	if(Utils_unsorted.getTeamColor(p_).equals(Utils_unsorted.getTeamColor(p))){
	        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
	        ItemMeta meta = item.getItemMeta();
	        if ((meta instanceof SkullMeta)) {
	          SkullMeta smeta = (SkullMeta)meta;
	          smeta.setDisplayName(ChatColor.AQUA + p_.getName());
	          
	          smeta.setOwner(p_.getName());
	          item.setItemMeta(smeta);
	          uii.setItem(slot, item);
	          slot++;
	               }
	             }
	    	  }
	      }
	    }
	    p.openInventory(uii.getInventory());
	  }
 
	
   @EventHandler
   public static void onClick(InventoryClickEvent event) {  
	    if (event.getCurrentItem() == null) return;
	    final Player player = (Player)event.getWhoClicked();
	   
	    if (event.getInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GUI.Spectator.Name")))) {
	      if (event.getCurrentItem() == null) return;
	      if ((event.getCurrentItem().getType() == Material.SKULL_ITEM)){
	          ItemStack item = event.getCurrentItem();
	        
	          if ((item.getItemMeta() instanceof SkullMeta)) {
	            SkullMeta meta = (SkullMeta)item.getItemMeta();
	            Player target = Bukkit.getPlayer(meta.getOwner().toString());
	            if(Main.getAPI().isSpectating(player)){
	            Main.getAPI().startSpectating(player, target, false);
		        Main.getAPI().setSpectateAngle(player, SpectateAngle.FIRST_PERSON);
	            } else {
	            Main.getAPI().startSpectating(player, target, false);
	            Main.getAPI().setSpectateAngle(player, SpectateAngle.FIRST_PERSON);
	            }
	          }
	        }

	        event.setCancelled(true);
	    }
    }
}

