package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.item.TeamSelect;
import de.mavecrit.pawars.item.Vote;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.spectator.SpectatorInv;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		if(!GameBool.isStarted()){
		Player p = e.getPlayer();
		p.getInventory().clear();
		 for(Player online : Bukkit.getOnlinePlayers()){
		    	online.showPlayer(p);
		    	p.showPlayer(online);
		    }
		if(Main.getPlugin().getConfig().getBoolean("Items.JoinItems.TeamSelector.Enabled")){		
				p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.JoinItems.TeamSelector.Slot"), TeamSelect.selector());		
		}
		if(Main.getPlugin().getConfig().getBoolean("General.Voting")){		
			p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.JoinItems.Vote.Slot"), Vote.voter());		
	    }

		p.teleport(LocationGetter.GetLocation("lobby"));
		
		if(Bukkit.getOnlinePlayers().size() >= Main.getPlugin().getConfig().getInt("General.MinPlayers")){
			if(!GameBool.isEnough()){
		    GameBool.setEnough(true);
			GameInstance.onStart();
			}
		  }
		} else {
			Alive.Spectator.add(e.getPlayer());
			openInv(e.getPlayer());
		}
	}
	
	private void openInv(Player p){
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
	        @Override
	        public void run() {
	        	SpectatorInv.openInv(p);
	          }
	        }, 2L);
	    
	    for(Player online : Bukkit.getOnlinePlayers()){
	    	online.hidePlayer(p);
	    }
	}
}
