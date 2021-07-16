
package main;

import org.bukkit.plugin.java.JavaPlugin;

import commands.*;

public class MagicMonsters extends JavaPlugin {
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	this.getCommand("warmode").setExecutor(new Warmode());
    	this.getCommand("magictest").setExecutor(new MagicTest());
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
