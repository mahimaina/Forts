package de.mavecrit.pawars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import de.mavecrit.pawars.Main;
import de.mavecrit.pawars.lists.Alive;
import de.mavecrit.pawars.spectator.SpectatorInv;

public class RandomSpectateToggle implements Listener {

	@EventHandler
	public void onClick(PlayerToggleSneakEvent  e) {
		Player p = e.getPlayer();

			if (Main.getAPI().isSpectating(p)) {
				if(Alive.Spectator.contains(p)){
					SpectatorInv.openInv(p);
				}
			}
	}
}
