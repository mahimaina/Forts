package de.mavecrit.pawars.guis;

import java.util.ArrayList;

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
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.ClickOnTeamEvent;

public class TeamSelectGUI implements Listener {
	public static void openGui(Player player) {
		Inventory games = Bukkit.getServer().createInventory(player,
				Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Size"), ChatColor.translateAlternateColorCodes('&',
						Main.getPlugin().getConfig().getString("GUI.TeamSelector.Name")));

		if (Main.getPlugin().getConfig().getBoolean("GUI.TeamSelector.Placeholders")) {
			for (int i = 0; i < games.getSize(); i++) {
				games.setItem(i, CustomItem(Main.getPlugin().getConfig().getString("General.GUI.Placeholder.Material"),
						Main.getPlugin().getConfig().getInt("General.GUI.Placeholder.Amount"),
						(byte) Main.getPlugin().getConfig().getInt("General.GUI.Placeholder.Byte"),
						Main.getPlugin().getConfig().getString("General.GUI.Placeholder.Displayname"),
						Main.getPlugin().getConfig().getString("General.GUI.Placeholder.Lore"),
						Main.getPlugin().getConfig().getBoolean("General.GUI.Placeholder.Glow")));
			}
		}
		if (Main.getPlugin().getConfig().getBoolean("GUI.TeamSelector.Items.CurrentTeams.Enabled")) {
		games.setItem(Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.CurrentTeams.Slot"), CurrentTeams(Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.CurrentTeams.Material"),
				Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.CurrentTeams.Amount"),
				(byte) Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.CurrentTeams.Byte"),
				Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.CurrentTeams.Displayname"),
				"",
				Main.getPlugin().getConfig().getBoolean("GUI.TeamSelector.Items.CurrentTeams.Glow")));
		}
	
		for(int i = 0; i < 4; i++){
		games.setItem(Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.Teams." + i +".Slot"), CustomItem(Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i +".Material"),
				Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.Teams." + i +".Amount"),
				(byte) Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.Teams." + i +".Byte"),
				Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i +".Displayname"),
				Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i +".Lore"),
				Main.getPlugin().getConfig().getBoolean("GUI.TeamSelector.Items.Teams." + i +".Glow")));
		}
		player.openInventory(games);
	}
	

	public static ItemStack CustomItem(String m, Integer Amount, Byte b, String displayname, String lore, boolean glow) {

		ItemStack itemStack = new ItemStack(Material.valueOf(m), Amount, b);
		if(glow){
			itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname).replace("{empty}", "§0§r"));
		ArrayList<String> lore1 = new ArrayList<String>();
		String[] without_split = lore.split(";");
		for(int i = 0; i < without_split.length; i++){
		String withSplit = ChatColor.translateAlternateColorCodes('&', without_split[i].replace("{empty}", "§0§r"));
		lore1.add(withSplit);
		}
		itemMeta.setLore(lore1);

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	public static ItemStack CurrentTeams(String m, Integer Amount, Byte b, String displayname, String lore, boolean glow) {

		ItemStack itemStack = new ItemStack(Material.valueOf(m), Amount, b);
		if(glow){
			itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname).replace("{empty}", "§0§r"));
		

		ArrayList<String> lore1 = new ArrayList<String>();
		lore1.add("§7Team Blue:");
		for(int i = 0; i < Main.getPlugin().getConfig().getInt("General.TeamSize"); i++){
			String allmembers = (TeamList.blue + "").replace("[", "").replace("]", "");
			String[] singlemember = allmembers.split(",");
			if(singlemember.length > i){
			lore1.add("§9" + singlemember[i]);
			}
		}
		
		lore1.add("§7Team Green:");
		for(int i = 0; i < Main.getPlugin().getConfig().getInt("General.TeamSize"); i++){
			String allmembers = (TeamList.green + "").replace("[", "").replace("]", "");
			String[] singlemember = allmembers.split(",");
			if(singlemember.length > i){
			lore1.add("§a" + singlemember[i]);
			}
		}
		
		lore1.add("§7Team Red:");
		for(int i = 0; i < Main.getPlugin().getConfig().getInt("General.TeamSize"); i++){
			String allmembers = (TeamList.red + "").replace("[", "").replace("]", "");
			String[] singlemember = allmembers.split(",");
			if(singlemember.length > i){
			lore1.add("§c" + singlemember[i]);
			}
		}
		
		lore1.add("§7Team Yellow:");
		for(int i = 0; i < Main.getPlugin().getConfig().getInt("General.TeamSize"); i++){
			String allmembers = (TeamList.yellow + "").replace("[", "").replace("]", "");
			String[] singlemember = allmembers.split(",");
			if(singlemember.length > i){
			lore1.add("§e" + singlemember[i]);
			}
		}
		itemMeta.setLore(lore1);

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory gui = event.getInventory();
		final Player p = (Player) event.getWhoClicked();
		if (gui.getName().equals(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("GUI.TeamSelector.Name")))) {
			for(int i = 0; i < 4; i++){
				if(event.getSlot() == Main.getPlugin().getConfig().getInt("GUI.TeamSelector.Items.Teams." + i + ".Slot")){
					
					Bukkit.getServer().getPluginManager().callEvent(new ClickOnTeamEvent(p, Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i + ".joins")));  
					
					if(Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i + ".joins").equals("blue")){
						if(!TeamList.blue.contains(p.getName())){
						if(TeamList.blue.size() < Main.getPlugin().getConfig().getInt("General.TeamSize")){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.JoinedTeam").replace("{team}", "Blue")));
							TeamList.blue.add(p.getName());
							TeamList.blue_alive.add(p.getName());
						    }
						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
							TeamList.blue.remove(p.getName());	
							TeamList.blue_alive.remove(p.getName());
						}
					}
					if(Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i + ".joins").equals("yellow")){
						if(!TeamList.yellow.contains(p.getName())){
						if(TeamList.yellow.size() < Main.getPlugin().getConfig().getInt("General.TeamSize")){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.JoinedTeam").replace("{team}", "Yellow")));
							TeamList.yellow.add(p.getName());
							TeamList.yellow_alive.add(p.getName());
						    }
						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
							TeamList.yellow.remove(p.getName());
							TeamList.yellow_alive.remove(p.getName());
						}
					}
					if(Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i + ".joins").equals("red")){
						if(!TeamList.red.contains(p.getName())){
						if(TeamList.red.size() < Main.getPlugin().getConfig().getInt("General.TeamSize")){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.JoinedTeam").replace("{team}", "Red")));
							TeamList.red.add(p.getName());
							TeamList.red_alive.add(p.getName());
						    }
						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
							TeamList.red.remove(p.getName());	
							TeamList.red_alive.remove(p.getName());
						}
					}
					if(Main.getPlugin().getConfig().getString("GUI.TeamSelector.Items.Teams." + i + ".joins").equals("green")){
						if(!TeamList.green.contains(p.getName())){
						if(TeamList.green.size() < Main.getPlugin().getConfig().getInt("General.TeamSize")){
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.JoinedTeam").replace("{team}", "green")));
							TeamList.green.add(p.getName());
							TeamList.green_alive.add(p.getName());
						    }
						} else {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
							TeamList.green.remove(p.getName());	
							TeamList.green_alive.remove(p.getName());
						}
					}
				}
			}
			p.closeInventory();
			event.setCancelled(true);
		
		}
	}
}
