package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import data.InventoryPair;
import main.MagicMonsters;
import utility.Augment;
import utility.Utility;

class SortAugments implements Comparator<Augment> {

	@Override
	public int compare(Augment o1, Augment o2) {
		return o1.getName().compareTo(o2.getName());
	}
	
}

public class AugmentList implements CommandExecutor, Listener {
	
	private final MagicMonsters plugin;
	public AugmentList(MagicMonsters plugin) {
		this.plugin = plugin;
	}
	
	private void setAugmentListInventory(Inventory inv, int page) {
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
			paper.setItemMeta(meta);
			inv.setItem(i-(45*page), paper);
		}
		for (int i = 45; i < 54; i++) {
			inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
		}
		if (allAugs.size() > (page+1)*45) {
			ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§a§lNEXT >>>");
			meta.setLore(Arrays.asList(new String[] {"§7Page "+(page+1)+"/1"}));
			item.setItemMeta(meta);
			inv.setItem(52, item);
		} else {
			ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§7§lNEXT >>>");
			meta.setLore(Arrays.asList(new String[] {"§7Page "+(page+1)+"/1"}));
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
	
	public void onAugmentListClick(InventoryClickEvent event) {
		event.setCancelled(true);
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
						ItemStack stack = event.getCurrentItem();
						stack.setAmount(64);
						player.setItemOnCursor(stack);
					}
				}
			}
		}
	}
	
	public void onAugmentListClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			HashMap<Player, InventoryPair> hm = plugin.getInventoryMap();
			if (hm.get(player) != null && hm.get(player).getInv() == event.getInventory()) {
				player.sendMessage("removed");
				hm.remove(player);
			} else {
				player.sendMessage("oh no");
			}
		}
	}
	
}
