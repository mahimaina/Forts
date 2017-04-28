package de.mavecrit.pawars.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.teams.TeamList;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;

public class Utils_unsorted {
	
	public static List<Block> getNearbyBlocks(Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}
	
	public static void clearGoals(EntityZombie cz){
	       Set  goalB = (Set )((CustomZombie) cz).getPrivateField("b", PathfinderGoalSelector.class, cz.goalSelector); goalB.clear();
	       Set  goalC = (Set )((CustomZombie) cz).getPrivateField("c", PathfinderGoalSelector.class, cz.goalSelector); goalC.clear();
	       Set  targetB = (Set )((CustomZombie) cz).getPrivateField("b", PathfinderGoalSelector.class, cz.targetSelector); targetB.clear();
	       Set  targetC = (Set )((CustomZombie) cz).getPrivateField("c", PathfinderGoalSelector.class, cz.targetSelector); targetC.clear();
		
	       cz.goalSelector.a(0, new PathfinderGoalFloat(cz));
	       cz.goalSelector.a(8, new PathfinderGoalLookAtPlayer(cz, EntityHuman.class, 8.0F));
	       cz.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(cz, 0.2D));
	       cz.goalSelector.a(4, new PathfinderGoalMeleeAttack(cz, 1.0, true)); //This adds melee attack to the mob
	       cz.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(cz, 0.2D, false));
	       cz.goalSelector.a(7, new PathfinderGoalRandomStroll(cz, 0.2D));
	       cz.goalSelector.a(8, new PathfinderGoalRandomLookaround(cz)); 
	}
	
	public static void mixTeams(){
		if(TeamList.blue.isEmpty()){
			if(TeamList.green.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.green.get(1));
				TeamList.blue.add(TeamList.green.get(1));
				TeamList.green.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Blue")));
			} else if(TeamList.yellow.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.yellow.get(1));
				TeamList.blue.add(TeamList.yellow.get(1));
				TeamList.yellow.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Blue")));
			}else if(TeamList.red.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.red.get(1));
				TeamList.blue.add(TeamList.red.get(1));
				TeamList.red.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Blue")));
			}
		}
		if(TeamList.red.isEmpty()){
			if(TeamList.green.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.green.get(1));
				TeamList.red.add(TeamList.green.get(1));
				TeamList.green.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Red")));
			} else if(TeamList.yellow.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.yellow.get(1));
				TeamList.red.add(TeamList.yellow.get(1));
				TeamList.yellow.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Red")));
			}else if(TeamList.blue.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.blue.get(1));
				TeamList.red.add(TeamList.blue.get(1));
				TeamList.blue.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Red")));
			}
		}
		
		if(TeamList.green.isEmpty()){
			if(TeamList.red.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.red.get(1));
				TeamList.green.add(TeamList.green.get(1));
				TeamList.red.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Green")));
			} else if(TeamList.yellow.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.yellow.get(1));
				TeamList.green.add(TeamList.yellow.get(1));
				TeamList.yellow.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Green")));
			}else if(TeamList.blue.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.blue.get(1));
				TeamList.green.add(TeamList.blue.get(1));
				TeamList.blue.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Green")));
			}
		}
		
		if(TeamList.yellow.isEmpty()){
			if(TeamList.red.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.red.get(1));
				TeamList.yellow.add(TeamList.green.get(1));
				TeamList.red.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Yellow")));
			} else if(TeamList.green.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.green.get(1));
				TeamList.yellow.add(TeamList.green.get(1));
				TeamList.green.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Yellow")));
			}else if(TeamList.blue.size() > 1){
				Player p = Bukkit.getPlayer(TeamList.blue.get(1));
				TeamList.yellow.add(TeamList.blue.get(1));
				TeamList.blue.remove(p.getName());
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.TeamMix").replace("{team}", "Yellow")));
			}
		}
	}

	public static String getTeamColor(Player p) {
		if(TeamList.blue.contains(p.getName())){
		return "§9";
		} else if(TeamList.green.contains(p.getName())){
			return "§2";
		} else if(TeamList.red.contains(p.getName())){
			return "§4";
		} else if(TeamList.yellow.contains(p.getName())){
			return "§6";
		}
		return "§f";
	}
	
	  public static Entity[]  getNearbyEntities(Location l, int radius){
	        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
	        HashSet<Entity> radiusEntities = new HashSet<Entity>();
	            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
	                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
	                    int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
	                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
	                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
	                    }
	                }
	            }
	        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	    }
}
