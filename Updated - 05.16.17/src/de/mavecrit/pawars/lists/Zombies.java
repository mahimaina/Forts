package de.mavecrit.pawars.lists;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.teams.ZombieEnum;

public class Zombies {
	
	public static HashMap<Location, List<CustomZombie>> zombies = new HashMap<>();	
	public static HashMap<String, List<CustomZombie>> Units = new HashMap<>();	
	public static HashMap<CustomZombie, Location> location = new HashMap<>();
	public static HashMap<CustomZombie, ZombieEnum> teams = new HashMap<>();
	
}
