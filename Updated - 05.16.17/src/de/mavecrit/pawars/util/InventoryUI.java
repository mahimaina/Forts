package de.mavecrit.pawars.util;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.mavecrit.pawars.Main;
import net.md_5.bungee.api.ChatColor;



public class InventoryUI
{
  private Inventory inv;
  private int slots = 0;
  private String title;

  public void createInventory()
  {
    this.inv = Bukkit.createInventory(null, this.slots, this.title);
  }

  public void createHopperInventory() {
    this.inv = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("GUI.Spectator.Name")));
  }

  public InventoryUI setTitle(String title) {
    this.title = title;
    return this;
  }

  public InventoryUI setSlots(int slots) {
    this.slots = slots;
    return this;
  }

  public InventoryUI setItem(int slot, ItemStack item) {
    this.inv.setItem(slot, item);
    return this;
  }

  public InventoryUI setContents(ItemStack[] content) {
    this.inv.setContents(content);
    return this;
  }

  public Inventory getInventory() {
    return this.inv;
  }

  public ItemStack createItem(Material mat, int amount, String title, List<String> lore) {
    ItemStack item = new ItemStack(mat, amount);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(title);
    meta.setLore(lore);
    item.setItemMeta(meta);
    return item;
  }

  public ItemStack createItem(Material mat, int amount, String title, List<String> lore, short damage) {
    ItemStack item = new ItemStack(mat, amount, damage);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(title);
    meta.setLore(lore);
    item.setItemMeta(meta);
    return item;
  }

  public ItemStack createPlayerSkull(String plrName) {
    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    ItemMeta meta = item.getItemMeta();
    if ((meta instanceof SkullMeta)) {
      SkullMeta m = (SkullMeta)meta;
      m.setDisplayName(plrName);
      m.setOwner(plrName);
      item.setItemMeta(m);
      return item;
    }
    return null;
  }
}