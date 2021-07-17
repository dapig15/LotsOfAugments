package commands;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import blocks.AttributeForge;
import data.NSKeys;
import data.NSKeys.NSKVals;

public class MagicTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && args.length > 0) {
			Player player = (Player) sender;
			if (args[0].equals("megasword")) {
				ItemStack bukkitItem = new ItemStack(Material.DIAMOND_SWORD);
				ItemMeta bukkitItemMeta = bukkitItem.getItemMeta();
				bukkitItemMeta.setDisplayName("§a§lMega Sword");
				bukkitItemMeta.setLore(Arrays.asList("§aA very strong sword"));
				bukkitItemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
						new AttributeModifier(UUID.randomUUID(), "Speed", 0.02, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
				bukkitItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
						new AttributeModifier(UUID.randomUUID(), "ASpeed", 1.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
				bukkitItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
						new AttributeModifier(UUID.randomUUID(), "ASpeed", 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
				bukkitItem.setItemMeta(bukkitItemMeta);
				player.getInventory().addItem(bukkitItem);
			} else if (args[0].equals("megaaxe")) {
				ItemStack bukkitItem = new ItemStack(Material.DIAMOND_AXE);
				ItemMeta bukkitItemMeta = bukkitItem.getItemMeta();
				bukkitItemMeta.setDisplayName("§a§lMega Sword");
				bukkitItemMeta.setLore(Arrays.asList("§aA very strong axe"));
				bukkitItemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
						new AttributeModifier(UUID.randomUUID(), "Speed", 0.02, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
				bukkitItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
						new AttributeModifier(UUID.randomUUID(), "ASpeed", 0.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
				bukkitItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
						new AttributeModifier(UUID.randomUUID(), "ASpeed", 30, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
				bukkitItemMeta.getPersistentDataContainer().set(
						NSKeys.getNSKey(NSKVals.CUSTOM), PersistentDataType.INTEGER, 1);
				bukkitItem.setItemMeta(bukkitItemMeta);
				player.getInventory().addItem(bukkitItem);
			} else if (args[0].equals("findaxe")) {
				if (player.getInventory().contains(Material.DIAMOND_AXE)) {
					player.sendMessage("§afound a diamond axe!");
				} else {
					player.sendMessage("§ano diamond axe");
				}
			} else if (args[0].equals("findmegaaxe")) {
				ItemStack[] inventory = player.getInventory().getContents();
				boolean flag = false;
				for (ItemStack item : inventory) {
					if (item == null || item.getType() == Material.AIR)
						continue;
					if (item.getType() == Material.DIAMOND_AXE) {
						flag = true;
						if (!item.getItemMeta().getPersistentDataContainer()
								.has(NSKeys.getNSKey(NSKVals.CUSTOM), PersistentDataType.INTEGER )) {
							player.sendMessage("§afound dia axe...");
							continue;
						}
						if (item.getItemMeta().getPersistentDataContainer()
								.get(NSKeys.getNSKey(NSKVals.CUSTOM), PersistentDataType.INTEGER) == 1) {
							player.sendMessage("§a== mega axe!");
						}
						if (item.getItemMeta().getPersistentDataContainer()
								.get(NSKeys.getNSKey(NSKVals.CUSTOM), PersistentDataType.INTEGER).equals(1)) {
							player.sendMessage("§aequals mega axe!");
						}
						if (item.getItemMeta().getPersistentDataContainer()
								.get(NSKeys.getNSKey(NSKVals.CUSTOM), PersistentDataType.INTEGER).equals(Integer.valueOf(1))) {
							player.sendMessage("§aequals Integer mega axe!");
						}
						player.sendMessage("§afound dia axe!");
						
					}
					if (item.isSimilar(new ItemStack(Material.DIAMOND_HELMET))) {
						player.sendMessage("§afound dia helm!");
					}
				}
				if (!flag) {
					player.sendMessage("§afound nothing");
				}
			} else if (args[0].equals("af")) {
				player.getInventory().addItem(AttributeForge.createAttributeForge());
			} else {
				player.sendMessage("§atold u not to run this cmd");
			}
			return true;
		}
		System.out.println("oops");
		return false;
	}

}
