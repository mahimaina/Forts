package de.mavecrit.pawars.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.guis.VoteGUI;

public class LocationGetter {
	public static List<Integer> Arenas = new ArrayList<Integer>();
	public static List<String> names = new ArrayList<String>();
	public static List<Integer> waypoints = new ArrayList<Integer>();
	
	public static void LoadAll() {
		for (int i = 1; Main.locations.contains("arena_" + i); i++) {
			Arenas.add(1);
			Bukkit.getConsoleSender().sendMessage("§aArena " + i + " has been found!");
		}
		
		if(Main.locations.get("arenanames") != null){
		String[] name = Main.locations.getString("arenanames").split(",");
		for(int i = 0; i < name.length; i++){
			names.add(name[i]);
			Bukkit.getConsoleSender().sendMessage("§aName " + (i +1) + " has been found!");
		}
		}

	}

	public static Location GetLocation(String Key) {
		if (!Main.locations.contains(Key)) {
			return null;
		}
		String[] v = Main.locations.getString(Key).split(",");
		return new Location(Bukkit.getWorld(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]),
				Double.parseDouble(v[3]), Float.parseFloat(v[4]), Float.parseFloat(v[5]));
	}
	

	

}
