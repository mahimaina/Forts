package de.mavecrit.pawars.item;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.mavecrit.pawars.Main;

public class CustomItem {

	public static ItemStack CustomItem(String m, Integer Amount, Byte b, String displayname, boolean glow, int num) {
		ItemStack itemStack = new ItemStack(Material.valueOf(m), Amount, b);
		if(glow){
			itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    itemStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(displayname);
		ArrayList<String> lore2 = new ArrayList<String>();
		String[] lore1 = Main.getPlugin().getConfig().getString("Items.DefaultItems." + num + ".Lore").split(";");
		for(int i = 0; i < lore1.length; i++){
		lore2.add(lore1[i]);
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	public static ItemStack CustomItem2(String m, Integer Amount, Byte b, String displayname, boolean glow, String lore) {
		ItemStack itemStack = new ItemStack(Material.valueOf(m), Amount, b);
		if(glow){
			itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    itemStack.getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName(displayname);
		ArrayList<String> lore2 = new ArrayList<String>();
		String[] lore1 = lore.split(";");
		for(int i = 0; i < lore1.length; i++){
		lore2.add(lore1[i]);
		}

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
