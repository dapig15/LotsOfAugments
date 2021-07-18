package utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utility {
	public static boolean itemStackExists(ItemStack i) {
		return (!(i == null || i.getType() == Material.AIR));
	}
}
