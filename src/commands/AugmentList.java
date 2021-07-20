package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import data.Gear;
import data.InventoryPair;
import data.NSKeys;
import data.NSKeys.NSKVals;
import main.MagicMonsters;
import utility.Augment;
import utility.AugmentApplier;
import utility.Utility;

class SortAugments implements Comparator<Augment> {

	@Override
	public int compare(Augment o1, Augment o2) {
		char[] str1 = o1.getName().toCharArray();
		char[] str2 = o2.getName().toCharArray();
		StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
		for (int i = 0; i < str1.length; i++) {
			if (str1[i] == '§')
				i++;
			else
				sb1.append(str1[i]);
		}
		for (int i = 0; i < str2.length; i++) {
			if (str2[i] == '§')
				i++;
			else
				sb2.append(str2[i]);
		}
		return sb1.toString().compareTo(sb2.toString());
	}
	
}

public class AugmentList implements CommandExecutor, Listener {
	
	private final MagicMonsters plugin;
	public AugmentList(MagicMonsters plugin) {
		this.plugin = plugin;
	}
	
	private void setAugmentListInventory(Inventory inv, int page) {
		inv.clear();
		ArrayList<Augment> allAugs = plugin.getValidAugments();
		Collections.sort(allAugs, new SortAugments());
		for (int i = 45*page; i < Math.min(45*(page+1), allAugs.size()); i++) {
			ItemStack paper = new ItemStack(Material.PAPER);
			ItemMeta meta = paper.getItemMeta();
			meta.setDisplayName(allAugs.get(i).getName());
			ArrayList<String> al = new ArrayList<>();
			al.add(allAugs.get(i).getDesc());
			al.add("§fWeight: "+allAugs.get(i).getWeight());
			al.add("§eCan apply to:");
			for (int j = 0; j < allAugs.get(i).getApplyArr().length; j++) {
				if (allAugs.get(i).getApplyArr()[j]) {
					String str = plugin.fieldStrings[j].substring(13);
					str = str.substring(0, 1).toUpperCase()+str.substring(1);
					al.add("§e - "+str);
				}
			}
			al.add("§bClick to get this trait!");
			meta.setLore(al);
			meta.getPersistentDataContainer()
				.set(NSKeys.getNSKey(NSKVals.AUGMENT_PAPER_ID), PersistentDataType.STRING,
						allAugs.get(i).getId());
			paper.setItemMeta(meta);
			inv.setItem(i-(45*page), paper);
		}
		for (int i = 45; i < 54; i++) {
			inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}
		if (allAugs.size() > (page+1)*45) {
			ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§a§lNEXT >>>");
			meta.setLore(Arrays.asList(new String[] {"§7Page "+(page+1)+"/"+(Math.max(0, allAugs.size()-1)/45+1)}));
			item.setItemMeta(meta);
			inv.setItem(52, item);
		} else {
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7§lNEXT >>>");
			meta.setLore(Arrays.asList(new String[] {"§7Page "+(page+1)+"/"+(Math.max(0, allAugs.size()-1)/45+1)}));
			item.setItemMeta(meta);
			inv.setItem(52, item);
		}
		if (page > 0) {
			ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§a§l<<< BACK");
			meta.setLore(Arrays.asList(new String[] {"§7Page "+(page+1)+"/1"}));
			item.setItemMeta(meta);
			inv.setItem(46, item);
		} else {
			ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7§l<<< BACK");
			meta.setLore(Arrays.asList(new String[] {"§7Page "+(page+1)+"/1"}));
			item.setItemMeta(meta);
			inv.setItem(46, item);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Inventory inv = Bukkit.createInventory(
					player, 54, "§4Augment List");
			setAugmentListInventory(inv, 0);
			player.openInventory(inv);
			plugin.getInventoryMap().put(player,
					new InventoryPair(inv, InventoryPair.AUGMENT_LIST, 0));
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onAugmentListClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if (plugin.getInventoryMap().get(player) != null &&
					plugin.getInventoryMap().get(player).getType() == InventoryPair.AUGMENT_LIST) {
				// inside alist
				if (event.getClickedInventory() == event.getInventory()) {
					// clicked inside alist
					if (event.getSlot() == 46 &&
							event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
						InventoryPair ip = plugin.getInventoryMap().get(player);
						ip.setExtraData(ip.getExtraData()-1);
						setAugmentListInventory(event.getInventory(), ip.getExtraData());
					} else if (event.getSlot() == 52 &&
						event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
						InventoryPair ip = plugin.getInventoryMap().get(player);
						ip.setExtraData(ip.getExtraData()+1);
						setAugmentListInventory(event.getInventory(), ip.getExtraData());
					} else if (event.getCurrentItem().getType() == Material.PAPER &&
							!Utility.itemStackExists(event.getCursor())) {
						ItemStack stack = new ItemStack(event.getCurrentItem());
						ItemMeta meta = stack.getItemMeta();
						List<String> lore = meta.getLore();
						lore.set(lore.size()-1, "§fClick gear §bin your hotbar §fwhile holding this!");
						if (player.getGameMode() == GameMode.CREATIVE) {
							player.sendMessage("§cThese papers don't work properly in creative!");
							player.sendMessage("§cIt's unsolvable, please switch to survival before applying.");
						}
						meta.setLore(lore);
						stack.setItemMeta(meta);
						stack.setAmount(64);
						player.getInventory().addItem(stack);
					}
				}
				event.setCancelled(true);
			} else if (event.getClickedInventory() == player.getInventory()) {
				// clicked inside player inv
				if (event.getSlot() < 9) {
					// clicked inside hotbar
					if (Utility.itemStackExists(event.getCursor()) &&
							event.getCursor().getItemMeta() != null &&
							event.getCursor().getItemMeta().getPersistentDataContainer()
							.has(NSKeys.getNSKey(NSKVals.AUGMENT_PAPER_ID), PersistentDataType.STRING) &&
							Utility.itemStackExists(event.getCurrentItem()) &&
							Gear.getGearType(event.getCurrentItem().getType()) != -1) {
						String attrId = event.getCursor().getItemMeta().getPersistentDataContainer()
								.get(NSKeys.getNSKey(NSKVals.AUGMENT_PAPER_ID), PersistentDataType.STRING);
						if (plugin.getValidAugment(attrId) != null) {
							ItemStack gear = event.getCurrentItem();
							int gearType = Gear.getGearType(gear.getType());
							Augment aug = plugin.getValidAugment(attrId);
							if (aug.getApplyArr()[gearType]) {
								new AugmentApplier(plugin).addAttributes(gear, aug);
								player.sendMessage("§aSuccess!");
								if (event.getCursor().getAmount() == 1) {
									player.setItemOnCursor(null);
								} else {
									player.getItemOnCursor().setAmount(event.getCursor().getAmount()-1);
								}
							} else {
								player.sendMessage("§cItem is incompatible!");
							}
						} else {
							player.sendMessage("§cThis augment no longer exists...");
						}
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
}
