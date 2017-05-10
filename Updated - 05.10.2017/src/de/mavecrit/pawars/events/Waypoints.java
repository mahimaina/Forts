package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Item;
import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.events.schedular.WaypointsSc;

public class Waypoints {

	public static void createWaypoints(){
		new WaypointsSc().runTaskTimer(Main.getPlugin(), 0, ( 20 * Main.getPlugin().getConfig().getInt("General.WaypointScheduler")));
	}
}
