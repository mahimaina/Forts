package de.mavecrit.pawars.spectator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import de.mavecrit.pawars.Main;


public class SpectateManager
{
  private Main plugin;
  private int spectateTask;
  private ArrayList<Player> isSpectating;
  private ArrayList<Player> isBeingSpectated;
  private HashMap<Player, ArrayList<Player>> spectators;
  private HashMap<Player, Player> target;
  private ArrayList<String> isClick;
  private HashMap<String, SpectateMode> playerMode;
  private HashMap<String, SpectateAngle> playerAngle;
  private ArrayList<String> isScanning;
  private HashMap<String, Integer> scanTask;
  private HashMap<Player, PlayerState> states;
  private HashMap<Player, PlayerState> multiInvStates;
  private ArrayList<String> inventoryOff;

  public SpectateManager(Main plugin)
  {
    this.spectateTask = -1;
    this.isSpectating = new ArrayList();
    this.isBeingSpectated = new ArrayList();
    this.spectators = new HashMap();
    this.target = new HashMap();
    this.isClick = new ArrayList();
    this.playerMode = new HashMap();
    this.playerAngle = new HashMap();
    this.isScanning = new ArrayList();
    this.scanTask = new HashMap();
    this.states = new HashMap();
    this.multiInvStates = new HashMap();
    this.inventoryOff = new ArrayList();
    this.plugin = plugin;
  }

  private void updateSpectators() {
    this.spectateTask = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
    {
      public void run() {
        for (Player p : SpectateManager.this.plugin.getServer().getOnlinePlayers())
          if (SpectateManager.this.isSpectating(p))
            if ((SpectateManager.this.plugin.multiverseInvEnabled()) && (!p.getWorld().getName().equals(SpectateManager.this.getTarget(p).getWorld().getName()))) {
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Spectate.SwitchedWorlds")));
              SpectateManager.this.stopSpectating(p, true);
            }
            else {
              if (SpectateManager.this.getSpectateAngle(p) == SpectateAngle.FIRST_PERSON) {
                if ((SpectateManager.this.roundTwoDecimals(p.getLocation().getX()) != SpectateManager.this.roundTwoDecimals(SpectateManager.this.getTarget(p).getLocation().getX())) || (SpectateManager.this.roundTwoDecimals(p.getLocation().getY()) != SpectateManager.this.roundTwoDecimals(SpectateManager.this.getTarget(p).getLocation().getY())) || (SpectateManager.this.roundTwoDecimals(p.getLocation().getZ()) != SpectateManager.this.roundTwoDecimals(SpectateManager.this.getTarget(p).getLocation().getZ())) || (SpectateManager.this.roundTwoDecimals(p.getLocation().getYaw()) != SpectateManager.this.roundTwoDecimals(SpectateManager.this.getTarget(p).getLocation().getYaw())) || (SpectateManager.this.roundTwoDecimals(p.getLocation().getPitch()) != SpectateManager.this.roundTwoDecimals(SpectateManager.this.getTarget(p).getLocation().getPitch()))) {
                  p.teleport(SpectateManager.this.getTarget(p));
                }
              }
              else if (SpectateManager.this.getSpectateAngle(p) != SpectateAngle.FREEROAM) {
                p.teleport(SpectateManager.this.getSpectateLocation(p));
              }
              if (!SpectateManager.this.inventoryOff.contains(p.getName())) {
                p.getInventory().setContents(SpectateManager.this.getTarget(p).getInventory().getContents());
                p.getInventory().setArmorContents(SpectateManager.this.getTarget(p).getInventory().getArmorContents());
              }
              if (SpectateManager.this.getTarget(p).getHealth() == 0.0D) {
                p.setHealth(1.0D);
              }
              else if (SpectateManager.this.getTarget(p).getHealth() < p.getHealth()) {
                double difference = p.getHealth() - SpectateManager.this.getTarget(p).getHealth();
                p.damage(difference);
              }
              else if (SpectateManager.this.getTarget(p).getHealth() > p.getHealth()) {
                p.setHealth(SpectateManager.this.getTarget(p).getHealth());
              }
              p.setLevel(SpectateManager.this.getTarget(p).getLevel());
              p.setExp(SpectateManager.this.getTarget(p).getExp());
              for (PotionEffect e : p.getActivePotionEffects()) {
                boolean foundPotion = false;
                for (PotionEffect e2 : SpectateManager.this.getTarget(p).getActivePotionEffects()) {
                  if (e2.getType() == e.getType()) {
                    foundPotion = true;
                    break;
                  }
                }
                if (!foundPotion) {
                  p.removePotionEffect(e.getType());
                }
              }
              for (PotionEffect e : SpectateManager.this.getTarget(p).getActivePotionEffects()) {
                p.addPotionEffect(e);
              }
              if (!SpectateManager.this.inventoryOff.contains(p.getName())) {
                p.getInventory().setHeldItemSlot(SpectateManager.this.getTarget(p).getInventory().getHeldItemSlot());
              }
              if ((SpectateManager.this.getTarget(p).isFlying()) && (!p.isFlying()))
              {
                p.setFlying(true);
              }
            }
      }
    }
    , 0L, 1L);
  }

  public void startSpectateTask() {
    if (this.spectateTask == -1)
      updateSpectators();
  }

  public void stopSpectateTask()
  {
    if (this.spectateTask != -1) {
      this.plugin.getServer().getScheduler().cancelTask(this.spectateTask);
      this.spectateTask = -1;
    }
  }

  public void startSpectating(Player p, Player target, boolean saveState) {
    if ((!isSpectating(p)) && (saveState)) {
      savePlayerState(p);
    }
    boolean saveMultiInvState = false;
    if ((this.plugin.multiverseInvEnabled()) && (!p.getWorld().getName().equals(target.getWorld().getName()))) {
      saveMultiInvState = true;
    }
    for (Player player1 : this.plugin.getServer().getOnlinePlayers()) {
      player1.hidePlayer(p);
    }
    if (saveMultiInvState) {
      p.teleport(target.getWorld().getSpawnLocation());
      this.multiInvStates.put(p, new PlayerState(p));
    }
    String playerListName = p.getPlayerListName();
    if (getSpectateAngle(p) == SpectateAngle.FIRST_PERSON) {
      p.hidePlayer(target);
    }
    else {
      p.showPlayer(target);
    }
    p.setPlayerListName(playerListName);
    p.setHealth(target.getHealth());
    p.teleport(target);
    if (isSpectating(p)) {
      setBeingSpectated(getTarget(p), false);
      p.showPlayer(getTarget(p));
      removeSpectator(getTarget(p), p);
    }
    for (PotionEffect e : p.getActivePotionEffects()) {
      p.removePotionEffect(e.getType());
    }
    setTarget(p, target);
    addSpectator(target, p);
    p.setGameMode(target.getGameMode());
    p.setFoodLevel(target.getFoodLevel());
  //  setExperienceCooldown(p, 2147483647);
    p.setAllowFlight(true);
    setSpectating(p, true);
    setBeingSpectated(target, true);
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Spectate.Spectate").replace("{target}", target.getName())));
  }

  public void stopSpectating(Player p, boolean loadState) {
    setSpectating(p, false);
    setBeingSpectated(getTarget(p), false);
    removeSpectator(getTarget(p), p);
    if (isScanning(p)) {
      stopScanning(p);
    }
    for (PotionEffect e : p.getActivePotionEffects()) {
      p.removePotionEffect(e.getType());
    }
    if (loadState) {
      loadPlayerState(p);
    }
    //setExperienceCooldown(p, 0);
    p.showPlayer(getTarget(p));
  }

  public boolean scrollRight(Player p, ArrayList<Player> playerList) {
    SpectateScrollEvent event = new SpectateScrollEvent(p, playerList, ScrollDirection.RIGHT);
    this.plugin.getServer().getPluginManager().callEvent(event);
    playerList = new ArrayList(event.getSpectateList());
    playerList.remove(p);
    if (playerList.size() == 0) {
      return false;
    }
    if ((this.plugin.multiverseInvEnabled()) && (isScanning(p)))
      for (Player players : event.getSpectateList())
        if (!players.getWorld().getName().equals(p.getWorld().getName()))
          playerList.remove(players);
    int scrollToIndex;
    if (getScrollNumber(p, playerList) == playerList.size()) {
      scrollToIndex = 1;
    }
    else {
      scrollToIndex = getScrollNumber(p, playerList) + 1;
    }
    startSpectating(p, (Player)playerList.get(scrollToIndex - 1), false);
    return true;
  }

  public boolean scrollLeft(Player p, ArrayList<Player> playerList) {
    SpectateScrollEvent event = new SpectateScrollEvent(p, playerList, ScrollDirection.LEFT);
    this.plugin.getServer().getPluginManager().callEvent(event);
    playerList = new ArrayList(event.getSpectateList());
    playerList.remove(p);
    if (playerList.size() == 0) {
      return false;
    }
    if ((this.plugin.multiverseInvEnabled()) && (isScanning(p)))
      for (Player players : event.getSpectateList())
        if (!players.getWorld().getName().equals(p.getWorld().getName()))
          playerList.remove(players);
    int scrollToIndex;
    if (getScrollNumber(p, playerList) == 1) {
      scrollToIndex = playerList.size();
    }
    else {
      scrollToIndex = getScrollNumber(p, playerList) - 1;
    }
    startSpectating(p, (Player)playerList.get(scrollToIndex - 1), false);
    return true;
  }

  public int getScrollNumber(Player p, ArrayList<Player> playerList) {
    if (!isSpectating(p)) {
      return 1;
    }
    if (!playerList.contains(getTarget(p))) {
      return 1;
    }
    playerList.remove(p);
    return playerList.indexOf(getTarget(p)) + 1;
  }

  public void setSpectateMode(Player p, SpectateMode newMode) {
    if (newMode == SpectateMode.DEFAULT) {
      this.playerMode.remove(p.getName());
    }
    else
      this.playerMode.put(p.getName(), newMode);
  }

  public SpectateMode getSpectateMode(Player p)
  {
    if (this.playerMode.get(p.getName()) == null) {
      return SpectateMode.DEFAULT;
    }
    return (SpectateMode)this.playerMode.get(p.getName());
  }

  public void setSpectateAngle(Player p, SpectateAngle newAngle) {
    if (isSpectating(p)) {
      if (newAngle == SpectateAngle.FIRST_PERSON) {
        p.hidePlayer(getTarget(p));
      }
      else {
        p.showPlayer(getTarget(p));
      }
      if (newAngle == SpectateAngle.FREEROAM) {
        p.teleport(getTarget(p));
      }
    }
    if (newAngle == SpectateAngle.FIRST_PERSON) {
      this.playerAngle.remove(p.getName());
    }
    else
      this.playerAngle.put(p.getName(), newAngle);
  }

  public SpectateAngle getSpectateAngle(Player p)
  {
    if (this.playerAngle.get(p.getName()) == null) {
      return SpectateAngle.FIRST_PERSON;
    }
    return (SpectateAngle)this.playerAngle.get(p.getName());
  }

  public void startScanning(final Player p, int interval) {
    this.isScanning.add(p.getName());
    this.scanTask.put(p.getName(), Integer.valueOf(this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable()
    {
      public void run() {
        SpectateManager.this.scrollRight(p, SpectateManager.this.getSpectateablePlayers());
      }
    }
    , 0L, 20 * interval)));
  }

  public void stopScanning(Player p) {
    this.plugin.getServer().getScheduler().cancelTask(((Integer)this.scanTask.get(p.getName())).intValue());
    this.isScanning.remove(p.getName());
  }

  public boolean isScanning(Player p) {
    return this.isScanning.contains(p.getName());
  }

  public ArrayList<Player> getSpectateablePlayers() {
    ArrayList spectateablePlayers = new ArrayList();
    for (Player onlinePlayers : this.plugin.getServer().getOnlinePlayers())
      if (!onlinePlayers.isDead())
      {
        if (!this.isSpectating.contains(onlinePlayers))
        {
          if ((!this.plugin.cantspectate_permission_enabled) || (!onlinePlayers.hasPermission("spectate.cantspectate")))
          {
            spectateablePlayers.add(onlinePlayers);
          }
        }
      }
    return spectateablePlayers;
  }

  private void setTarget(Player p, Player ptarget) {
    this.target.put(p, ptarget);
  }

  public Player getTarget(Player p) {
    return (Player)this.target.get(p);
  }

  public boolean isSpectating(Player p) {
    return this.isSpectating.contains(p);
  }

  public boolean isBeingSpectated(Player p) {
    return this.isBeingSpectated.contains(p);
  }

  private void setBeingSpectated(Player p, boolean beingSpectated) {
    if (beingSpectated) {
      if (this.isBeingSpectated.contains(p)) {
        return;
      }
      this.isBeingSpectated.add(p);
    }
    else {
      this.isBeingSpectated.remove(p);
    }
  }

  private void addSpectator(Player p, Player spectator) {
    if (this.spectators.get(p) == null) {
      ArrayList newSpectators = new ArrayList();
      newSpectators.add(spectator);
      this.spectators.put(p, newSpectators);
    }
    else {
      ((ArrayList)this.spectators.get(p)).add(spectator);
    }
  }

  private void removeSpectator(Player p, Player spectator) {
    if (this.spectators.get(p) != null)
      if (((ArrayList)this.spectators.get(p)).size() == 1) {
        this.spectators.remove(p);
      }
      else
        ((ArrayList)this.spectators.get(p)).remove(spectator);
  }

  public ArrayList<Player> getSpectators(Player p)
  {
    return this.spectators.get(p) == null ? new ArrayList() : (ArrayList)this.spectators.get(p);
  }

  public ArrayList<Player> getSpectatingPlayers() {
    ArrayList spectatingPlayers = new ArrayList();
    for (Player p : this.plugin.getServer().getOnlinePlayers()) {
      if (isSpectating(p)) {
        spectatingPlayers.add(p);
      }
    }
    return spectatingPlayers;
  }

  private void setSpectating(Player p, boolean spectating) {
    if (spectating) {
      if (this.isSpectating.contains(p)) {
        return;
      }
      this.isSpectating.add(p);
    }
    else {
      this.isSpectating.remove(p);
    }
  }

  public void setModifyInventory(Player p, boolean modify) {
    if (modify) {
      if (this.inventoryOff.contains(p.getName())) {
        this.inventoryOff.remove(p.getName());
      }
    }
    else if (!this.inventoryOff.contains(p.getName()))
      this.inventoryOff.add(p.getName());
  }

  public void disableScroll(final Player player, long ticks)
  {
    if (!this.isClick.contains(player.getName())) {
      this.isClick.add(player.getName());
      this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
      {
        public void run() {
          SpectateManager.this.isClick.remove(player.getName());
        }
      }
      , ticks);
    }
  }

  public Location getSpectateLocation(Player p) {
    if (getSpectateAngle(p) == SpectateAngle.FIRST_PERSON) {
      return getTarget(p).getLocation();
    }
    Location playerLoc = getTarget(p).getLocation();
    double currentSubtraction = 0.0D;
    Location previousLoc = playerLoc;
    while (currentSubtraction <= 5.0D) {
      playerLoc = getTarget(p).getLocation();
      Vector v = getTarget(p).getLocation().getDirection().normalize();
      v.multiply(currentSubtraction);
      if (getSpectateAngle(p) == SpectateAngle.THIRD_PERSON) {
        playerLoc.subtract(v);
      }
      else if (getSpectateAngle(p) == SpectateAngle.THIRD_PERSON_FRONT) {
        playerLoc.add(v);
        if (playerLoc.getYaw() < -180.0F) {
          playerLoc.setYaw(playerLoc.getYaw() + 180.0F);
        }
        else {
          playerLoc.setYaw(playerLoc.getYaw() - 180.0F);
        }
        playerLoc.setPitch(-playerLoc.getPitch());
      }
      Material tempMat = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY() + 1.5D, playerLoc.getZ()).getBlock().getType();
      if ((tempMat != Material.AIR) && (tempMat != Material.WATER) && (tempMat != Material.STATIONARY_WATER)) {
        return previousLoc;
      }
      previousLoc = playerLoc;
      currentSubtraction += 0.5D;
    }
    return playerLoc;
  }

  public PlayerState getPlayerState(Player p) {
    return (PlayerState)this.states.get(p);
  }

  public void savePlayerState(Player p) {
    PlayerState playerstate = new PlayerState(p);
    this.states.put(p, playerstate);
  }

  public void loadPlayerState(Player toPlayer) {
    loadPlayerState(toPlayer, toPlayer);
  }

  public void loadPlayerState(Player fromState, Player toPlayer) {
    if ((this.plugin.multiverseInvEnabled()) && (this.multiInvStates.get(fromState) != null)) {
      loadFinalState((PlayerState)this.multiInvStates.get(fromState), toPlayer);
      this.multiInvStates.remove(fromState);
    }
    loadFinalState(getPlayerState(fromState), toPlayer);
    this.states.remove(fromState);
  }

  private void loadFinalState(PlayerState state, Player toPlayer) {
    toPlayer.teleport(state.location);
    toPlayer.getInventory().setContents(state.inventory);
    toPlayer.getInventory().setArmorContents(state.armor);
    toPlayer.setFoodLevel(state.hunger);
    toPlayer.setHealth(state.health);
    toPlayer.setLevel(state.level);
    toPlayer.setExp(state.exp);
    toPlayer.getInventory().setHeldItemSlot(state.slot);
    toPlayer.setAllowFlight(state.allowFlight);
    toPlayer.setFlying(state.isFlying);
    toPlayer.setGameMode(state.mode);
    for (Player onlinePlayers : this.plugin.getServer().getOnlinePlayers()) {
      if (!state.vanishedFrom.contains(onlinePlayers)) {
        onlinePlayers.showPlayer(toPlayer);
      }
    }
    for (PotionEffect e : state.potions)
      toPlayer.addPotionEffect(e);
  }

  public ArrayList<Player> getVanishedFromList(Player p)
  {
    return getPlayerState(p).vanishedFrom;
  }

 /* public void setExperienceCooldown(Player p, int cooldown) {
    try {
      Method handle = p.getClass().getDeclaredMethod("getHandle", new Class[0]);
      Object entityPlayer = handle.invoke(p, new Object[0]);
      Field cooldownField = entityPlayer.getClass().getSuperclass().getDeclaredField("bu");
      cooldownField.setAccessible(true);
      cooldownField.setInt(entityPlayer, cooldown);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }*/

  public boolean isReadyForNextScroll(Player p) {
    return !this.isClick.contains(p.getName());
  }

  public double roundTwoDecimals(double d) {
    try {
      DecimalFormat twoDForm = new DecimalFormat("#.##");
      return Double.valueOf(twoDForm.format(d)).doubleValue();
    } catch (NumberFormatException e) {
    }
    return d;
  }
}