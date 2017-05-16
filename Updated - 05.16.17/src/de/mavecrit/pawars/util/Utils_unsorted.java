package de.mavecrit.pawars.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.events.BlockTask;
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
	
	  @SuppressWarnings("rawtypes")
	public static void sendPaket(Player p, net.minecraft.server.v1_11_R1.Packet pa) {
	        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pa);
	    }
	
	public static List<Block> select(Location loc1, Location loc2, World w){
		 
        //First of all, we create the list:
        List<Block> blocks = new ArrayList<Block>();
 
        //Next we will name each coordinate
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();
 
        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();
 
        //Then we create the following integers
        int xMin, yMin, zMin;
        int xMax, yMax, zMax;
        int x, y, z;
 
        //Now we need to make sure xMin is always lower then xMax
        if(x1 > x2){ //If x1 is a higher number then x2
            xMin = x2;
            xMax = x1;
        }else{
            xMin = x1;
            xMax = x2;
        }
 
        //Same with Y
        if(y1 > y2){
            yMin = y2;
            yMax = y1;
        }else{
            yMin = y1;
            yMax = y2;
        }
 
        //And Z
        if(z1 > z2){
            zMin = z2;
            zMax = z1;
        }else{
            zMin = z1;
            zMax = z2;
        }
 
        //Now it's time for the loop
        for(x = xMin; x <= xMax; x ++){
            for(y = yMin; y <= yMax; y ++){
                for(z = zMin; z <= zMax; z ++){
                    Block b = new Location(w, x, y, z).getBlock();
                    blocks.add(b);
                }
            }
        }
 
        //And last but not least, we return with the list
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
	
	public static String getZombieTeam(EntityZombie cz){
		String name = cz.getCustomName().toString();
		String without_Setter= name.replace("§9", "").replace("&", "");
		String team_code = without_Setter.substring(0, 1);
		
		return team_code;
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
	
	  public static List<EntityType>  getNearbyEntities(Location l, int radius){
	        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
	        HashSet<Entity> radiusEntities = new HashSet<Entity>();
	        List<EntityType> list = new ArrayList<>();
	            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
	                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
	                    int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
	                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
	                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
	                        list.add(e.getType());
	                    }
	                }
	            }
	        return list;
	    }
	  
	  public static List<Player> getNearbyPlayers(Location l, int radius){
	        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
	        HashSet<Entity> radiusEntities = new HashSet<Entity>();
	        List<Player> list = new ArrayList<>();
	            for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
	                for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
	                    int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
	                    for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
	                        if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
	                        if(e instanceof Player){
	                        Player p = (Player) e;
	                        list.add(p);
	                        }
	                    }
	                }
	            }
	        return list;
	    }
	  
	  public static String translateHearts(Location l){
		int i = BlockTask.blocklive.get(l);
		if(i >= 90){
			return "§4❤❤❤❤❤❤❤❤❤❤";
		}
		if(i < 90 && i >= 80){
			return "§4❤❤❤❤❤❤❤❤❤§0❤";
		}
		if(i < 80 && i >= 70){
			return "§4❤❤❤❤❤❤❤❤§0❤❤";
		}    
		if(i < 70 && i >= 60){
			return "§4❤❤❤❤❤❤❤§0❤❤❤";
		}   
		if(i < 60 && i >= 50){
			return "§4❤❤❤❤❤❤§0❤❤❤❤";
		}  
		if(i < 50 && i >= 40){
			return "§4❤❤❤❤❤§0❤❤❤❤❤";
		}  
		if(i < 40 && i >= 30){
			return "§4❤❤❤❤§0❤❤❤❤❤❤";
		}  
		if(i < 30 && i >= 20){
			return "§4❤❤❤§0❤❤❤❤❤❤❤";
		}
		if(i < 20 && i >= 10){
			return "§4❤❤§0❤❤❤❤❤❤❤❤";
		}
		if(i < 10 && i >= 0){
			return "§4❤§0❤❤❤❤❤❤❤❤❤";
		}
		return null;
	  }
}
