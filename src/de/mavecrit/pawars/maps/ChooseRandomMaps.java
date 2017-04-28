package de.mavecrit.pawars.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.location.LocationGetter;

public class ChooseRandomMaps {
	
	public static List<Integer> chooseMaps(){
		List<Integer> choosed = new ArrayList<Integer>();

		for(int i = 0; i < Main.getPlugin().getConfig().getInt("General.VoteableMaps"); i++){
			
			Integer choosedMap = VoteGUI.possibleIntegers.get(new Random().nextInt(VoteGUI.possibleIntegers.size()));
			choosed.add(choosedMap);
			VoteGUI.possibleIntegers.remove(choosedMap);
		}
		return choosed;
	}
	
	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
}
