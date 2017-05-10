package de.mavecrit.pawars.item;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.lists.Cooldown;


public class Empty {

	
	public static ItemStack Empty(Player p){
		ItemStack itemstack = new ItemStack(Material.MONSTER_EGG);
		if(Main.getPlugin().getConfig().getBoolean("Items.Empty.Glow")){
			itemstack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		}
		SpawnEggMeta itemmeta = (SpawnEggMeta) itemstack.getItemMeta();
		itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.Empty.Displayname")));
		itemmeta.setSpawnedType(EntityType.valueOf(Main.getPlugin().getConfig().getString("Items.Empty.EggColor")));
		ArrayList<String> lore1 = new ArrayList<String>();
		String[] without_split = Main.getPlugin().getConfig().getString("Items.Empty.Lore").split(";");
		for(int i = 0; i < without_split.length; i++){
		String withSplit = ChatColor.translateAlternateColorCodes('&', without_split[i].replace("{empty}", "§0§r"));
		lore1.add(withSplit);
		}
		
		itemmeta.setLore(lore1);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}

}
