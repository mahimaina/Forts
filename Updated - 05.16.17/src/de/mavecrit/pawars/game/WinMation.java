package de.mavecrit.pawars.game;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.util.ParticleEffect;



public class WinMation {

	
	
	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
	
	public static void WinAnimation(Player p){

		for(Player all :Bukkit.getOnlinePlayers()){
			all.playSound(all.getLocation(), Sound.MUSIC_CREDITS, Float.MAX_VALUE, 1f);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			public void run() {

			ParticleEffect.FLAME.display(0.5F, 0.5F, 0.5F, 0.1F, 30,
					p.getLocation().add(getRandom(-3,5), getRandom(1,3), getRandom(-3,5)), 20); 
			ParticleEffect.FLAME.display(0.5F, 0.5F, 0.5F, 0.1F, 30,
					p.getLocation().add(getRandom(-3,5), getRandom(1,3), getRandom(-3,5)), 20); 
			ParticleEffect.FLAME.display(0.5F, 0.5F, 0.5F, 0.1F, 30,
				p.getLocation().add(getRandom(-3,5), getRandom(1,3), getRandom(-3,5)), 20); 
		
			}
		}, 20L, 20);
	}
}
