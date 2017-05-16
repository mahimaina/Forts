package de.mavecrit.pawars.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.booleans.TeamsB;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.item.CustomItem;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.item.Spectate;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.spectator.SpectatorInv;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.Utils_unsorted;

public class PlayerRespawn implements Listener{
	
	@EventHandler
	public void onDeath(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		String t = Utils_unsorted.getTeamColor(p);
		
		if(t.equals("§9")){
			if(GameBool.destroyed_blue){
				Alive.Alive.remove(p);
				Alive.Spectator.add(p);
				TeamList.blue_alive.remove(p.getName());
				if(TeamList.blue_alive.size() == 0){
					TeamsB.blue = false;
					TeamsB.state.remove("blue");
				}
				p.getInventory().clear();
				p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.Spectate.Slot"), Spectate.spec());	
				openInv(p);
			} else {
				tele(p,LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".spawn.blue"));
				giveItems(p);
			}
		} else
		if(t.equals("§2")){
			if(GameBool.destroyed_green){
				Alive.Alive.remove(p);
				Alive.Spectator.add(p);
				TeamList.green_alive.remove(p.getName());
				if(TeamList.green_alive.size() == 0){
					TeamsB.green = false;
					TeamsB.state.remove("green");
				}
				p.getInventory().clear();
				p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.Spectate.Slot"), Spectate.spec());	
				openInv(p);
			} else {
				tele(p,LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".spawn.green"));
				giveItems(p);
			}
		} else
		if(t.equals("§6")){
			if(GameBool.destroyed_yellow){
				Alive.Alive.remove(p);
				Alive.Spectator.add(p);
				TeamList.yellow_alive.remove(p.getName());
				if(TeamList.yellow_alive.size() == 0){
					TeamsB.yellow = false;
					TeamsB.state.remove("yellow");
				}
				p.getInventory().clear();
				p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.Spectate.Slot"), Spectate.spec());	
				openInv(p);
			} else {
				tele(p,LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".spawn.yellow"));
				giveItems(p);
			}
		} else 
		if(t.equals("§4")){
			if(GameBool.destroyed_red){
				Alive.Alive.remove(p);
				Alive.Spectator.add(p);
				TeamList.red_alive.remove(p.getName());
				if(TeamList.red_alive.size() == 0){
					TeamsB.red = false;
					TeamsB.state.remove("red");
				}
				p.getInventory().clear();
				p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.Spectate.Slot"), Spectate.spec());
				openInv(p);
			} else {
				tele(p,LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".spawn.red"));
				giveItems(p);
			}
		}
		
		if(TeamsB.state.size() == 1){
			GameInstance.end();
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
	
	private void giveItems(Player p){
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
		        @Override
		        public void run() {
		    		
					for(int num = 1; Main.getPlugin().getConfig().contains("Items.DefaultItems." + num  + ".Enabled"); num++){	
					if(Main.getPlugin().getConfig().getBoolean("Items.DefaultItems." + num  + ".Enabled")){
						p.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.DefaultItems." + num + ".Slot"),
						CustomItem.CustomItem(Main.getPlugin().getConfig().getString("Items.DefaultItems." + num + ".Material"),
								Main.getPlugin().getConfig().getInt("Items.DefaultItems." + num + ".Amount"), 
								(byte) Main.getPlugin().getConfig().getInt("Items.DefaultItems." + num + ".Byte"), 
								ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.DefaultItems." + num + ".Displayname")), 
								Main.getPlugin().getConfig().getBoolean("Items.DefaultItems." + num + ".Glow"),
								num));}
					}
					for(int num = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + num + ".EggColor"); num++){	
						if(Main.getPlugin().getConfig().getBoolean("Items.Zombies." + num  + ".GiveOnStart")){
							p.getInventory().addItem(SpawnZombies.Zombie(num));}
					}
		        }
		 },5l);
	}
	
	private void tele(Player p, Location l){
	    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
	        @Override
	        public void run() {
	        	p.teleport(l);
	          }
	        }, 2L);
	}
}
