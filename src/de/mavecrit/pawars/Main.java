package de.mavecrit.pawars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import de.mavecrit.pawars.commands.ArenaCreate;
import de.mavecrit.pawars.commands.WalkHere;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.entitys.nms.CustomEntityRegistry;
import de.mavecrit.pawars.events.ClickOnTeam;
import de.mavecrit.pawars.events.EntityTarget;
import de.mavecrit.pawars.events.Interact;

import de.mavecrit.pawars.events.PlayerJoin;
import de.mavecrit.pawars.events.mapcreator.InteractEventPou;
import de.mavecrit.pawars.events.mapcreator.InteractEventShop;
import de.mavecrit.pawars.events.mapcreator.InteractEventSpawn;
import de.mavecrit.pawars.events.mapcreator.InteractEventWaypoint;
import de.mavecrit.pawars.game.GameInstance;
import de.mavecrit.pawars.guis.TeamSelectGUI;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.maps.ChooseRandomMaps;
import de.mavecrit.pawars.util.SimpleConfig;
import de.mavecrit.pawars.util.SimpleConfigManager;
import net.minecraft.server.v1_11_R1.Entity;

public class Main extends JavaPlugin implements Listener {
	public static SimpleConfigManager manager;
	public static SimpleConfig locations;
	public static Main plugin;
	public static List<Player> seeGlow = new ArrayList<>();
	
	public void loadConfiguration() {
		plugin.getConfig().addDefault("General.ServerSize", 16);
		plugin.getConfig().addDefault("General.TeamSize", 4);
		plugin.getConfig().addDefault("General.MinPlayers", 4);
		plugin.getConfig().addDefault("General.Voting", true);
		plugin.getConfig().addDefault("General.VoteableMaps", 3);
		plugin.getConfig().addDefault("General.PouBlock", "PRISMARINE");
		plugin.getConfig().addDefault("General.WallBlock", "SANDSTONE"); 
		
		plugin.getConfig().addDefault("General.GUI.Placeholder.Material", "STAINED_GLASS_PANE");
		plugin.getConfig().addDefault("General.GUI.Placeholder.Amount", 1);
		plugin.getConfig().addDefault("General.GUI.Placeholder.Byte", 15);
		plugin.getConfig().addDefault("General.GUI.Placeholder.Displayname", "{empty}");
		plugin.getConfig().addDefault("General.GUI.Placeholder.Lore", "{empty}");
		plugin.getConfig().addDefault("General.GUI.Placeholder.Glow", false);

		plugin.getConfig().addDefault("Messages.Error.Reopen", "&cYou cannot open this item twice");
		plugin.getConfig().addDefault("Messages.Error.NotEnoughPlayers", "&cNot enough players");
		plugin.getConfig().addDefault("Messages.Error.Permission", "&cI dont think you're supposed to do that.");
		plugin.getConfig().addDefault("Messages.JoinedTeam", "&aYou've joined team: {team}");
		plugin.getConfig().addDefault("Messages.LeftTeam", "&cYou left your current team");
		plugin.getConfig().addDefault("Messages.TeamMix", "&cWe changed your team (for fairness reasons) to: {team}");
		
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
		
		plugin.getConfig().addDefault("Items.Zombies.1.EggColor", "CREEPER");
		plugin.getConfig().addDefault("Items.Zombies.1.Glow", false);
		plugin.getConfig().addDefault("Items.Zombies.1.Displayname", "Attacker");
		plugin.getConfig().addDefault("Items.Zombies.1.Lore",
				"Left: {amount};&8Rightclick to use");
		
		plugin.getConfig().addDefault("Items.Zombies.2.EggColor", "PIG");
		plugin.getConfig().addDefault("Items.Zombies.2.Glow", false);
		plugin.getConfig().addDefault("Items.Zombies.2.Displayname", "Healer");
		plugin.getConfig().addDefault("Items.Zombies.2.Lore",
				"Left: {amount};&8Rightclick to use");
		
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.Displayname", "Zombie");
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.Speed",0.28D);
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.Damage", 3);
		plugin.getConfig().addDefault("Mobs.Zombies.Attack.FollowRange", 20);
		
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.Displayname", "Zombie");
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.Speed",0.28D);
		plugin.getConfig().addDefault("Mobs.Zombies.Healer.Potion", "INSTANT_DAMAGE");
				
		plugin.getConfig().addDefault("GUI.Vote.Size", 9);
		plugin.getConfig().addDefault("GUI.Vote.Name", "&5Vote");
		plugin.getConfig().addDefault("GUI.Vote.Placeholders", false);
		plugin.getConfig().addDefault("GUI.Vote.Reopenable", false);
		plugin.getConfig().addDefault("GUI.Vote.Items.Material", "PAPER");
		plugin.getConfig().addDefault("GUI.Vote.Items.Lore", "&6Current votes: &7{votes}");

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

	public void onEnable() {
		plugin = this;
		CustomEntityRegistry.registerCustomEntity(54, "zombie", CustomZombie.class);

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
		Bukkit.getPluginManager().registerEvents(new InteractEventPou(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventSpawn(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventShop(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEventWaypoint(), this);
		Bukkit.getPluginManager().registerEvents(new VoteGUI(), this);
		Bukkit.getPluginManager().registerEvents(new EntityTarget(), this);

		getCommand("pa").setExecutor(new ArenaCreate());
		getCommand("walkhere").setExecutor(new WalkHere());

		ProtocolLibrary.getProtocolManager();

	    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
	        @Override
	        public void onPacketSending(PacketEvent event) {
	     
	         if (seeGlow.contains(event.getPlayer()))
	         {
	           for (Player player : Bukkit.getOnlinePlayers())
	      {
	           if (/*has a player with*/player.getEntityId() == event.getPacket().getIntegers().read(0)) {
	        if (event.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
	        List<WrappedWatchableObject> watchableObjectList = event.getPacket().getWatchableCollectionModifier().read(0);
	        for (WrappedWatchableObject metadata : watchableObjectList)
	        {
	           if (metadata.getIndex() == 0) {
	          byte b = (byte) metadata.getValue();
	          b |= 0b01000000;
	          metadata.setValue(b);
	          }
	        }
	        } else {
	           WrappedDataWatcher watcher = event.getPacket().getDataWatcherModifier().read(0);
	           if (watcher.hasIndex(0)) {
	        byte b = watcher.getByte(0);
	        b |= 0b01000000;
	        watcher.setObject(0, b);
	           }
	          }
	         }
	        }
	       }
	      }
	    });

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
			GameInstance.mapID = ChooseRandomMaps.getRandom(0, VoteGUI.possibleIntegers.size());
			Bukkit.getConsoleSender().sendMessage("§aChoosed Map: " + GameInstance.mapID + " '" + locations.getString("arena_" + GameInstance.mapID + ".name") + "'");
		}
		
		EntityTarget.setTarget();

	}

	public void onDisable() {
		System.out.print("PA Wars going to sleep");
	}

	public static Main getPlugin(){
		return plugin;
	}
	
	private Class<?> getNmsClass(String className) throws ClassNotFoundException {

        return Class.forName("net.minecraft.server." + getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + className);
    }

    private Class<?> getCraftbukkitClass(String className) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + className);
    }
	
}
