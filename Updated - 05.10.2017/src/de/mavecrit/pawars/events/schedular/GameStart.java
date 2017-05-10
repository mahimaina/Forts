package de.mavecrit.pawars.events.schedular;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.events.Waypoints;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.item.CustomItem;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.maps.ChooseRandomMaps;
import de.mavecrit.pawars.util.Actionbar_1_11_R1;
import de.mavecrit.pawars.util.GetHashMap;
import de.mavecrit.pawars.util.Titles;

public class GameStart extends BukkitRunnable{
	
    private final JavaPlugin plugin;
    private int counter;

    public GameStart(JavaPlugin plugin, int counter) {
        this.plugin = plugin;

        if (counter < 1) {
            throw new IllegalArgumentException("counter must be greater than 1");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
    	
        if (counter > 0) {
        	counter--;
        	for (Player online : Bukkit.getOnlinePlayers()) {
        		if(Main.getPlugin().getConfig().getBoolean("Messages.Countdown.Actionbar.Enabled")){
				Actionbar_1_11_R1.sendActionbar(online, ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Countdown.Actionbar.Message")
						.replace("{timer}", "" + counter)));}
				
        		if(counter == 20){
        		online.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Countdown.Chat")
						.replace("{timer}", "" + counter)));}
        		if(counter <= 10){
        			online.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Countdown.Chat")
    						.replace("{timer}", "" + counter)));}
        		
			}
        } else {
    		if (Bukkit.getServer().getOnlinePlayers().size() < Main.getPlugin().getConfig().getInt("General.MinPlayers")) {
				for (Player online : Bukkit.getOnlinePlayers()) {
					Actionbar_1_11_R1.sendActionbar(online, ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.NotEnoughPlayers")));
				    GameBool.setEnough(false);
				}
			} else {
				if(Main.getPlugin().getConfig().getBoolean("General.Voting")){
					if(GetHashMap.getVotedMap(VoteGUI.votes) != null){
					int i = GetHashMap.getVotedMap(VoteGUI.votes);		
					GameInstance.mapID = i;
					} else {
					GameInstance.mapID = ChooseRandomMaps.getRandom(1, VoteGUI.possibleIntegers.size());
					}
				}		
				GameBool.setStarted(true);
				for (Player online : Bukkit.getOnlinePlayers()) {
					Alive.Alive.add(online);
					Titles.sendTitle(online, ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Titles.VotedMap.Title"))
							.replace("{map}", Main.locations.getString("arena_" + GameInstance.mapID + ".name")),
							ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Titles.VotedMap.SubTitle"))
							.replace("{map}", Main.locations.getString("arena_" + GameInstance.mapID + ".name")), 0, 60, 0);
				
					for(int num = 1; Main.getPlugin().getConfig().contains("Items.DefaultItems." + num  + ".Enabled"); num++){	
					if(Main.getPlugin().getConfig().getBoolean("Items.DefaultItems." + num  + ".Enabled")){
						online.getInventory().setItem(Main.getPlugin().getConfig().getInt("Items.DefaultItems." + num + ".Slot"),
						CustomItem.CustomItem(Main.getPlugin().getConfig().getString("Items.DefaultItems." + num + ".Material"),
								Main.getPlugin().getConfig().getInt("Items.DefaultItems." + num + ".Amount"), 
								(byte) Main.getPlugin().getConfig().getInt("Items.DefaultItems." + num + ".Byte"), 
								ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.DefaultItems." + num + ".Displayname")), 
								Main.getPlugin().getConfig().getBoolean("Items.DefaultItems." + num + ".Glow"),
								num));}
					}
					for(int num = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + num + ".EggColor"); num++){	
						if(Main.getPlugin().getConfig().getBoolean("Items.Zombies." + num  + ".GiveOnStart")){
							online.getInventory().addItem(SpawnZombies.Zombie(num));}
					}
					Alive.Alive.add(online);
				}
				GameInstance.createPous();
				GameInstance.createShops();
				GameInstance.RealStart();
				GameInstance.saveBlocks();
				Waypoints.createWaypoints();
				
			}
            this.cancel();
        }
    }

}
