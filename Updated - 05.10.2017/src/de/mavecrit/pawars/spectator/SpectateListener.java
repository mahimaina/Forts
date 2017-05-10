package de.mavecrit.pawars.spectator;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

import de.mavecrit.pawars.Main;


public class SpectateListener
  implements Listener
{
  Main plugin;

  public SpectateListener(Main plugin)
  {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    for (Player p : Main.getAPI().getSpectatingPlayers())
      event.getPlayer().hidePlayer(p);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    if (Main.getAPI().isSpectating(event.getPlayer())) {
    	Main.getAPI().stopSpectating(event.getPlayer(), true);
    }
    else if (Main.getAPI().isBeingSpectated(event.getPlayer()))
      for (Player p : Main.getAPI().getSpectators(event.getPlayer()))
        if ((Main.getAPI().getSpectateMode(p) == SpectateMode.SCROLL) || (Main.getAPI().isScanning(p))) {
          SpectateScrollEvent scrollEvent = new SpectateScrollEvent(p, Main.getAPI().getSpectateablePlayers(), ScrollDirection.RIGHT);
          Bukkit.getServer().getPluginManager().callEvent(scrollEvent);
          ArrayList playerList = scrollEvent.getSpectateList();
          playerList.remove(p);
          playerList.remove(event.getPlayer());
          p.sendMessage(ChatColor.GRAY + "The person you were previously spectating has disconnected.");
          if (!Main.getAPI().scrollRight(p, playerList))
          {
        	  Main.getAPI().stopSpectating(p, true);
            p.sendMessage(ChatColor.GRAY + "You were forced to stop spectating because there is nobody left to spectate.");
          }
        } else {
        	Main.getAPI().stopSpectating(p, true);
          p.sendMessage(ChatColor.GRAY + "You were forced to stop spectating because the person you were spectating disconnected.");
        }
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event)
  {
    if (Main.getAPI().isBeingSpectated(event.getEntity()))
      for (Player p : Main.getAPI().getSpectators(event.getEntity()))
        if ((Main.getAPI().getSpectateMode(p) == SpectateMode.SCROLL) || (Main.getAPI().isScanning(p))) {
          SpectateScrollEvent scrollEvent = new SpectateScrollEvent(p, Main.getAPI().getSpectateablePlayers(), ScrollDirection.RIGHT);
          Bukkit.getServer().getPluginManager().callEvent(scrollEvent);
          ArrayList playerList = scrollEvent.getSpectateList();
          playerList.remove(p);
          playerList.remove(event.getEntity());
          p.sendMessage(ChatColor.GRAY + "The person you were previously spectating has died.");
          if (!Main.getAPI().scrollRight(p, playerList))
          {
            Main.getAPI().stopSpectating(p, true);
            p.sendMessage(ChatColor.GRAY + "You were forced to stop spectating because there is nobody left to spectate.");
          }
        } else {
          Main.getAPI().stopSpectating(p, true);
          p.sendMessage(ChatColor.GRAY + "You were forced to stop spectating because the person you were spectating died.");
        }
  }

  @EventHandler
  public void onPlayerDamage(EntityDamageEvent event)
  {
    if ((event instanceof EntityDamageByEntityEvent)) {
      EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent)event;
      if (((event2.getDamager() instanceof Player)) && (Main.getAPI().isSpectating((Player)event2.getDamager()))) {
        event.setCancelled(true);
        return;
      }
    }
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    Player p = (Player)event.getEntity();
    if (Main.getAPI().isSpectating(p))
      event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    if (Main.getAPI().isSpectating(event.getPlayer())) {
      if ((Main.getAPI().isReadyForNextScroll(event.getPlayer())) && (Main.getAPI().getSpectateMode(event.getPlayer()) == SpectateMode.SCROLL)) {
        if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
          if (Bukkit.getServer().getOnlinePlayers().size() > 2) {
            Main.getAPI().scrollLeft(event.getPlayer(), Main.getAPI().getSpectateablePlayers());
            Main.getAPI().disableScroll(event.getPlayer(), 5L);
          }
        }
        else if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) && (Bukkit.getServer().getOnlinePlayers().size() > 2)) {
          Main.getAPI().scrollRight(event.getPlayer(), Main.getAPI().getSpectateablePlayers());
          Main.getAPI().disableScroll(event.getPlayer(), 5L);
        }
      }
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    if (Main.getAPI().isSpectating(event.getPlayer())) {
      if ((Main.getAPI().isReadyForNextScroll(event.getPlayer())) && (Main.getAPI().getSpectateMode(event.getPlayer()) == SpectateMode.SCROLL) && (Bukkit.getServer().getOnlinePlayers().size() > 2)) {
        Main.getAPI().scrollRight(event.getPlayer(), Main.getAPI().getSpectateablePlayers());
        Main.getAPI().disableScroll(event.getPlayer(), 5L);
      }
      event.setCancelled(true);
    }
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
    if ((event.getEntity() instanceof Player)) {
      Player player = (Player)event.getEntity();
      if ((!event.isCancelled()) && (Main.getAPI().isBeingSpectated(player)))
        for (Player p : Main.getAPI().getSpectators(player))
          p.setFoodLevel(event.getFoodLevel());
    }
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerGameModeChange(PlayerGameModeChangeEvent event)
  {
    if ((!event.isCancelled()) && (Main.getAPI().isBeingSpectated(event.getPlayer())))
      for (Player p : Main.getAPI().getSpectators(event.getPlayer()))
        p.setGameMode(event.getNewGameMode());
  }

  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent event)
  {
    if (!(event.getPlayer() instanceof Player)) {
      return;
    }
    Player p = (Player)event.getPlayer();
    if (Main.getAPI().isBeingSpectated(p))
      for (Player spectators : Main.getAPI().getSpectators(p))
        spectators.openInventory(event.getInventory());
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event)
  {
    if (!(event.getPlayer() instanceof Player)) {
      return;
    }
    Player p = (Player)event.getPlayer();
    if (Main.getAPI().isBeingSpectated(p))
      for (Player spectators : Main.getAPI().getSpectators(p))
        spectators.closeInventory();
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event)
  {
    if (!(event.getWhoClicked() instanceof Player)) {
      return;
    }
    Player p = (Player)event.getWhoClicked();
    if (Main.getAPI().isSpectating(p))
      event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent event)
  {
    if (Main.getAPI().isSpectating(event.getPlayer()))
      event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent event)
  {
    if (Main.getAPI().isSpectating(event.getPlayer()))
      event.setCancelled(true);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event)
  {
    if (Main.getAPI().isSpectating(event.getPlayer()))
      event.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event)
  {
    if (Main.getAPI().isSpectating(event.getPlayer()))
      event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerRegen(EntityRegainHealthEvent event)
  {
    if ((event.getEntity() instanceof Player)) {
      Player p = (Player)event.getEntity();
      if (Main.getAPI().isSpectating(p))
        event.setCancelled(true);
    }
  }

  @EventHandler
  public void onMobTarget(EntityTargetEvent event)
  {
    if (((event.getEntity() instanceof Monster)) && ((event.getTarget() instanceof Player)) && (Main.getAPI().isSpectating((Player)event.getTarget())))
      event.setCancelled(true);
  }

  @EventHandler
  public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
  {
    if ((Main.getAPI().isSpectating(event.getPlayer())) && (this.plugin.disable_commands) && (!event.getMessage().startsWith("/spectate")) && (!event.getMessage().startsWith("/spec"))) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(ChatColor.RED + "You can not execute this command while spectating.");
    }
  }
}