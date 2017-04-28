package de.mavecrit.pawars.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.events.BlockTask;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.ActionBarCountdown;
import de.mavecrit.pawars.util.Actionbar_1_11_R1;
import de.mavecrit.pawars.util.GetHashMap;
import de.mavecrit.pawars.util.Titles;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.md_5.bungee.api.ChatColor;

public class GameInstance {
	
	public static Integer mapID = null;
	
	public static void onStart(){
		new ActionBarCountdown(25, Main.getPlugin()) {
			@Override
			public String transformText(String curr) {
				return curr + " §7| §4" + (this.getDuration() - this.getCurrent()) + "s left";
			}

			@Override
			public void onEnd() {
				if (Bukkit.getServer().getOnlinePlayers().size() < Main.getPlugin().getConfig().getInt("General.MinPlayers")) {
					for (Player online : Bukkit.getOnlinePlayers()) {
						Actionbar_1_11_R1.sendActionbar(online, ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.NotEnoughPlayers")));
					}
				} else {
					if(Main.getPlugin().getConfig().getBoolean("General.Voting")){
						int i = GetHashMap.getVotedMap(VoteGUI.votes);
						mapID = i;
					}		
					GameBool.setStarted(true);
					for (Player online : Bukkit.getOnlinePlayers()) {
						Alive.Alive.add(online);
						Titles.sendTitle(online, ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Titles.VotedMap.Title"))
								.replace("{map}", Main.locations.getString("arena_" + mapID + ".name")),
								ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Titles.VotedMap.SubTitle"))
								.replace("{map}", Main.locations.getString("arena_" + mapID + ".name")), 0, 60, 0);
					}
					createPous();
					RealStart();
					Location l = LocationGetter.GetLocation("arena_" + mapID + ".pou.red");
					saveBlocks(l);
				}
			}
		};
	}
	
	public static void RealStart(){
		mixTeams();
		
		for(String blue_names : TeamList.blue){
			Player blue = Bukkit.getPlayer(blue_names);
			blue.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.blue"));
		}	
		for(String red_names : TeamList.blue){
			Player red = Bukkit.getPlayer(red_names);
			red.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.red"));
		}	
		for(String yellow_names : TeamList.blue){
			Player yellow = Bukkit.getPlayer(yellow_names);
			yellow.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.yellow"));
		}	
		for(String green_names : TeamList.blue){
			Player green = Bukkit.getPlayer(green_names);
			green.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.green"));
		}
	}
	
	private static void createPous(){
		Location yellow = LocationGetter.GetLocation("arena_" + mapID + ".pou.yellow");
		Location blue = LocationGetter.GetLocation("arena_" + mapID + ".pou.blue");
		Location green = LocationGetter.GetLocation("arena_" + mapID + ".pou.green");
		Location red = LocationGetter.GetLocation("arena_" + mapID + ".pou.red");
		
		yellow.getBlock().setType(Material.valueOf(Main.getPlugin().getConfig().getString("General.PouBlock")));
		blue.getBlock().setType(Material.valueOf(Main.getPlugin().getConfig().getString("General.PouBlock")));
		green.getBlock().setType(Material.valueOf(Main.getPlugin().getConfig().getString("General.PouBlock")));
		red.getBlock().setType(Material.valueOf(Main.getPlugin().getConfig().getString("General.PouBlock")));
		Bukkit.getConsoleSender().sendMessage("§aPous setted.");
	}
	
	private static void mixTeams(){
		Utils_unsorted.mixTeams();
	}
	
	private static void saveBlocks(Location l){
		List<Block> temporary = new ArrayList<Block>();		
		temporary = Utils_unsorted.getNearbyBlocks(l, 550);	
		for(Block b : temporary){
			if(b.getType().equals(Material.valueOf(Main.getPlugin().getConfig().getString("General.WallBlock")))){
				BlockTask.blocklive.put(b.getLocation(), 100);
				BlockTask.blocks.add(b);
			}
		}
	}
}
