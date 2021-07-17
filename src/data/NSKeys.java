
package data;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class NSKeys {
	public enum Vals {
		CUSTOM,
		FISHING
	}
	public static NamespacedKey getNSKey(Vals v) {
		switch (v) {
		case CUSTOM:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"custom");
		case FISHING:
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"fishing");
		default:
			System.out.println("uh oh");
			return new NamespacedKey(
					Bukkit.getServer().getPluginManager().getPlugin("MagicMonsters"),
					"fail");
		}
	}
	public static PersistentDataType<?, ?> getNSDataType(Vals v) {
		switch (v) {
		case CUSTOM:
			return PersistentDataType.INTEGER;
		case FISHING:
			return PersistentDataType.INTEGER;
		default:
			System.out.println("uh oh");
			return PersistentDataType.INTEGER;
		}
	}
	public static boolean hasNSKey(ItemStack item, Vals v) {
		return item.getItemMeta().getPersistentDataContainer()
				.has(getNSKey(v), getNSDataType(v));
	}
}
