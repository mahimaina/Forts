package de.mavecrit.pawars.item;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import de.mavecrit.pawars.Main;


public class CreaterItems {

	public static ItemStack Finish(){
		ItemStack itemstack = new ItemStack(Material.NETHER_STAR);
		
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName("§dFinish");

		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	


}
