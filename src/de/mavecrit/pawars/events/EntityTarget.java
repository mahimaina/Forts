package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.entitys.nms.PathfinderGoalWalkToLoc;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityZombie;
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
				Player p = (Player) target;
				Location targetLocation = null;
				targetLocation = z.getLocation();

				final Location test = targetLocation;
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
								if (cz.getCustomName().contains(Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))) {	
									if(((Zombie) en).getTarget() == null){
										for(int i = 1; i < Zombies.zombies.size(); i++){
											if(Zombies.zombies.get(i).contains(cz)){
												int size = BlockTask.blocks.size();
										    	Location loc = BlockTask.blocks.get(getRandom(0, size)).getLocation();
												List<CustomZombie> zombies = Zombies.zombies.get(i);
											    for(CustomZombie walk : zombies){
													ArmorStand standi = loc.getWorld().spawn(loc, ArmorStand.class);
													standi.setAI(false);
													standi.setInvulnerable(true);
													standi.setCanPickupItems(false);
													standi.setCollidable(false);
											        standi.setVisible(false);
											        standi.setGravity(false);		        
											        ((Zombie) walk).setTarget(standi);
											        walk.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED)
													.setValue((Main.getPlugin().getConfig().getDouble("Mobs.Attack.Zombies.Speed") * 2));
											        walk.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE)
													.setValue(Main.getPlugin().getConfig().getDouble("Mobs.Attack.Zombies.Damage"));      
											    
											    }
											}
										}
										
									} else if(((Zombie) en).getTarget() instanceof ArmorStand) {
										for (Entity near : en.getNearbyEntities(10, 10, 10)) {
											if (near instanceof Zombie) {
												EntityZombie cz2 = ((CraftZombie) near).getHandle();
												if(cz2 instanceof CustomZombie){
												    if (!cz.getCustomName().equals(cz2.getCustomName())) {
													  ((Zombie) en).setTarget(null);
													  if(((Zombie) en).getTarget() == null){
													  Utils_unsorted.clearGoals(cz);
													  ((Zombie) en).setTarget((LivingEntity) near);
													  cz.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED)
														.setValue((Main.getPlugin().getConfig().getDouble("Mobs.Attack.Zombies.Speed") * 2));
												      cz.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE)
														.setValue(Main.getPlugin().getConfig().getDouble("Mobs.Attack.Zombies.Damage"));
													  } else {
														  if(((Zombie) en).getTarget().getLocation().distance(en.getLocation()) > Main.getPlugin().getConfig().getInt("Mobs.Attack.Zombies.FollowRange")){		
																((Zombie) en).setTarget(null);						  
														  }
													   }
												    }
												}
											}
										}
										
									} else if(((Zombie) en).getTarget() instanceof Zombie || ((Zombie) en).getTarget() instanceof ArmorStand){
										for (Entity near : en.getNearbyEntities(10, 10, 10)) {
											if (near instanceof Player) {
												Player p = (Player) near;
												String teamColor = Utils_unsorted.getTeamColor(p);
												if (!cz.getCustomName()	.equalsIgnoreCase(teamColor + Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"))) {
													  if(((Zombie) en).getTarget() == null){
													  ((Zombie) en).setTarget(null);
													  Utils_unsorted.clearGoals(cz);
													  ((Zombie) en).setTarget(p);
													  cz.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED)
														.setValue((Main.getPlugin().getConfig().getDouble("Mobs.Zombies.Attack.Speed") * 2));
												      cz.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE)
														.setValue(Main.getPlugin().getConfig().getDouble("Mobs.Zombies.Attack.Damage"));      
													  } else {
														  if(((Zombie) en).getTarget().getLocation().distance(en.getLocation()) > Main.getPlugin().getConfig().getInt("Mobs.Zombies.Attack.FollowRange")){		
																((Zombie) en).setTarget(null);						  
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
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		if(e.getEntity() instanceof Zombie){
			List<Entity> temporary = new ArrayList<>();
			for(Entity en : e.getEntity().getNearbyEntities(2, 2, 2)){		
				if(en instanceof Zombie)
				temporary.add(en);
			}
			Entity ent = temporary.get(0);
			Zombie z = (Zombie) e.getEntity();
			Zombie z2 = (Zombie) ent;
			if(z.getTarget() != null){
				z.setTarget(null);
			}
			if(ent instanceof Zombie){
			if(z2.getTarget() != null){
				z2.setTarget(null);
			    temporary.clear();
			   }
			}
		}
	}
	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
	
}
