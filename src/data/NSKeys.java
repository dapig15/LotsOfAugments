
package data;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

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
}
