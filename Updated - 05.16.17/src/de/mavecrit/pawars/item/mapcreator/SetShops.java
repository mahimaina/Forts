package de.mavecrit.pawars.item.mapcreator;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class SetShops {
	public static ItemStack BlueShop() {

		ItemStack itemStack = new ItemStack(Material.POTION, 1, (short) 8197);
		ItemMeta itemMeta = itemStack.getItemMeta();
		PotionMeta pm = (PotionMeta) itemMeta;
		pm.setDisplayName("§9Set blue shop");
		pm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		pm.setColor(Color.BLUE);

		itemStack.setItemMeta(pm);
		return itemStack;
	}
	
	public static ItemStack RedShop() {

		ItemStack itemStack = new ItemStack(Material.POTION, 1, (short) 8197);
		ItemMeta itemMeta = itemStack.getItemMeta();
		PotionMeta pm = (PotionMeta) itemMeta;
		pm.setDisplayName("§cSet red shop");
		pm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		pm.setColor(Color.RED);

		itemStack.setItemMeta(pm);
		return itemStack;
	}
	
	public static ItemStack GreenShop() {

		ItemStack itemStack = new ItemStack(Material.POTION, 1, (short) 8197);
		ItemMeta itemMeta = itemStack.getItemMeta();
		PotionMeta pm = (PotionMeta) itemMeta;
		pm.setDisplayName("§aSet green shop");
		pm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		pm.setColor(Color.GREEN);

		itemStack.setItemMeta(pm);
		return itemStack;
	}
	
	public static ItemStack YellowShop() {

		ItemStack itemStack = new ItemStack(Material.POTION, 1, (short) 8197);
		ItemMeta itemMeta = itemStack.getItemMeta();
		PotionMeta pm = (PotionMeta) itemMeta;
		pm.setDisplayName("§eSet yellow shop");
		pm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		pm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		pm.setColor(Color.YELLOW);

		itemStack.setItemMeta(pm);
		return itemStack;
	}
	
}
