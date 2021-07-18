package blocks;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import data.*;
import data.NSKeys.NSKVals;

public class AttributeArmorKit {
	
	private static String[] tiers = new String[] {
			"Leather",
			"Chain",
			"Iron",
			"Gold",
			"Diamond",
			"Netherite"
	};
	private static String[] coloredTiers = new String[] {
			"§6§lLEATHER",
			"§7§lCHAIN",
			"§f§lIRON",
			"§e§lGOLD",
			"§b§lDIAMOND",
			"§4§lNETHERITE"
	};
	
	public static ItemStack createAttributeArmorKit(int tier) {
		ItemStack item = new ItemStack(Material.NETHER_BRICK);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("§f"+tiers[tier]+" Armor Kit");
		im.setLore(Arrays.asList(new String[] {
				"§fUse at an attribute forge!",
				"§fTier: "+coloredTiers[tier]
		}));
		im.getPersistentDataContainer().set(
				NSKeys.getNSKey(NSKVals.ATTR_ARMOR_KIT), PersistentDataType.INTEGER, tier);
		item.setItemMeta(im);
		return item;
	}
	
}
