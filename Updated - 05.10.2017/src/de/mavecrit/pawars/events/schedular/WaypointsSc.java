package de.mavecrit.pawars.events.schedular;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.location.LocationGetter;

public class WaypointsSc extends BukkitRunnable {
	static Random r = new Random();

	public WaypointsSc() {
	}

	@Override
	public void run() {
		for (int i = 1; Main.locations.contains("arena_" + GameInstance.mapID + ".waypoint_" + i); i++) {
			String wp = Main.locations.getString("arena_" + GameInstance.mapID + ".waypoint_" + i);
			String[] split = wp.split(",");
			Location loc = LocationGetter.GetLocation("arena_" + GameInstance.mapID + ".waypoint_" + i);
			if (split[6].equals("diamond")) {
				final Item ITEM = loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND));
				ITEM.setVelocity(new Vector(r.nextDouble() - 0.5, r.nextDouble() / 2.0, r.nextDouble() - 0.5));
			} else if (split[6].equals("emerald")) {
				final Item ITEM = loc.getWorld().dropItem(loc, new ItemStack(Material.EMERALD));
				ITEM.setVelocity(new Vector(r.nextDouble() - 0.5, r.nextDouble() / 2.0, r.nextDouble() - 0.5));
			}
		}
	}

}
