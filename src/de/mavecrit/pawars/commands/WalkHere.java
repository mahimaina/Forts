package de.mavecrit.pawars.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import de.mavecrit.pawars.events.PlayerJoin;
import de.mavecrit.pawars.guis.VoteGUI;
import de.mavecrit.pawars.location.LocationGetter;


public class WalkHere implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
	
		if (cmd.getName().equalsIgnoreCase("walkhere")) {
			
			VoteGUI.openGui(player);
		
			for(Entity en : player.getWorld().getEntities()){
				if(en instanceof Zombie){
					Zombie z = (Zombie) en;
					
				}
				
			}
		}
		return false;
	}
}
