package de.mavecrit.pawars.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


 
public abstract class ActionBarCountdown extends Countdown {
	
 
        public ActionBarCountdown(int secs, Plugin plugin) {
                super(secs, plugin);
        }
        
        @Override
        public void send(String builder) {

        	  for(Player all : Bukkit.getOnlinePlayers()){
                  Actionbar_1_11_R1.sendActionbar(all, "");
                  }
                
        }
 
        @Override
        public abstract void onEnd();
       
        public abstract String transformText(String curr);
}