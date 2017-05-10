package de.mavecrit.pawars.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.item.CreaterItems;
import de.mavecrit.pawars.item.mapcreator.SetSpawn;
import de.mavecrit.pawars.location.LocationGetter;
import de.mavecrit.pawars.util.SimpleConfig;


public class ArenaCreate implements CommandExecutor{
	

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
	    Location location = player.getLocation();
		if (cmd.getName().equalsIgnoreCase("pa")) {
			if(player.hasPermission("pa.admin")){
			if(args.length == 0){
				player.sendMessage("§7Valid commands:");
				player.sendMessage("§a/pa arena create <name>");
			
				player.sendMessage("§a/pa arena enable <name>");
				player.sendMessage("§a/pa arena disable <name>");
				player.sendMessage("§a/pa setlobbyspawn");
			} else if(args.length == 1){
				if(args[0].equals("setlobbyspawn")){
					Main.locations.set("lobby", location.getWorld().getName() + "," + location.getBlockX()
					+ "," + location.getBlockY() + "," + location.getBlockZ() + ","
					+ location.getYaw() + "," + location.getPitch());
					Main.locations.saveConfig();
					player.sendMessage("§aLobby spawn setted");
				} else {
				player.sendMessage("§7Valid commands:");
				player.sendMessage("§a/pa arena create <name>");
				
				player.sendMessage("§a/pa arena enable <name>");
				player.sendMessage("§a/pa arena disable <name>");
				}
			} else if(args.length == 2){
				if(args[0].equals("arena")&& args[1].equals("create")){
					player.sendMessage("§cYou have to specify a name");
					player.sendMessage("§a/pa arena create <name>");
				}
			
				if(args[0].equals("arena")&& args[1].equals("enable")){
					player.sendMessage("§cYou have to specify a name");
					player.sendMessage("§a/pa arena enable <name>");
				}
				if(args[0].equals("arena")&& args[1].equals("disable")){
					player.sendMessage("§cYou have to specify a name");
					player.sendMessage("§a/pa arena disable <name>");
				}
			} else if(args.length == 3){
				if(args[0].equals("arena")&& args[1].equals("create")){			
				
					if(!LocationGetter.names.contains(args[2])){
						
						LocationGetter.names.add(args[2]);
						LocationGetter.Arenas.add(1);
					
						if(Main.locations.getString("arenanames") != null){	
							Main.locations.set("arenanames", Main.locations.get("arenanames") + "," + args[2]);
						} else {
							Main.locations.set("arenanames", args[2]);
						}
						Main.locations.set("arena_" + LocationGetter.Arenas.size() + ".name", args[2]);
						Main.locations.saveConfig();
				
						player.sendMessage("§aYou've created Arena " + LocationGetter.Arenas.size() + "!§7 Name: §a" + args[2]);
						player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f);
						player.getInventory().clear();
						player.getInventory().addItem(SetSpawn.BlueSpawn());
						player.getInventory().addItem(SetSpawn.RedSpawn());
						player.getInventory().addItem(SetSpawn.GreenSpawn());
						player.getInventory().addItem(SetSpawn.YellowSpawn());
						player.getInventory().addItem(CreaterItems.Finish());
					} else {
						player.sendMessage("§cYou already have a arena with that name");
					   }
				}
					
		
				if(args[0].equals("arena")&& args[1].equals("enable")){
					
					  for(int i = 1; i <= LocationGetter.Arenas.size(); i++){
					    	 if(Main.locations.getString("arena_" + i + ".name").equals(args[2])){
					    		
					    		if(Main.locations.getBoolean("arena_" + i + ".Enabled")){
					    			player.sendMessage("§cThis arena is already enabled");
					    		} else {
					    			Main.locations.set("arena_"+i+".Enabled", true);
					    			Main.locations.saveConfig();
					    			player.sendMessage("§aThe arena is enabled now");
					    		}
					    	 }
					     }
				}
				if(args[0].equals("arena")&& args[1].equals("disable")){
					for(int i = 1; i <= LocationGetter.Arenas.size(); i++){
				    	 if(Main.locations.getString("arena_" + i + ".name").equals(args[2])){
				    		if(Main.locations.getBoolean("arena_" + i + ".Enabled")){
				    			Main.locations.set("arena_"+i+".Enabled", false);
				    			Main.locations.saveConfig();
				    			player.sendMessage("§cThe arena is disabled now");
				    			
				    		}
				    	 }
				     }
				}
			}
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.Permission")));
			}
		}
		return false;
	}
	
	public static void copyConfigSection(SimpleConfig locations, String fromPath, String toPath){
	    Map<String, Object> vals = locations.getConfigurationSection(fromPath).getValues(true);
	    String toDot = toPath.equals("") ? "" : ".";
	    for (String s : vals.keySet()){
	        System.out.println(s);
	        Object val = vals.get(s);
	        if (val instanceof List)
	            val = new ArrayList((List)val);
	        locations.set(toPath + toDot + s, val);
	    }
	}
}
