package de.mavecrit.pawars.item;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.mavecrit.pawars.Main;


public class TeamSelect {

	
	public static ItemStack selector(){
		ItemStack itemstack = new ItemStack(Material.valueOf(Main.getPlugin().getConfig().getString("Items.JoinItems.TeamSelector.Material")),
				Main.getPlugin().getConfig().getInt("Items.JoinItems.TeamSelector.Amount"),
				(byte) Main.getPlugin().getConfig().getInt("Items.JoinItems.TeamSelector.Byte"));
		if(Main.getPlugin().getConfig().getBoolean("Items.JoinItems.TeamSelector.Glow")){
			itemstack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.JoinItems.TeamSelector.Displayname")));
		ArrayList<String> lore1 = new ArrayList<String>();
		String[] lore = Main.getPlugin().getConfig().getString("Items.JoinItems.TeamSelector.Lore").split(";");
		for(int i = 0; i < lore.length; i++){
		lore1.add(lore[i]);
		}
		itemmeta.setLore(lore1);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
}
