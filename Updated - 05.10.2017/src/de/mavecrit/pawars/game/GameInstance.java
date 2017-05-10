package de.mavecrit.pawars.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;
import de.mavecrit.pawars.entitys.CustomVillager;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.events.BlockTask;
import de.mavecrit.pawars.events.schedular.GameStart;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.Actionbar_1_11_R1;
import de.mavecrit.pawars.util.GetHashMap;
import de.mavecrit.pawars.util.Schedular;
import de.mavecrit.pawars.util.Titles;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.World;

public class GameInstance {
	
	public static Integer mapID = null;
	
	public static void onStart(){
		new GameStart(Main.getPlugin(), Main.getPlugin().getConfig().getInt("General.WaitingTimer")).runTaskTimer(Main.getPlugin(), 0, 20);
	}
	
	public static void RealStart(){
		mixTeams();
		
		for(String blue_names : TeamList.blue){
			Player blue = Bukkit.getPlayer(blue_names);
			blue.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.blue"));
		}	
		for(String red_names : TeamList.red){
			Player red = Bukkit.getPlayer(red_names);
			red.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.red"));
		}	
		for(String yellow_names : TeamList.yellow){
			Player yellow = Bukkit.getPlayer(yellow_names);
			yellow.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.yellow"));
		}	
		for(String green_names : TeamList.green){
			Player green = Bukkit.getPlayer(green_names);
			green.teleport(LocationGetter.GetLocation("arena_" + mapID + ".spawn.green"));
		}
	}			
	
	public static void createShops(){
		Location yellow = LocationGetter.GetLocation("arena_" + mapID + ".shop.yellow");
		Location blue = LocationGetter.GetLocation("arena_" + mapID + ".shop.blue");
		Location green = LocationGetter.GetLocation("arena_" + mapID + ".shop.green");
		Location red = LocationGetter.GetLocation("arena_" + mapID + ".shop.red");
		World nmsWorld = ((CraftWorld) red.getWorld()).getHandle();
		
		CustomVillager shopy = new CustomVillager(nmsWorld);
		shopy.setPosition(yellow.getX(), yellow.getY(), yellow.getZ());
		shopy.setCustomName("§6" + Main.getPlugin().getConfig().getString("Mobs.Shop.Displayname"));
		shopy.setCustomNameVisible(true);
		shopy.setSilent(true);				
		nmsWorld.addEntity(shopy);		
		
		CustomVillager shopg = new CustomVillager(nmsWorld);
		shopg.setPosition(green.getX(), green.getY(), green.getZ());
		shopg.setCustomName("§2" + Main.getPlugin().getConfig().getString("Mobs.Shop.Displayname"));
		shopg.setCustomNameVisible(true);
		shopg.setSilent(true);				
		nmsWorld.addEntity(shopg);	
		
		CustomVillager shopr = new CustomVillager(nmsWorld);
		shopr.setPosition(red.getX(), red.getY(), red.getZ());
		shopr.setCustomName("§4" + Main.getPlugin().getConfig().getString("Mobs.Shop.Displayname"));
		shopr.setCustomNameVisible(true);
		shopr.setSilent(true);				
		nmsWorld.addEntity(shopr);	
		
		CustomVillager shopb = new CustomVillager(nmsWorld);
		shopb.setPosition(blue.getX(), blue.getY(), blue.getZ());
		shopb.setCustomName("§9" + Main.getPlugin().getConfig().getString("Mobs.Shop.Displayname"));
		shopb.setCustomNameVisible(true);
		shopb.setSilent(true);				
		nmsWorld.addEntity(shopb);	
		
		Bukkit.getConsoleSender().sendMessage("§aShops setted.");
	}
	
	public static void createPous(){
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
	
	public static void mixTeams(){
		Utils_unsorted.mixTeams();
	}
	
	public static void saveBlocks(){
			
		String blockstring = Main.locations.getString("arena_" + mapID + ".wallblocks");
		String[] splitted = blockstring.split(";");
		
		for(int i = 0; i < splitted.length; i++){
			String[] v = splitted[i].split(",");
			Location loc = new Location(Bukkit.getWorld(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]),
					Double.parseDouble(v[3]), Float.parseFloat(v[4]), Float.parseFloat(v[5]));
			BlockTask.blocklive.put(loc, 100);
			BlockTask.blocks.add(loc.getBlock());
			loc.getBlock().setType(Material.valueOf(Main.getPlugin().getConfig().getString("General.WallBlock")));
		}
	}
}
