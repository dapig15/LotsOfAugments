
package main;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import blocks.*;
import commands.*;

public class MagicMonsters extends JavaPlugin {
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	this.getCommand("warmode").setExecutor(new Warmode());
    	this.getCommand("magictest").setExecutor(new MagicTest());
    	this.getCommand("senbonstorm").setExecutor(new Senbonstorm());
    	
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvents(new AttributeForge(), pm.getPlugin("MagicMonsters")); // get name from yml
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
