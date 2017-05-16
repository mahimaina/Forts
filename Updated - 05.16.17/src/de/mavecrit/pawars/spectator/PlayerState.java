package de.mavecrit.pawars.spectator;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class PlayerState
{
  public Player player;
  public ItemStack[] inventory;
  public ItemStack[] armor;
  public int hunger;
  public double health;
  public int level;
  public float exp;
  public int slot;
  public boolean allowFlight;
  public boolean isFlying;
  public GameMode mode;
  public Location location;
  public Collection<PotionEffect> potions;
  public ArrayList<Player> vanishedFrom;

  public PlayerState(Player p)
  {
    this.vanishedFrom = new ArrayList();
    this.player = p;
    this.inventory = p.getInventory().getContents();
    this.armor = p.getInventory().getArmorContents();
    this.hunger = p.getFoodLevel();
    this.health = p.getHealth();
    this.level = p.getLevel();
    this.exp = p.getExp();
    this.slot = p.getInventory().getHeldItemSlot();
    this.allowFlight = p.getAllowFlight();
    this.isFlying = p.isFlying();
    this.mode = p.getGameMode();
    this.location = p.getLocation();
    this.potions = p.getActivePotionEffects();
    for (Player players : Bukkit.getServer().getOnlinePlayers())
      if ((players != p) && (!players.canSee(p)))
        this.vanishedFrom.add(players);
  }
}