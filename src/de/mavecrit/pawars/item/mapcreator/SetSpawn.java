package de.mavecrit.pawars.item.mapcreator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetSpawn {
	public static ItemStack BlueSpawn(){
		ItemStack itemstack = new ItemStack(Material.WOOL, 1, (byte) 11);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§9Set blue spawn");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public static ItemStack RedSpawn(){
		ItemStack itemstack = new ItemStack(Material.WOOL, 1, (byte) 14);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§cSet red spawn");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}

	public static ItemStack GreenSpawn(){
		ItemStack itemstack = new ItemStack(Material.WOOL, 1, (byte) 5);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§aSet green spawn");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public static ItemStack YellowSpawn(){
		ItemStack itemstack = new ItemStack(Material.WOOL, 1, (byte) 4);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§eSet yellow spawn");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}

}
