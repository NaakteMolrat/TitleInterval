package me.NaakteMolrat.TitleInterval;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
    // Fired when plugin is first enabled
	static protected JavaPlugin instance;
	FileConfiguration config;
	Timer timer = new Timer();
	String text, subtext; int fadein, showtime, fadeout;
	
    @Override
    public void onEnable() {
    	config = this.getConfig();
    	 this.config.addDefault("interval", 300);
         this.config.addDefault("fadein", 20);
         this.config.addDefault("showtime", 40);
         this.config.addDefault("fadeout", 20);
         this.config.addDefault("text", "Your title");
         this.config.addDefault("subtext", "Your subtitle");
         this.saveDefaultConfig();
        text = config.getString("text"); subtext = config.getString("subtext"); fadein = config.getInt("fadein"); fadeout = config.getInt("fadeout"); showtime = config.getInt("showtime");
    	instance = this;
        timer.schedule(new SayHello(), 0, config.getInt("interval")*1000);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	timer.cancel();
    }
    
    class SayHello extends TimerTask {
        public void run() {
        	new BukkitRunnable() {
                @Override
                public void run() {
                	
                	
                	for(Player p : Bukkit.getOnlinePlayers()) {
                		p.sendTitle(text, subtext, fadein,showtime,fadeout);
                		System.out.println("sent title " +text + " " + subtext + " for " + showtime);
                	}
                }
            }.runTask(instance);
        }
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reloadtitleinterval")) {
        	if(sender.isOp() || sender instanceof ConsoleCommandSender || sender.hasPermission("titleinterval.reload"))  {
            reloadConfig();
            this.config = this.getConfig();
            text = config.getString("text"); subtext = config.getString("subtext"); fadein = config.getInt("fadein"); fadeout = config.getInt("fadeout"); showtime = config.getInt("showtime");
            timer.cancel();
            timer = new Timer();
            timer.schedule(new SayHello(), 0, config.getInt("interval")*1000);
            
            sender.sendMessage(ChatColor.GREEN + "Reloaded TitleInterval config!");
        	} else {
        		sender.sendMessage(ChatColor.GREEN + "You are not allowed to do this!");
        	}
        }
        return true;
    }
    
}
