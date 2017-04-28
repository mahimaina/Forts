package de.mavecrit.pawars.lists;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

import de.mavecrit.pawars.entitys.CustomZombie;

public class Zombies {
	
	public static HashMap<Integer, List<CustomZombie>> zombies = new HashMap<>();	
	public static HashMap<CustomZombie, Location> location = new HashMap<>();
}
