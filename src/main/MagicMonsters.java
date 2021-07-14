
package main;

import org.bukkit.plugin.java.JavaPlugin;

import commands.Warmode;

public class MagicMonsters extends JavaPlugin {
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	this.getCommand("warmode").setExecutor(new Warmode());
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
