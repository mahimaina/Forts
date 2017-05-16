package de.mavecrit.pawars.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.item.Spectate;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.spectator.SpectatorInv;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.md_5.bungee.api.ChatColor;

public class PlayerDeath implements Listener{
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		
		if(Main.getAPI().isBeingSpectated(p)){
			List<Player> spectators = Main.getAPI().getSpectators(p);
			for(Player s : spectators){
				s.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Spectate.SpectatorDied")));
				Main.getAPI().stopSpectating(s, false);
				openInv(s);
			}
		}
	}
	
	private void openInv(Player p){
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
	        @Override
	        public void run() {
	        	SpectatorInv.openInv(p);
	          }
	        }, 2L);
	}
}
