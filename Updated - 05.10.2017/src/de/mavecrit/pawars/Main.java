package de.mavecrit.pawars;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.apihelper.APIManager;

import de.mavecrit.pawars.commands.ArenaCreate;
import de.mavecrit.pawars.entitys.CustomVillager;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.entitys.nms.CustomEntityRegistry;
import de.mavecrit.pawars.events.BlockBreak;
import de.mavecrit.pawars.events.ClickOnTeam;
import de.mavecrit.pawars.events.Disconnect;
import de.mavecrit.pawars.events.EntityCombust;
import de.mavecrit.pawars.events.EntityDamage;
import de.mavecrit.pawars.events.EntityDamageByEntity;
import de.mavecrit.pawars.events.EntityDeath;
import de.mavecrit.pawars.events.EntityTarget;
import de.mavecrit.pawars.events.HeldItemChange;
import de.mavecrit.pawars.events.Interact;
import de.mavecrit.pawars.events.InventoryClose;
import de.mavecrit.pawars.events.PlayerDeath;
import de.mavecrit.pawars.events.PlayerJoin;
import de.mavecrit.pawars.events.PlayerRespawn;
import de.mavecrit.pawars.events.ProjectileHit;
import de.mavecrit.pawars.events.RandomSpectateToggle;
import de.mavecrit.pawars.events.State;
import de.mavecrit.pawars.events.Teleport;
import de.mavecrit.pawars.events.mapcreator.InteractArenaOutBorders;
import de.mavecrit.pawars.events.mapcreator.InteractEventPou;
import de.mavecrit.pawars.events.mapcreator.InteractEventShop;
import de.mavecrit.pawars.events.mapcreator.InteractEventSpawn;
import de.mavecrit.pawars.events.mapcreator.InteractEventWaypoint;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.guis.TeamSelectGUI;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.maps.ChooseRandomMaps;
import de.mavecrit.pawars.spectator.SpectateManager;
import de.mavecrit.pawars.spectator.SpectatorInv;
import de.mavecrit.pawars.util.GlowAPI;
import de.mavecrit.pawars.util.SimpleConfig;
import de.mavecrit.pawars.util.SimpleConfigManager;

public class Main extends JavaPlugin implements Listener {
	public static SimpleConfigManager manager;
	public static SimpleConfig locations;
	private static SpectateManager Manager;
	public boolean cantspectate_permission_enabled;
	public boolean disable_commands;
	public static Main plugin;
	GlowAPI glowAPI = new GlowAPI();
	
	
	public void loadConfiguration() {
		plugin.getConfig().addDefault("General.ServerSize", 16);
		plugin.getConfig().addDefault("General.TeamSize", 4);
		plugin.getConfig().addDefault("General.MinPlayers", 4);
		plugin.getConfig().addDefault("General.Voting", true);
		plugin.getConfig().addDefault("General.VoteableMaps", 3);
		plugin.getConfig().addDefault("General.PouBlock", "PRISMARINE");
		plugin.getConfig().addDefault("General.WallBlock", "SANDSTONE"); 
		plugin.getConfig().addDefault("General.ItemCooldown", 20); 
		plugin.getConfig().addDefault("General.MobGroupSize", 5); 
		plugin.getConfig().addDefault("General.WaitingTimer", 25); 
		plugin.getConfig().addDefault("General.WaypointScheduler", 10); 
		plugin.getConfig().addDefault("General.BreakableBlocks", "DIRT,STONE,GLOWSTONE"); 
		
		plugin.getConfig().addDefault("General.GUI.Placeholder.Material", "STAINED_GLASS_PANE");
		plugin.getConfig().addDefault("General.GUI.Placeholder.Amount", 1);
		plugin.getConfig().addDefault("General.GUI.Placeholder.Byte", 15);
		plugin.getConfig().addDefault("General.GUI.Placeholder.Displayname", "{empty}");
		plugin.getConfig().addDefault("General.GUI.Placeholder.Lore", "{empty}");
		plugin.getConfig().addDefault("General.GUI.Placeholder.Glow", false);

		plugin.getConfig().addDefault("Messages.Error.Reopen", "&cYou cannot open this item twice");
		plugin.getConfig().addDefault("Messages.Error.TooFar", "&cYou are too far away");
		plugin.getConfig().addDefault("Messages.Error.NotEnoughPlayers", "&cNot enough players");
		plugin.getConfig().addDefault("Messages.Error.Permission", "&cI dont think you're supposed to do that.");
		plugin.getConfig().addDefault("Messages.Error.Units", "&cThe unit you have send out is still alive, left zombies: {count}");
		plugin.getConfig().addDefault("Messages.Error.NotYours", "&cThis isn't your unit!");
		plugin.getConfig().addDefault("Messages.JoinedTeam", "&aYou've joined team: {team}");
		plugin.getConfig().addDefault("Messages.LeftTeam", "&cYou left your current team");
		plugin.getConfig().addDefault("Messages.TeamMix", "&cWe changed your team (for fairness reasons) to: {team}");
		plugin.getConfig().addDefault("Messages.Spawning", "&aOne of your {type} units has died, you can spawn a new one");
		plugin.getConfig().addDefault("Messages.Countdown.Chat", "&6The game starts in {timer}");
		plugin.getConfig().addDefault("Messages.Countdown.Actionbar.Enabled", true);
		plugin.getConfig().addDefault("Messages.Countdown.Actionbar.Message", "&6Starting in: &e{timer}");
		plugin.getConfig().addDefault("Messages.PleaseClick", "&aPlease leftclick the location where the zombies should walk to&7,&c rightclick to cancel");
		plugin.getConfig().addDefault("Messages.PouDestroyed", "&cThe pou of team {team} is destroyed!");
		plugin.getConfig().addDefault("Messages.RestLife", "&cThis blocks restlife: {hearts}");
		
		plugin.getConfig().addDefault("Messages.Spectate.SpectatorDied", "&cThe person you were spectating has died.");
		plugin.getConfig().addDefault("Messages.Spectate.SwitchedWorlds", "&cThe person you were spectating switched worlds.");
		plugin.getConfig().addDefault("Messages.Spectate.Spectate", "&7You are now spectating {target}");
		
		plugin.getConfig().addDefault("MOTD.Running", "&4In-Game");
		plugin.getConfig().addDefault("MOTD.Waiting", "&2Waiting");
		plugin.getConfig().addDefault("MOTD.Full", "&6FULL");
		
		plugin.getConfig().addDefault("Titles.VotedMap.Title", "&7Voting ended!");
		plugin.getConfig().addDefault("Titles.VotedMap.SubTitle", "&7Winner: &a{map}");
		
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Enabled", true);
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Material", "WOOD_SWORD");
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Amount", 1);
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Byte", 0);
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Glow", false);
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Displayname", "&9Team-Selector");
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Lore",
				"&7Rightlick to open;&8Leftclick for informations");
		plugin.getConfig().addDefault("Items.JoinItems.TeamSelector.Slot", 0);
		
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Material", "PAPER");
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Amount", 1);
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Byte", 0);
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Glow", true);
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Displayname", "&9Team-Selector");
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Lore",
				"&7Rightlick to open;&8Leftclick for informations");
		plugin.getConfig().addDefault("Items.JoinItems.Vote.Slot", 0);
		
		plugin.getConfig().addDefault("Items.Spectate.Material", "COMPASS");
		plugin.getConfig().addDefault("Items.Spectate.Amount", 1);
		plugin.getConfig().addDefault("Items.Spectate.Byte", 0);
		plugin.getConfig().addDefault("Items.Spectate.Glow", true);
		plugin.getConfig().addDefault("Items.Spectate.Displayname", "&9Spectate");
		plugin.getConfig().addDefault("Items.Spectate.Lore",
				"&7Rightlick to open");
		plugin.getConfig().addDefault("Items.Spectate.Slot", 8);
		
		plugin.getConfig().addDefault("Items.DefaultItems.1.Enabled", true);
		plugin.getConfig().addDefault("Items.DefaultItems.1.Material", "WOOD_SWORD");
		plugin.getConfig().addDefault("Items.DefaultItems.1.Amount", 1);
		plugin.getConfig().addDefault("Items.DefaultItems.1.Byte", 0);
		plugin.getConfig().addDefault("Items.DefaultItems.1.Glow", false);
		plugin.getConfig().addDefault("Items.DefaultItems.1.Displayname", "&4The killer");
		plugin.getConfig().addDefault("Items.DefaultItems.1.Lore",
				"&7Kill them with the force Luke");
		plugin.getConfig().addDefault("Items.DefaultItems.1.Slot", 0);
		
		plugin.getConfig().addDefault("Items.DefaultItems.2.Enabled", true);
		plugin.getConfig().addDefault("Items.DefaultItems.2.Material", "APPLE");
		plugin.getConfig().addDefault("Items.DefaultItems.2.Amount", 21);
		plugin.getConfig().addDefault("Items.DefaultItems.2.Byte", 0);
		plugin.getConfig().addDefault("Items.DefaultItems.2.Glow", false);
		plugin.getConfig().addDefault("Items.DefaultItems.2.Displayname", "&4The appler");
		plugin.getConfig().addDefault("Items.DefaultItems.2.Lore",
				"&7Kill them with the force Luke");
		plugin.getConfig().addDefault("Items.DefaultItems.1.Slot", 1);
		
		plugin.getConfig().addDefault("Items.Empty.EggColor", "GHAST");
		plugin.getConfig().addDefault("Items.Empty.Glow", true);
		plugin.getConfig().addDefault("Items.Empty.Displayname", "&7Loading egg.. &8- &a{time}");
		plugin.getConfig().addDefault("Items.Empty.Lore","&8Time left: &a{time}");
		
		plugin.getConfig().addDefault("Items.Zombies.1.EggColor", "CREEPER");
		plugin.getConfig().addDefault("Items.Zombies.1.Glow", false);
		plugin.getConfig().addDefault("Items.Zombies.1.Displayname", "Attacker");
		plugin.getConfig().addDefault("Items.Zombies.1.Lore",
				"&8Rightclick to use");
		plugin.getConfig().addDefault("Items.Zombies.1.Assigned", "Attack");
		plugin.getConfig().addDefault("Items.Zombies.1.GiveOnStart", true);
		
		plugin.getConfig().addDefault("Items.Zombies.2.EggColor", "PIG");
		plugin.getConfig().addDefault("Items.Zombies.2.Glow", false);
		plugin.getConfig().addDefault("Items.Zombies.2.Displayname", "Healer");
		plugin.getConfig().addDefault("Items.Zombies.2.Lore",
				"&8Rightclick to use");
		plugin.getConfig().addDefault("Items.Zombies.2.Assigned", "Healer");
		plugin.getConfig().addDefault("Items.Zombies.2.GiveOnStart", true);
		
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.Displayname", "Zombie");
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.Speed",0.28D);
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.Damage", 3);
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.FollowRange", 20);
		
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.Displayname", "Zombie");
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.Speed",0.28D);
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.Potion", "INSTANT_DAMAGE");
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.FollowRange", 20);
		
		plugin.getConfig().addDefault("Mobs.Shop.Displayname", "Shop");
				
		plugin.getConfig().addDefault("GUI.Vote.Size", 9);
		plugin.getConfig().addDefault("GUI.Vote.Name", "&5Vote");
		plugin.getConfig().addDefault("GUI.Vote.Placeholders", false);
		plugin.getConfig().addDefault("GUI.Vote.Reopenable", false);
		plugin.getConfig().addDefault("GUI.Vote.Items.Material", "PAPER");
		plugin.getConfig().addDefault("GUI.Vote.Items.Lore", "&6Current votes: &7{votes}");

		plugin.getConfig().addDefault("GUI.Spectator.Name", "&aSpectator menu");
		
		plugin.getConfig().addDefault("GUI.TeamSelector.Size", 18);
		plugin.getConfig().addDefault("GUI.TeamSelector.Name", "&7Select team");
		plugin.getConfig().addDefault("GUI.TeamSelector.Placeholders", true);
		plugin.getConfig().addDefault("GUI.TeamSelector.Reopenable", true);

		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Material", "WOOL");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Amount", 1);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Byte", 11);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Glow", false);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Displayname", "&9Team Blue");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Lore",
				"&7Rightlick to open;&8Leftclick for informations");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.Slot", 1);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.0.joins", "blue");

		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Material", "WOOL");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Amount", 1);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Byte", 14);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Glow", false);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Displayname", "&cTeam Red");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Lore",
				"&7Rightlick to open;&8Leftclick for informations");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.Slot", 3);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.1.joins", "red");

		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Material", "WOOL");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Amount", 1);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Byte", 5);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Glow", false);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Displayname", "&aTeam Green");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Lore",
				"&7Rightlick to open;&8Leftclick for informations");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.Slot", 5);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.2.joins", "green");

		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Material", "WOOL");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Amount", 1);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Byte", 4);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Glow", false);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Displayname", "&eTeam Yellow");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Lore",
				"&7Rightlick to open;&8Leftclick for informations");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.Slot", 7);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.Teams.3.joins", "yellow");

		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Material", "BEACON");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Amount", 1);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Byte", 0);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Glow", true);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Displayname", "&7Current teams");
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Slot", 13);
		plugin.getConfig().addDefault("GUI.TeamSelector.Items.CurrentTeams.Enabled", true);

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}

	
	@Override
	public void onLoad() {
		//Register this API if the plugin got loaded
		APIManager.registerAPI(glowAPI, this);
	}

	
	public void onEnable() {

		plugin = this;
		CustomEntityRegistry.registerCustomEntity(54, "zombie", CustomZombie.class);
		CustomEntityRegistry.registerCustomEntity(120, "villager", CustomVillager.class);
		
		loadConfiguration();
		manager = new SimpleConfigManager(this);

		String[] header = { "Do NOT edit." };
		locations = manager.getNewConfig("locations.yml");
		if (locations.getString("started") != null) {

		} else {
			locations.setHeader(header);
			locations.set("started", "start");
		}
		locations.set("DO NOT", "EDIT");
		locations.saveConfig();

			
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new TeamSelectGUI(), this);
		Bukkit.getPluginManager().registerEvents(new Interact(), this);
		Bukkit.getPluginManager().registerEvents(new ClickOnTeam(), this);
		Bukkit.getPluginManager().registerEvents(new InteractArenaOutBorders(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventPou(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventSpawn(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventShop(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventWaypoint(), this);
		Bukkit.getPluginManager().registerEvents(new VoteGUI(), this);
		Bukkit.getPluginManager().registerEvents(new EntityTarget(), this);
		Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDeath(), this);
		Bukkit.getPluginManager().registerEvents(new HeldItemChange(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileHit(), this);
		Bukkit.getPluginManager().registerEvents(new EntityCombust(), this);
		Bukkit.getPluginManager().registerEvents(new Teleport(), this);
		Bukkit.getPluginManager().registerEvents(new SpectatorInv(), this);
		Bukkit.getPluginManager().registerEvents(new Disconnect(), this);
		Bukkit.getPluginManager().registerEvents(new State(), this);
		Bukkit.getPluginManager().registerEvents(new RandomSpectateToggle(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawn(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
		
		APIManager.initAPI(GlowAPI.class);
		getCommand("pa").setExecutor(new ArenaCreate());

		 Manager = new SpectateManager(this);
		 boolean convertcantspectate = false;
		 boolean convertdisable = false;
		 
		getAPI().startSpectateTask();
		 
		Bukkit.getConsoleSender().sendMessage("§a" + plugin.getDescription().getName() + " §7version: §a"
				+ plugin.getDescription().getVersion() + " §7by §aMavecrit");

		if ((Main.getPlugin().getConfig().getInt("General.TeamSize") * 4) == Main.getPlugin().getConfig()
				.getInt("General.ServerSize")) {
		} else {
			Bukkit.getConsoleSender().sendMessage("§cFATAL ERROR OCCURED TO " + plugin.getDescription().getName());
			Bukkit.getConsoleSender().sendMessage("§cCAUSED BY: §7(Team size * 4) is not equal to Server size");
			Bukkit.getConsoleSender().sendMessage("§aSUGGESTED FIX: §7Set Team size to "
					+ (Main.getPlugin().getConfig().getInt("General.ServerSize") / 4));
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
			onDisable();
		}
		LocationGetter.LoadAll();
		for (int i = 1; i <= LocationGetter.Arenas.size(); i++) {
			if (Main.locations.getBoolean("arena_" + i + ".Enabled")) {
				VoteGUI.possibleIntegers.add(i);
			}
		}
		if(Main.getPlugin().getConfig().getInt("General.ServerSize") != Bukkit.getServer().getMaxPlayers()){
			Bukkit.getConsoleSender().sendMessage("§cFATAL ERROR OCCURED TO " + plugin.getDescription().getName());
			Bukkit.getConsoleSender()
					.sendMessage("§cCAUSED BY: §7Config section ServerSize is setted to " + Main.getPlugin().getConfig().getInt("General.ServerSize")
							+ " but server has max slots of " + Bukkit.getServer().getMaxPlayers());
			Bukkit.getConsoleSender()
					.sendMessage("§aSUGGESTED FIX: §7Set ServerSize to " + Bukkit.getServer().getMaxPlayers());
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
			onDisable();
		}
		

		if(Main.getPlugin().getConfig().getBoolean("General.Voting")){
		if (Main.getPlugin().getConfig().getInt("General.VoteableMaps") < VoteGUI.possibleIntegers.size()) {

		} else if (Main.getPlugin().getConfig().getInt("General.VoteableMaps") == VoteGUI.possibleIntegers.size()) {
			Bukkit.getConsoleSender()
					.sendMessage("§6SEMI-FATAL ERROR HAS OCCURED TO " + plugin.getDescription().getName());
			Bukkit.getConsoleSender()
					.sendMessage("§6CAUSED BY: §7" + Main.getPlugin().getConfig().getInt("General.VoteableMaps")
							+ " voteable maps and " + VoteGUI.possibleIntegers.size() + " Maps were found");
			Bukkit.getConsoleSender().sendMessage("§aSUGGESTED FIX: §7Create one more map ");
		} else {
			Bukkit.getConsoleSender().sendMessage("§cFATAL ERROR OCCURED TO " + plugin.getDescription().getName());
			Bukkit.getConsoleSender()
					.sendMessage("§cCAUSED BY: §7" + Main.getPlugin().getConfig().getInt("General.VoteableMaps")
							+ " Maps can get voted but only " + VoteGUI.possibleIntegers.size() + " Maps were found");
			Bukkit.getConsoleSender()
					.sendMessage("§aSUGGESTED FIX: §7Set votable maps to " + VoteGUI.possibleIntegers.size());
			Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
			onDisable();
		}
		Bukkit.getConsoleSender()
				.sendMessage("§aChoosing " + plugin.getConfig().getInt("General.VoteableMaps") + " random maps...");
		VoteGUI.Maps = ChooseRandomMaps.chooseMaps();

		Bukkit.getConsoleSender().sendMessage("§aChoosed Maps:");
		for (int i = 0; i < Main.getPlugin().getConfig().getInt("General.VoteableMaps"); i++) {
			Bukkit.getConsoleSender().sendMessage("§e" + locations.getString("arena_" + VoteGUI.Maps.get(i) + ".name"));
		   }
		} else {
			Bukkit.getConsoleSender().sendMessage("§cVoting disabled. §aChoosing random map..");
			GameInstance.mapID = ChooseRandomMaps.getRandom(1, VoteGUI.possibleIntegers.size());
			Bukkit.getConsoleSender().sendMessage("§aChoosed Map: " + GameInstance.mapID + " '" + locations.getString("arena_" + GameInstance.mapID + ".name") + "'");
		}
		
		String s = Main.getPlugin().getConfig().getString("General.BreakableBlocks");
		String[] blocks = s.split(",");
		
		for(int i = 0; i < blocks.length; i++){
			BlockBreak.breakable.add(Material.valueOf(blocks[i]));
		}
		Bukkit.getConsoleSender().sendMessage("§aPAWars found " + blocks.length + " breakable blocks");
		
		EntityTarget.setTarget();

	}

	public void onDisable() {
		System.out.print("PA Wars going to sleep");
		
	}

	public static Main getPlugin(){
		return plugin;
	}
	
	public static SpectateManager getAPI() {
	    return Manager;
	  }

    public boolean multiverseInvEnabled() {
	    return (getServer().getPluginManager().getPlugin("Multiverse-Inventories") != null) && (getServer().getPluginManager().getPlugin("Multiverse-Inventories").isEnabled());
    }
	
}
