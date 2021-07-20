
package data;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class NSKeys {
	public enum NSKVals {
		CUSTOM,
		FISHING,
		ATTRIBUTE_FORGE,
		UPGRADE_KIT,
		AUGMENT_ID,
		AUGMENT_PAPER_ID
	}
	public static NamespacedKey getNSKey(NSKVals v) {
		switch (v) {
		case CUSTOM:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"custom");
		case FISHING:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"fishing");
		case ATTRIBUTE_FORGE:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"reforge_anvil");
		case UPGRADE_KIT:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"attr_armor_kit");
		case AUGMENT_ID:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"augment_id");
		case AUGMENT_PAPER_ID:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"augment_paper_id");
		default:
			System.out.println("uh oh");
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"fail");
		}
	}
	public static PersistentDataType<?, ?> getNSDataType(NSKVals v) {
		switch (v) {
		case CUSTOM: case FISHING: case ATTRIBUTE_FORGE: case UPGRADE_KIT:
			return PersistentDataType.INTEGER;
		case AUGMENT_ID: case AUGMENT_PAPER_ID:
			return PersistentDataType.STRING;
		default:
			System.out.println("uh oh");
			return PersistentDataType.INTEGER;
		}
	}
	public static boolean hasNSKey(ItemStack item, NSKVals v) {
		return item.getItemMeta().getPersistentDataContainer()
				.has(getNSKey(v), getNSDataType(v));
	}
	public static boolean hasNSKey(PersistentDataContainer pdc, NSKVals v) {
		return pdc.has(getNSKey(v), getNSDataType(v));
	}
}
