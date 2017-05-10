package de.mavecrit.pawars.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.teams.TeamList;
import de.mavecrit.pawars.util.ClickOnTeamEvent;

public class ClickOnTeam implements Listener{
	
	
	@EventHandler
	public void onTeam(ClickOnTeamEvent e){
		Player p = e.getPlayer();
		
		if(TeamList.blue.contains(p.getName())){
			TeamList.blue.remove(p.getName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
		}
		if(TeamList.red.contains(p.getName())){
			TeamList.red.remove(p.getName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
		}
		if(TeamList.green.contains(p.getName())){
			TeamList.green.remove(p.getName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
		}
		if(TeamList.yellow.contains(p.getName())){
			TeamList.yellow.remove(p.getName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Messages.LeftTeam")));
		}
	}
}
