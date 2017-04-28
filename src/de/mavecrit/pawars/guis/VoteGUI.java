package de.mavecrit.pawars.guis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.ClickOnTeamEvent;

   
public class VoteGUI implements Listener {
	  
	public static List<Integer> possibleIntegers = new ArrayList<Integer>();
	public static List<Integer> Maps;
	public static HashMap<Integer, Integer> votes = new HashMap<Integer,Integer>();
	
	public static void openGui(Player player) {
		Inventory games = Bukkit.getServer().createInventory(player,
				Main.getPlugin().getConfig().getInt("GUI.Vote.Size"), ChatColor.translateAlternateColorCodes('&',
						Main.getPlugin().getConfig().getString("GUI.Vote.Name")));

		if (Main.getPlugin().getConfig().getBoolean("GUI.Vote.Placeholders")) {
			for (int i = 0; i < games.getSize(); i++) {
				games.setItem(i, CustomItem(Main.getPlugin().getConfig().getString("General.GUI.Placeholder.Material"),
						Main.getPlugin().getConfig().getInt("General.GUI.Placeholder.Amount"),
						(byte) Main.getPlugin().getConfig().getInt("General.GUI.Placeholder.Byte"),
						Main.getPlugin().getConfig().getString("General.GUI.Placeholder.Displayname"),
						Main.getPlugin().getConfig().getString("General.GUI.Placeholder.Lore"),
						Main.getPlugin().getConfig().getBoolean("General.GUI.Placeholder.Glow")));
			}
		}
		
		for(int i = 0; i < Main.getPlugin().getConfig().getInt("General.VoteableMaps"); i++){
			games.setItem(i, CustomItem("PAPER",1,(byte)0,
					Main.locations.getString("arena_" + Maps.get(i) + ".name"),
					Main.getPlugin().getConfig().getString("GUI.Vote.Items.Lore"),
					false));
		}
		
		player.openInventory(games);
	}
	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}

	public static ItemStack CustomItem(String m, Integer Amount, Byte b, String displayname, String lore, boolean glow) {
		int vote;
		ItemStack itemStack = new ItemStack(Material.valueOf(m), Amount, b);
		if(glow){
			itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname).replace("{empty}", "§0§r"));
		ArrayList<String> lore1 = new ArrayList<String>();
        vote = 0;
		for(int i = 1; i <= LocationGetter.Arenas.size(); i++){
			if(Main.locations.getString("arena_" + i + ".name").equals(displayname)){
				if(votes.get(i) != null){
					vote = votes.get(i);
				} else {
					vote = 0;
				}
			}
		}
		
		lore1.add(ChatColor.translateAlternateColorCodes('&',lore.replace("{votes}", vote + "")));	
		itemMeta.setLore(lore1);
		
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory gui = event.getInventory();
		final Player p = (Player) event.getWhoClicked();
		if (gui.getName().equals(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("GUI.Vote.Name")))) {	
			if(event.getCurrentItem() != null)
			if(event.getCurrentItem().getType().equals(Material.PAPER)){

					for(int i = 1; i <= LocationGetter.Arenas.size(); i++){
						
						if(Main.locations.getString("arena_" + i + ".name").equals(event.getCurrentItem().getItemMeta().getDisplayName())){
							if(votes.get(i) != null){
							int current = votes.get(i);
							votes.put(i, (current + 1));
							} else {
							votes.put(i, 1);
							}
							p.sendMessage("§aYou've voted! Your map has now " + votes.get(i));
						}
					}
				}
			
			p.closeInventory();
			event.setCancelled(true);
		
		}
	}
}
