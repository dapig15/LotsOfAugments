
package commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Warmode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			PlayerInventory inv = player.getInventory();
    		inv.clear();
    		inv.setHelmet(createItem(Material.IRON_HELMET, 1, "§c§lWar Helm", "§4Shined to perfection."));
    		inv.setChestplate(createItem(Material.DIAMOND_CHESTPLATE, 1, "§c§lWar Chestplate", "§4Encrusted with brilliant gems."));
    		inv.setLeggings(createItem(Material.IRON_LEGGINGS, 1, "§c§lWar Greaves", "§4Heavy, but effective."));
    		inv.setBoots(createItem(Material.DIAMOND_BOOTS, 1, "§c§lWar Boots", "§4Made for trampling."));
    		ItemStack sword = createItem(Material.DIAMOND_SWORD, 1, "§c§lWar Blade", "§4Ready to draw blood.");
    		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
    		sword.addEnchantment(Enchantment.KNOCKBACK, 1);
    		inv.setItem(0, sword);
    		inv.setItemInOffHand(createItem(Material.SHIELD, 1, "§c§lWar Shield", "§4Seemingly indestructible."));
    		inv.setItem(7, createItem(Material.GOLDEN_APPLE, 3, "§c§lWar Gapple", "§4Infused with human blood."));
    		inv.setItem(8, createItem(Material.COOKED_BEEF, 64, "§c§lWar Steak", "§4actually its just steak"));
    		return true;
		}
		return false;
	}
	
	private ItemStack createItem(Material m, int size, String name, String lore) {
		ItemStack item = new ItemStack(m, size);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		ArrayList<String> al = new ArrayList<>();
		al.add(lore);
		im.setLore(al);
		item.setItemMeta(im);
		return item;
	}
	
}
