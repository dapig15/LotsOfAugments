
package blocks;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import data.NSKeys;
import data.NSKeys.NSKVals;

public class AttributeForge implements Listener {
	
	public static ItemStack createAttributeForge() {
		ItemStack bukkitItem = new ItemStack(Material.BLAST_FURNACE);
		ItemMeta bukkitItemMeta = bukkitItem.getItemMeta();
		bukkitItemMeta.setDisplayName("§a§lAttribute Forge");
		bukkitItemMeta.setLore(Arrays.asList("§aTweak your gear!"));
		bukkitItemMeta.getPersistentDataContainer().set(
				NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE), PersistentDataType.INTEGER, 1);
		bukkitItem.setItemMeta(bukkitItemMeta);
		return bukkitItem;
	}
	
	@EventHandler
	public void onReforgeAnvilPlace(BlockPlaceEvent event) {
		if (NSKeys.hasNSKey(event.getItemInHand(), NSKVals.ATTRIBUTE_FORGE)) {
			event.getPlayer().sendMessage("woooo");
			BlastFurnace bf = (BlastFurnace) event.getBlockPlaced().getState();
			bf.getPersistentDataContainer().set(NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE),
					PersistentDataType.INTEGER, 1);
			bf.update();
		}
	}
	
	@EventHandler
	public void onReforgeAnvilMine(BlockBreakEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("block broken!");
		if (event.getBlock().getBlockData().getMaterial() == Material.BLAST_FURNACE) {
			BlastFurnace bf = (BlastFurnace) event.getBlock().getState();
			player.sendMessage("blast furnace broken!");
			if (bf.getPersistentDataContainer().has(
					NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE), PersistentDataType.INTEGER)) {
				player.sendMessage("attribute forge broken!");
				event.setDropItems(false);
				player.getWorld().dropItemNaturally(event.getBlock().getLocation(),
						createAttributeForge());
			}
		}
	}
	
}
