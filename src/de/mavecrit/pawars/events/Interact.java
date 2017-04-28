package de.mavecrit.pawars.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.projectiles.ProjectileSource;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.entitys.CustomZombie;
import de.mavecrit.pawars.entitys.nms.PathfinderGoalWalkToLoc;
import de.mavecrit.pawars.entitys.nms.RandomPathfinding;
import de.mavecrit.pawars.guis.TeamSelectGUI;
import de.mavecrit.pawars.item.SpawnZombies;
import de.mavecrit.pawars.item.TeamSelect;
import de.mavecrit.pawars.lists.Zombies;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.Utils_unsorted;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntityInsentient;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.MobEffect;
import net.minecraft.server.v1_11_R1.MobEffectList;
import net.minecraft.server.v1_11_R1.NavigationAbstract;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_11_R1.PathEntity;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_11_R1.World;

public class Interact implements Listener {

	public static int getRandom(int lower, int upper) {
		Random random = new Random();
		return random.nextInt((upper - lower) + 1) + lower;
	}
	
	@EventHandler
	public void onJoin(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			for(int i = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + i + ".EggColor"); i++){
				if (p.getItemInHand().equals(SpawnZombies.Zombie(i))) {

					Egg egg = p.getWorld().spawn(p.getLocation().add(0, 1, 0), Egg.class);
					egg.setVelocity(p.getEyeLocation().getDirection().multiply(1.5));
					egg.setShooter(p);
				}
			}

			if (p.getItemInHand().equals(TeamSelect.selector())) {
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

		}
	}

	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		ProjectileSource ps = e.getEntity().getShooter();
		if (ps instanceof Player) {
			Player p = (Player) ps;
			for(int num = 1; Main.getPlugin().getConfig().contains("Items.Zombies." + num + ".EggColor"); num++){
			if (p.getItemInHand().equals(SpawnZombies.Zombie(num))) {
				if(num == 1){
				Location loc = e.getEntity().getLocation();
				World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
				int currentsize = Zombies.zombies.size();
				List<CustomZombie> list = new ArrayList<>();
				for (int i = 0; i < 5; i++) {
					CustomZombie skele = new CustomZombie(nmsWorld);
					skele.setPosition(loc.add(i, 0, 0).getX(), loc.getY(), loc.getZ());
					skele.h(p.getLocation().getYaw());
					skele.i(p.getLocation().getYaw());		
					skele.setCustomName(Utils_unsorted.getTeamColor(p) + Main.getPlugin().getConfig().getString("Mobs.Zombies.Attack.Displayname"));
					skele.setCustomNameVisible(true);
					skele.setSilent(true);
					skele.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(skele, EntityHuman.class, 0, true, false, null));
					nmsWorld.addEntity(skele);			
					skele.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Main.getPlugin().getConfig().getDouble("Mobs.Zombies.Attack.Speed"));
					skele.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(Main.getPlugin().getConfig().getDouble("Mobs.Zombies.Attack.Damage"));
			       
					list.add(skele);
					 Zombies.location.put(skele, loc);
					if(i == 4){
						 Zombies.zombies.put((currentsize + 1), list);					
						 list.clear();
					   }				   		
				     }	    
				   }
			    }
			}

		}
	}

	public static void walk(EntityInsentient entity, Location loc, double speed) {
		NavigationAbstract navigation = entity.getNavigation();
		PathEntity pathEntity = navigation.a(loc.getX(), loc.getY(), loc.getZ());
		navigation.a(pathEntity, speed);
	}

	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		if (event.getEntity() instanceof Zombie) {
			event.setCancelled(true);

		}

	}
}
