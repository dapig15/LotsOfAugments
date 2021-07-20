package blocks;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import data.*;
import data.NSKeys.NSKVals;

public class UpgradeKit {
	
	private static String[] armorTiers = new String[] {
			"Leather",
			"Chain",
			"Iron",
			"Golden",
			"Diamond",
			"Netherite"
	};
	private static String[] toolTiers = new String[] {
			"Wooden",
			"Stone",
			"Iron",
			"Golden",
			"Diamond",
			"Netherite"
	};
	private static String[] coloredArmorTiers = new String[] {
			"§6§lLEATHER",
			"§7§lCHAIN",
			"§f§lIRON",
			"§e§lGOLDEN",
			"§b§lDIAMOND",
			"§4§lNETHERITE"
	};
	private static String[] coloredToolTiers = new String[] {
			"§6§lWOODEN",
			"§7§lSTONE",
			"§f§lIRON",
			"§e§lGOLDEN",
			"§b§lDIAMOND",
			"§4§lNETHERITE"
	};
	
	public static ItemStack createUpgradeKit(int gearType, int tier) {
		if (tier >= 6 || tier < 0)
			tier = 0;
		if (gearType >= 9 || gearType < 0)
			gearType = -1;
		ItemStack item = new ItemStack(Material.NETHER_BRICK);
		ItemMeta im = item.getItemMeta();
		switch (gearType) {
		case 0:
			item.setType(Material.NETHER_BRICK);
			im.setDisplayName("§f"+armorTiers[tier]+" Armor Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!",
					"§fTier: "+coloredArmorTiers[tier]
			}));
			break;
		case 1:
			item.setType(Material.NETHER_BRICK);
			im.setDisplayName("§fElytra Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!"
			}));
			break;
		case 2:
			item.setType(Material.BRICK);
			im.setDisplayName("§f"+toolTiers[tier]+" Sword Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!",
					"§fTier: "+coloredToolTiers[tier]
			}));
			break;
		case 3:
			item.setType(Material.BRICK);
			im.setDisplayName("§f"+toolTiers[tier]+" Axe Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!",
					"§fTier: "+coloredToolTiers[tier]
			}));
			break;
		case 4:
			item.setType(Material.BRICK);
			im.setDisplayName("§fTrident Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!"
			}));
			break;
		case 5:
			item.setType(Material.BRICK);
			im.setDisplayName("§fBow Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!"
			}));
			break;
		case 6:
			item.setType(Material.BRICK);
			im.setDisplayName("§fCrossbow Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!"
			}));
			break;
		case 7:
			item.setType(Material.NETHER_BRICK);
			im.setDisplayName("§fShield Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!"
			}));
			break;
		case 8:
			item.setType(Material.BRICK);
			im.setDisplayName("§f"+toolTiers[tier]+" Tool Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fUse at an augmentation station!",
					"§fTier: "+coloredToolTiers[tier]
			}));
			break;
		default:
			item.setType(Material.BRICK);
			im.setDisplayName("§fBlank Upgrade Kit");
			im.setLore(Arrays.asList(new String[] {
					"§fPut four of these around extra gear in a workbench!",
			}));
			break;
		}
		im.getPersistentDataContainer().set(
				NSKeys.getNSKey(NSKVals.UPGRADE_KIT), PersistentDataType.INTEGER, 
				(gearType != -1 ? gearType*10+tier : -1));
		item.setItemMeta(im);
		return item;
	}
	
}
