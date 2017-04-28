package de.mavecrit.pawars.item.mapcreator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetPou {

	public static ItemStack BluePou(){
		ItemStack itemstack = new ItemStack(Material.STAINED_CLAY, 1, (byte) 11);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§9Set blue Pou");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public static ItemStack RedPou(){
		ItemStack itemstack = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§cSet red Pou");
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public static ItemStack GreenPou(){
		ItemStack itemstack = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§aSet green Pou");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public static ItemStack YellowPou(){
		ItemStack itemstack = new ItemStack(Material.STAINED_CLAY, 1, (byte) 4);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§eSet yellow Pou");
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
}
