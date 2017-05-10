package de.mavecrit.pawars.item.mapcreator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetArenaWalls {
	public static ItemStack Waypoint(){
		ItemStack itemstack = new ItemStack(Material.STICK, 1, (byte) 0);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§bPos1");
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public static ItemStack Waypoint2(){
		ItemStack itemstack = new ItemStack(Material.STICK, 1, (byte) 0);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§aPos2");
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
}
