package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.entitys.nms.PathfinderGoalWalkToLoc;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.teams.ZombieEnum;
import de.mavecrit.pawars.util.ParticleEffect;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_11_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_11_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_11_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;

public class EntityTarget implements Listener {

	@EventHandler
	public void onAttack(EntityTargetEvent e) {
		Entity en = e.getEntity();
		Entity target = e.getTarget();
		if (en instanceof Zombie) {
			if (target instanceof Player) {
				Zombie z = (Zombie) en;
	     		EntityZombie cz = ((CraftZombie) z).getHandle();
				if (cz instanceof CustomZombie) {
					e.setCancelled(true);
				}
			}
		}
	}

	public static void setTarget() {
		BlockTask.damageBlocks();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for (World w : Bukkit.getWorlds()) {
					for (Entity en : w.getEntities()) {
						if (en instanceof Zombie) {
							EntityZombie cz = ((CraftZombie) en).getHandle();
							if (cz instanceof CustomZombie) {
								if(!Interact.cooldown.contains(cz)){
								List<CustomZombie> list = new ArrayList<>();
								list.add((CustomZombie) cz);		
								if (cz.getCustomName().contains(Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))) {	
									
									if(((Zombie) en).getTarget() == null){				 
										for(int i = 0; i < list.size(); i++){
											if(Zombies.location.get(cz) != null){											
												Location key = Zombies.location.get(cz);
												Block b = BlockTask.blocks.get(getRandom(0, (BlockTask.blocks.size() -1 )));
										    	Location loc = b.getLocation();							
											    for(CustomZombie walk : Zombies.zombies.get(key)){    
											    	if(BlockTask.blocklive.get(loc) != null){
													Utils_unsorted.clearGoals(cz);
													ArmorStand standi = loc.getWorld().spawn(loc, ArmorStand.class);
													standi.setAI(false);
													standi.setInvulnerable(true);
													standi.setCanPickupItems(false);
													standi.setCollidable(false);
											        standi.setVisible(false);
											        standi.setGravity(false);		 
											    	((Zombie) en).setTarget(standi);			
											        walk.goalSelector.a(3, new PathfinderGoalWalkToLoc(walk, 0.28d, loc.getX(), loc.getY(), loc.getZ()));
											    	} else {
											    		((Zombie) en).setTarget(null);	
											    	}
											    }
											}
										}	
								
									} else if(((Zombie) en).getTarget() instanceof CraftArmorStand) {
										
										for (Entity near : en.getNearbyEntities(10, 10, 10)) {
											if (near instanceof Zombie) {
												EntityZombie cz2 = ((CraftZombie) near).getHandle();
												if(cz2 instanceof CustomZombie){
													if(((Zombie) en).getTarget() instanceof CraftArmorStand){
														ArmorStand as = (ArmorStand) ((Zombie) en).getTarget();
														as.remove();
													}
													ZombieEnum cz_team = Zombies.teams.get(cz);
													ZombieEnum cz2_team = Zombies.teams.get(cz2);
												    if (!cz_team.equals(cz2_team)) {
													  ((Zombie) en).setTarget(null);
													  if(((Zombie) en).getTarget() == null){
													  Utils_unsorted.clearGoals(cz);
													  ((Zombie) en).setTarget((LivingEntity) near);
													
													  } else {
														  if(((Zombie) en).getTarget().getLocation().distance(en.getLocation()) > Main.getPlugin().getConfig().getInt("Mobs.Attack.Zombies.FollowRange")){		
																((Zombie) en).setTarget(null);						  
														  }
													   }
												    }
												}
											}
										}
										
									} else if(((Zombie) en).getTarget() instanceof CraftZombie || ((Zombie) en).getTarget() instanceof CraftArmorStand){
										
										for (Entity near : en.getNearbyEntities(10, 10, 10)) {
											if (near instanceof Player) {
												Player p = (Player) near;
												String teamColor = Utils_unsorted.getTeamColor(p);
												if (!cz.getCustomName()	.equalsIgnoreCase(teamColor + Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))) {
													  if(((Zombie) en).getTarget() == null){
														  if(((Zombie) en).getTarget() instanceof CraftArmorStand){
																ArmorStand as = (ArmorStand) ((Zombie) en).getTarget();as.remove();}
														  
													  ((Zombie) en).setTarget(null);
													  Utils_unsorted.clearGoals(cz);
													  ((Zombie) en).setTarget(p);
													/*  walk.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED)
														.setValue((Main.getPlugin().getConfig().getDouble("Mobs.Attack.Zombies.Speed") * 2));
												        walk.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE)
														.setValue(Main.getPlugin().getConfig().getDouble("Mobs.Attack.Zombies.Damage"));    */
													  } else {
														  if(((Zombie) en).getTarget().getLocation().distance(en.getLocation()) > Main.getPlugin().getConfig().getInt("Mobs.Zombies.Attack.FollowRange")){		
																((Zombie) en).setTarget(null);						  
													    }
												    }
												}
											}
										}
									}
								} else if (cz.getCustomName().contains(Main.getPlugin().getConfig().getString("Mobs.Zombies.Healer.Displayname"))) {	
									if(((Zombie) en).getTarget() == null){				 								
												for(Entity near : en.getNearbyEntities(10, 10, 10)){
													if(near instanceof Zombie){	
														EntityZombie cz2 = ((CraftZombie) near).getHandle();
														if(cz2 instanceof CustomZombie){
															ZombieEnum cz_team = Zombies.teams.get(cz);
															ZombieEnum cz2_team = Zombies.teams.get(cz2);
															
															if (cz_team.equals(cz2_team)) {
																		Utils_unsorted.clearGoals(cz);
																    	((Zombie) en).setTarget((LivingEntity) near);
																    	cz.goalSelector.a(3, new PathfinderGoalWalkToLoc(cz, 0.28d, near.getLocation().add(getRandom(-0,1), 0, getRandom(-1,1)).getX(), 
																    			near.getLocation().getY(),  near.getLocation().add(getRandom(-0,1), 0, getRandom(-1,1)).getZ()));		
																    
															  }
														  }
													 }
												}
											
										
									} else if(((Zombie) en).getTarget() instanceof Zombie || ((Zombie) en).getTarget() instanceof CraftZombie){
										 if(((Zombie) en).getTarget().getLocation().distance(en.getLocation()) > Main.getPlugin().getConfig().getInt("Mobs.Zombies.Healer.FollowRange")){		
												((Zombie) en).setTarget(null);						  
									     } else {	 	
									    	 for(Entity near : en.getNearbyEntities(2, 2, 2)){
									    		 if(near instanceof Zombie || near instanceof CraftZombie){
									    			 EntityZombie cz2 = ((CraftZombie) near).getHandle();
									    			 if(cz2 instanceof CustomZombie){
									    				 if(cz2.getCustomName().contains(Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))){
									    			 float health = cz2.getHealth();
									    			 cz2.setHealth((health + 5));
									    			 ParticleEffect.SPELL_INSTANT.display(0.1F, 0.1F, 0.1F, 0.1F, 10,
																en.getLocation().add(getRandom(-1,1), 0, getRandom(-1,1)), 20);
											    	 ThrownPotion thrownPotion = ((CraftLivingEntity) en).launchProjectile(ThrownPotion.class);
											    	 thrownPotion.setVelocity(en.getLocation().getDirection().multiply(0.9));
											    	 ((Zombie) en).setTarget(null);	
											    	 for(Entity near2 : en.getNearbyEntities(10, 10, 10)){
											    		 if(near2 instanceof Player){
											    			 Player p_near = (Player) near2;
											    			 p_near.playSound(en.getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 1f, 1f);
											    		   }
											    	    }
									    			 }
									    		   }
									    		 }
									    	 }
									     }
									}
								  }   
								}
							}
						}
					}
				}
			}
		}, 0, 20);
	}
	

	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
	
	
}
