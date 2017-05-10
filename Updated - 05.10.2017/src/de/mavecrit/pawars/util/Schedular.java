package de.mavecrit.pawars.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.item.SpawnZombies;

public class Schedular extends BukkitRunnable{
	
    private final JavaPlugin plugin;
    private int counter;
    private Player p;
    private int slot;
    private int num;
    public Schedular(JavaPlugin plugin, int counter, Player p, int slot, int numerial) {
        this.plugin = plugin;
        this.p = p;
        this.slot = slot;
        this.num = numerial;
        if (counter < 1) {
            throw new IllegalArgumentException("counter must be greater than 1");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
    	ItemMeta im = p.getInventory().getItem(slot).getItemMeta();
        if (counter > 0) {
        	counter--;
		
			ArrayList<String> lore1 = new ArrayList<String>();
			String[] without_split = Main.getPlugin().getConfig().getString("Items.Empty.Lore").split(";");
			for(int i = 0; i < without_split.length; i++){
			String withSplit = ChatColor.translateAlternateColorCodes('&', without_split[i].replace("{empty}", "§0§r").replace("{time}", "" + counter));
			lore1.add(withSplit);
			}
			im.setLore(lore1);
		
			im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.Empty.Displayname").replace("{time}", "" + counter)));
			p.getInventory().getItem(slot).setItemMeta(im);
        } else {
        	p.getInventory().setItem(slot, SpawnZombies.Zombie(num));
            this.cancel();
        }
    }

}
