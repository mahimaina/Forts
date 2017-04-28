package de.mavecrit.pawars.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.mavecrit.pawars.Main;


public abstract class Countdown extends BukkitRunnable {
 
        private String filled;
        private String rest;
        private int bars;
        private String barchar;
        private int currsec;
        private int secs;
        public Main pl;
       
        public Countdown(String filled, String rest, String barchar, int bars, Plugin plugin, int secs) {
                this.filled = filled;
                this.rest = rest;
                this.bars = bars;
                this.barchar = barchar;
                this.currsec = 0;
                this.secs = secs;
                this.runTaskTimer(plugin, 0, 20);
        }
        public Countdown(int bars, String barchar, int secs, Plugin plugin) {
                this("§a", "§7", barchar, bars, plugin, secs);
        }
        public Countdown(int bars, int secs, Plugin plugin) {
                this(bars, "▒", secs, plugin);
        }
        public Countdown(int secs, Plugin plugin) {
                this(10, secs, plugin);
        }
        public Countdown(Main pl) {
                this(10, pl);
        }
        @Override
        public void run() {
                CountdownCalculator c = new CountdownCalculator(secs, currsec, bars);
                String a = filled;
                for(int i = 0; i < c.getFilledBars(); i++) {
                        a += barchar;
                }
                a += rest;
                for(int i = 0; i < c.getRestBars(); i++) {
                        a += barchar;
                }
                send(a);
                if(currsec >= secs) {
                        this.cancel();
                        for(Player all : Bukkit.getOnlinePlayers()){
                        Actionbar_1_11_R1.sendActionbar(all, "");
                        }
                        onEnd();
                }
                currsec++;
        }
        public int getCurrent() {
                return currsec;
        }
        public int getDuration() {
                return secs;
        }
       
        public abstract void send(String builder);
        public abstract void onEnd();
}