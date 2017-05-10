package de.mavecrit.pawars.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.booleans.GameBool;

public class State implements Listener{
	
	@EventHandler
	public void onPing(ServerListPingEvent e){
		if(GameBool.isStarted()){
			e.setMotd(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MOTD.Running")));
		} else {
			if(Bukkit.getOnlinePlayers().size() == Main.getPlugin().getConfig().getInt("General.ServerSize")){
				e.setMotd(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MOTD.Full")));
			} else {
			e.setMotd(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MOTD.Waiting")));
			}
		}
	}
}
