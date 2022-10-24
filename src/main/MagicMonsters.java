
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import blocks.*;
import commands.*;
import data.*;
import data.Gear.GearStats;
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
	public final String[] fieldStrings = new String[] {
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
	private HashMap<String, Augment> validAugmentMap;
	public ArrayList<Augment> getValidAugments() {
		return validAugments;
	}
	public ArrayList<Augment> getValidAugments(int slot) {
		ArrayList<Augment> res = new ArrayList<>();
		for (Augment a : validAugments) {
			if (a.getApplyArr()[slot])
				res.add(a);
		}
		return res;
	}
	public Augment getValidAugment(String str) {
		return validAugmentMap.containsKey(str) ? validAugmentMap.get(str) : null;
	}
	
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	
    	this.getCommand("warmode").setExecutor(new Warmode());
    	AugmentList augList = new AugmentList(this);
    	this.getCommand("augmentlist").setExecutor(augList);
    	
    	PluginManager pm = getServer().getPluginManager();
    	AugmentApplier applier = new AugmentApplier(this);
    	pm.registerEvents(new AugmentStation(this, applier), pm.getPlugin("MagicMonsters")); // get name from yml
    	pm.registerEvents(augList, pm.getPlugin("MagicMonsters"));
    	
    	// make blank upgrade kit recipe
    	NamespacedKey blankKitKey = new NamespacedKey(this, "blank_kit_recipe");
    	ShapedRecipe blankKitRecipe = new ShapedRecipe(blankKitKey, UpgradeKit.createUpgradeKit(-1, 0));
    	blankKitRecipe.shape(" I ", "IBI", " I ");
    	blankKitRecipe.setIngredient('I', Material.IRON_NUGGET);
    	blankKitRecipe.setIngredient('B', Material.BRICK);
    	Bukkit.addRecipe(blankKitRecipe);
    	
    	// make all the indiv kits' recipes
    	for (GearStats val : Gear.GearStats.values()) {
			String name = val.name();
	    	NamespacedKey upgradeKitKey = new NamespacedKey(this, "upgrade_kit_"+name+"_recipe");
	    	ItemStack newKit = UpgradeKit.createUpgradeKit(Gear.getGearType(Material.valueOf(name)),
					Gear.getTier(Material.valueOf(name)));
	    	newKit.setAmount(4);
	    	ShapedRecipe upgradeKitRecipe = new ShapedRecipe(upgradeKitKey, newKit);
	    	upgradeKitRecipe.shape(" B ", "BGB", " B ");
	    	upgradeKitRecipe.setIngredient('G', Material.valueOf(name));
	    	ExactChoice ec = new ExactChoice(UpgradeKit.createUpgradeKit(-1, 0));
	    	upgradeKitRecipe.setIngredient('B', ec);
	    	Bukkit.addRecipe(upgradeKitRecipe);
		}
    	
    	// make augment station recipe
    	NamespacedKey augStationKey = new NamespacedKey(this, "augment_station_recipe");
    	ShapedRecipe augStationRecipe = new ShapedRecipe(augStationKey, AugmentStation.createAttributeForge());
    	augStationRecipe.shape("III", "SBS", "SSS");
    	augStationRecipe.setIngredient('I', Material.IRON_INGOT);
    	ExactChoice ec = new ExactChoice(UpgradeKit.createUpgradeKit(-1, 0));
    	augStationRecipe.setIngredient('B', ec);
    	augStationRecipe.setIngredient('S', Material.STONE);
    	Bukkit.addRecipe(augStationRecipe);
    	
    	inventoryMap = new HashMap<>();
    	
    	this.saveDefaultConfig();
    	config = this.getConfig();
    	List<String> augmentStrs = config.getStringList("augment-list");
    	validAugments = new ArrayList<>();
    	validAugmentMap = new HashMap<String, Augment>();
    	outer: for (int i = 0; i < augmentStrs.size(); i++) {
    		String augmentId = augmentStrs.get(i);
    		
    		Builder<Attribute, ModifierPair> builder = ImmutableMultimap.builder();
			for (int j = 0; j < attrStrings.length; j++) {
				String attr = attrStrings[j];
				for (int k = 0; k < operationStrings.length; k++) {
					String oper = operationStrings[k];
					String path = augmentId+"."+attr+"."+oper;
					if (!(!config.contains(path) || (!config.isDouble(path) && !config.isInt(path)))) {
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
			String desc = "§7(no desc provided)";
			if (config.contains(augmentId+".description") && config.isString(augmentId+".description")) {
				desc = config.getString(augmentId+".description");
			}
			final boolean[] canApply = new boolean[9];
			for (int j = 0; j < canApply.length; j++) {
				if (!(!config.contains(augmentId+"."+fieldStrings[j]) || !config.isBoolean(augmentId+"."+fieldStrings[j]))) {
					canApply[j] = config.getBoolean(augmentId+"."+fieldStrings[j]);
				}
			}
			int weight = 1;
			if (!(!config.contains(augmentId+".weight") || !config.isInt(augmentId+".weight") ||
					config.getInt(augmentId+".weight") < 1)) {
				weight = config.getInt(augmentId+".weight");
			}
			Augment madeAug = new Augment(map, augmentId, name, desc, canApply, weight);
			validAugments.add(madeAug);
			validAugmentMap.put(augmentId, madeAug);
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
