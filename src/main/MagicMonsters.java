
package main;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import blocks.*;
import commands.*;
import data.*;

public class MagicMonsters extends JavaPlugin {
	
	// HashMap for players and special inventories (attr forge, etc)
	private static HashMap<Player, InventoryPair> inventoryMap;
	
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	this.getCommand("warmode").setExecutor(new Warmode());
    	this.getCommand("magictest").setExecutor(new MagicTest());
    	this.getCommand("senbonstorm").setExecutor(new Senbonstorm());
    	
    	PluginManager pm = getServer().getPluginManager();
    	pm.registerEvents(new AttributeForge(), pm.getPlugin("MagicMonsters")); // get name from yml
    	
    	inventoryMap = new HashMap<>();
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

	public static HashMap<Player, InventoryPair> getInventoryMap() {
		return inventoryMap;
	}
}
