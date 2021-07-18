
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import blocks.*;
import commands.*;
import data.*;
import utility.Augment;
import utility.AugmentApplier;
import utility.ModifierPair;

public class MagicMonsters extends JavaPlugin {
	
	// HashMap for players and special inventories (attr forge, etc)
	private HashMap<Player, InventoryPair> inventoryMap;
	private FileConfiguration config;
	
	private final String[] attrStrings = new String[] {
			"armor",
			"armor-toughness",
			"attack-damage",
			"attack-speed",
			"knockback-resistance",
			"luck",
			"max-health",
			"movement-speed"
	};
	private final Attribute[] attrVals = new Attribute[] {
			Attribute.GENERIC_ARMOR,
			Attribute.GENERIC_ARMOR_TOUGHNESS,
			Attribute.GENERIC_ATTACK_DAMAGE,
			Attribute.GENERIC_ATTACK_SPEED,
			Attribute.GENERIC_KNOCKBACK_RESISTANCE,
			Attribute.GENERIC_LUCK,
			Attribute.GENERIC_MAX_HEALTH,
			Attribute.GENERIC_MOVEMENT_SPEED
	};
	private final String[] operationStrings = new String[] {
			"add",
			"mult-add",
			"mult-mult"
	};
	private final Operation[] operationVals = new Operation[] {
			Operation.ADD_NUMBER,
			Operation.ADD_SCALAR,
			Operation.MULTIPLY_SCALAR_1
	};
	private final String[] fieldStrings = new String[] {
			"can-apply-to-armor",
			"can-apply-to-elytras",
			"can-apply-to-swords",
			"can-apply-to-axes",
			"can-apply-to-tridents",
			"can-apply-to-bows",
			"can-apply-to-crossbows",
			"can-apply-to-shields",
			"can-apply-to-tools"
	};
	
	private ArrayList<Augment> validAugments;
	public ArrayList<Augment> getValidAugments(int slot) {
		ArrayList<Augment> res = new ArrayList<>();
		for (Augment a : validAugments) {
			if (a.getApplyArr()[slot])
				res.add(a);
		}
		return res;
	}
	
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	
    	this.getCommand("warmode").setExecutor(new Warmode());
    	this.getCommand("magictest").setExecutor(new MagicTest());
    	this.getCommand("senbonstorm").setExecutor(new Senbonstorm());
    	
    	PluginManager pm = getServer().getPluginManager();
    	AugmentApplier applier = new AugmentApplier(this);
    	pm.registerEvents(new AugmentStation(this, applier), pm.getPlugin("MagicMonsters")); // get name from yml
    	
    	inventoryMap = new HashMap<>();
    	
    	this.saveDefaultConfig();
    	config = this.getConfig();
    	List<String> augmentStrs = config.getStringList("augment-list");
    	validAugments = new ArrayList<>();
    	outer: for (int i = 0; i < augmentStrs.size(); i++) {
    		String augmentId = augmentStrs.get(i);
    		this.getLogger().info("found augment "+augmentId);
    		
    		Builder<Attribute, ModifierPair> builder = ImmutableMultimap.builder();
			for (int j = 0; j < attrStrings.length; j++) {
				String attr = attrStrings[j];
				for (int k = 0; k < operationStrings.length; k++) {
					String oper = operationStrings[k];
					String path = augmentId+"."+attr+"."+oper;
					if (!config.contains(path) || (!config.isDouble(path) && !config.isInt(path))) {
						this.getLogger().info(augmentId + " has invalid "+path+"!");
					} else {
						if (config.getDouble(path) != 0) {
							builder.put(attrVals[j], new ModifierPair(config.getDouble(path), operationVals[k]));
						}
					}
				}
			}
			ImmutableMultimap<Attribute, ModifierPair> map = builder.build();
			if (!config.contains(augmentId+".display-name") || !config.isString(augmentId+".display-name")) {
				this.getLogger().info(augmentId + " has invalid "+augmentId+".display-name"+"! skipping it");
				continue outer;
			}
			final String name = config.getString(augmentId+".display-name");
			final boolean[] canApply = new boolean[8];
			for (int j = 0; j < canApply.length; j++) {
				if (!config.contains(augmentId+"."+fieldStrings[j]) || !config.isBoolean(augmentId+"."+fieldStrings[j])) {
					this.getLogger().info(augmentId + " has invalid "+augmentId+"."+fieldStrings[j]+"!");
				} else {
					canApply[j] = config.getBoolean(augmentId+"."+fieldStrings[j]);
				}
			}
			
			validAugments.add(new Augment(map, name, canApply));
		}
    	this.getLogger().info("we got "+validAugments.size()+" augs");
    	for (int i = 0; i < validAugments.size(); i++) {
			this.getLogger().info("including "+validAugments.get(i).getName());
		}
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }

	public HashMap<Player, InventoryPair> getInventoryMap() {
		return inventoryMap;
	}
}
