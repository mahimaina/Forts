package de.mavecrit.pawars.events;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.entitys.nms.PathfinderGoalWalkToLoc;
import de.mavecrit.pawars.entitys.nms.RandomPathfinding;
import de.mavecrit.pawars.guis.TeamSelectGUI;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.item.Empty;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.item.Spectate;
import de.mavecrit.pawars.item.TeamSelect;
import de.mavecrit.pawars.item.Vote;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.lists.Cooldown;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.spectator.SpectatorInv;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.teams.ZombieEnum;
import de.mavecrit.pawars.util.GlowAPI;
import de.mavecrit.pawars.util.ParticleEffect;
import de.mavecrit.pawars.util.Schedular;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.MobEffect;
import net.minecraft.server.v1_11_R1.MobEffectList;
import net.minecraft.server.v1_11_R1.NavigationAbstract;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_11_R1.PathEntity;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.World;

public class Interact implements Listener {
	public static List<Player> c = new ArrayList<>();
	public static List<Player> c1 = new ArrayList<>();
	public static List<CustomZombie> cooldown = new ArrayList<>();
	public int countdown = Main.getPlugin().getConfig().getInt("General.ItemCooldown");
	
	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
	
	public static HashMap<String, List<CustomZombie>> temporary = new HashMap<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			for(int i = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + i + ".EggColor"); i++){
				if (p.getItemInHand().equals(SpawnZombies.Zombie(i))) {
					Egg egg = p.getWorld().spawn(p.getLocation().add(0, 1, 0), Egg.class);
					egg.setVelocity(p.getEyeLocation().getDirection().multiply(1.5));
					egg.setShooter(p);
					e.setCancelled(true);
				   }
			}
			
			if(p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Items.Leave.Displayname")))){
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
		 		out.writeUTF("Connect");
		 		out.writeUTF("lobby_1");	
		 		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
			}

			if (p.getItemInHand().equals(TeamSelect.selector())) {
				e.setCancelled(true);
				if (Main.getPlugin().getConfig().getBoolean("GUI.TeamSelector.Reopenable")) {
					TeamSelectGUI.openGui(p);
				} else {
					if (TeamList.blue.contains(p.getName()) || TeamList.green.contains(p.getName())
							|| TeamList.red.contains(p.getName()) || TeamList.yellow.contains(p.getName())) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Main.getPlugin().getConfig().getString("Messages.Error.Reopen")));
					} else {
						TeamSelectGUI.openGui(p);
					}
				}
			}
			
			if (p.getItemInHand().equals(Vote.voter())) {
				e.setCancelled(true);
				if (Main.getPlugin().getConfig().getBoolean("GUI.Vote.Reopenable")) {
					VoteGUI.openGui(p);
				} else {
					if (VoteGUI.hasVoted.get(p.getName()) != null) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&',
								Main.getPlugin().getConfig().getString("Messages.Error.Reopen")));
					} else {
						VoteGUI.openGui(p);
					}
				}
			}
			
			if (p.getItemInHand().equals(Spectate.spec())) {
				e.setCancelled(true);
				if (Alive.Alive.contains(p)){
					System.out.println("ERROR: Alive contains " + p.getName());
				} else if(Alive.Spectator.contains(p) && !Alive.Alive.contains(p)){
					SpectatorInv.openInv(p);
				}
			}
			
			if(temporary.get(p.getName()) != null){
				for(CustomZombie cz : temporary.get(p.getName())){
				GlowAPI.setGlowing(cz.getBukkitEntity(), false, p);
				}
				c.remove(p);
			}
		} else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			int slot = p.getInventory().getHeldItemSlot();
			Block b = p.getTargetBlock((HashSet<Byte>) null, 100);
			Location target = b.getLocation();
			
			if(Zombies.Units.get(p.getName() + ".Attack." + slot) != null){
				 if(target.distance(Zombies.Units.get(p.getName() + ".Attack." + slot).get(0).getBukkitEntity().getLocation()) <= 20){
				 for(CustomZombie cz : Zombies.Units.get(p.getName() + ".Attack." + slot)){	 
					 if(!cooldown.contains(cz))
					 cooldown.add(cz);
					 if(c1.contains(p)){
						 c1.remove(p);}
					  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
					        @Override
					        public void run() {
								 		cz.setGoalTarget(null);
										cz.goalSelector.a(3, new PathfinderGoalWalkToLoc(cz, 0.28d, target.add(getRandom(-0,1), 0, getRandom(-1,1)).getX(), 
												target.getY(),  target.add(getRandom(-0,1), 0, getRandom(-1,1)).getZ()));		
								 
					           }
					        }, 5L);
					  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
					        @Override
					        public void run() {
								 		cooldown.remove(cz);
								 
					           }
					        }, 20 *2L);
					    EnderPearl egg = p.getWorld().spawn(p.getEyeLocation().add(0, 0.4, 0), EnderPearl.class); 
					    egg.setShooter(p);
						egg.setVelocity(p.getEyeLocation().getDirection().multiply(3));
						c1.add(p);
						p.playSound(p.getLocation(), Sound.ENTITY_ENDERPEARL_THROW, 1f, 1f);
						ParticleEffect.PORTAL.display(0f, 0f, 0f, 0.2f, 10, p.getLocation(), p);
						
			     }
			 } else {
				 p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.TooFar"))); 
			 }
		  }
			
			if(Zombies.Units.get(p.getName() + ".Healer." + slot) != null){
				 if(target.distance(Zombies.Units.get(p.getName() + ".Healer." + slot).get(0).getBukkitEntity().getLocation()) <= 20){
				 for(CustomZombie cz : Zombies.Units.get(p.getName() + ".Healer." + slot)){	 
					 if(c1.contains(p)){
						 c1.remove(p);}
					 if(!cooldown.contains(cz))
					 cooldown.add(cz);
					  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
					        @Override
					        public void run() {
								 		cz.setGoalTarget(null);
										cz.goalSelector.a(3, new PathfinderGoalWalkToLoc(cz, 0.28d, target.add(getRandom(-0,1), 0, getRandom(-1,1)).getX(), 
												target.getY(),  target.add(getRandom(-0,1), 0, getRandom(-1,1)).getZ()));		
								 
					           }
					        }, 5L);
					  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {  		    
					        @Override
					        public void run() {
								 		cooldown.remove(cz);
								 
					           }
					        }, 20 *2L);
					    EnderPearl egg = p.getWorld().spawn(p.getEyeLocation().add(0, 0.4, 0), EnderPearl.class); 
					    egg.setShooter(p);
						egg.setVelocity(p.getEyeLocation().getDirection().multiply(3));
						c1.add(p);
						p.playSound(p.getLocation(), Sound.ENTITY_ENDERPEARL_THROW, 1f, 1f);
						ParticleEffect.PORTAL.display(0f, 0f, 0f, 0.2f, 10, p.getLocation(), p);
						
			     }
			 } else {
				 p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.TooFar"))); 
			 }
		  }
			
			
			if(c.contains(p)){
				if(temporary.get(p.getName()).get(0).getBukkitEntity().getLocation().distance(p.getLocation()) > 20){
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.Error.TooFar")));
				} else {
				for(CustomZombie cz : temporary.get(p.getName())){			
					cz.setGoalTarget(null);
					cz.goalSelector.a(3, new PathfinderGoalWalkToLoc(cz, 0.28d, e.getClickedBlock().getLocation().add(getRandom(-0,1), 0, getRandom(-1,1)).getX(), 
							e.getClickedBlock().getLocation().getY(),  e.getClickedBlock().getLocation().add(getRandom(-0,1), 0, getRandom(-1,1)).getZ()));		
					GlowAPI.setGlowing(cz.getBukkitEntity(), false, p);
					c.remove(p);
				   }
				}			
			}
		}
		
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			Block b = e.getClickedBlock();
			Location l = b.getLocation();
			if(BlockTask.blocklive.get(l) != null){		
				String s = Utils_unsorted.translateHearts(l);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.RestLife").replace("{hearts}", s)));
			}
		}
	}

	
	
	@EventHandler
	public void onClick2(PlayerInteractAtEntityEvent e){
		Entity en = e.getRightClicked();
		Player p =  e.getPlayer();
		if(!c.contains(p)){
		if(en instanceof Zombie){
			Zombie z = (Zombie) en;
			EntityZombie cz = ((CraftZombie) z).getHandle();
			if(cz instanceof CustomZombie){
				e.setCancelled(true);
			   }
		     }
		  }
		
	}
}
