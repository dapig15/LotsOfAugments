
package blocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Structure;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.google.common.collect.Multimap;

import data.Gear;
import data.InventoryPair;
import data.NSKeys;
import data.NSKeys.NSKVals;
import main.MagicMonsters;
import utility.AugmentApplier;
import utility.Utility;

public class AugmentStation implements Listener {
	
	// TODO REMEMBER THAT MOST OF THESE LISTENERS SHOULD GO INTO THEIR OWN CLASS
	// TODO IF YOU END UP ADDING MORE BLOCKS LIKE THIS
	
	private void updateWool(ItemStack gear, ItemStack kit, Inventory inv) {
		ItemStack wool = new ItemStack(Material.GRAY_WOOL);
		ItemMeta meta = wool.getItemMeta();
		if (Utility.itemStackExists(gear) && Utility.itemStackExists(kit)) {
			int kitData = kit.getItemMeta()
					.getPersistentDataContainer()
					.get(NSKeys.getNSKey(NSKVals.UPGRADE_KIT), PersistentDataType.INTEGER);
			if (Gear.getTier(gear.getType()) == kitData%10 &&
					Gear.getGearType(gear.getType()) == kitData/10) {
				wool.setType(Material.GREEN_WOOL);
				meta.setDisplayName("§a§lFORGE!");
			} else {
				wool.setType(Material.RED_WOOL);
				meta.setDisplayName("§cGear and kit mismatch...");
			}
		} else {
			meta.setDisplayName("§fPlace gear and upgrade kit!");
		}
		wool.setItemMeta(meta);
		inv.setItem(16, wool);
	}
	
	private final MagicMonsters plugin;
	private final AugmentApplier applier;
	public AugmentStation(MagicMonsters plugin, AugmentApplier applier) {
		this.plugin = plugin;
		this.applier = applier;
	}
	
	public static ItemStack createAttributeForge() {
		ItemStack bukkitItem = new ItemStack(Material.STRUCTURE_BLOCK);
		ItemMeta bukkitItemMeta = bukkitItem.getItemMeta();
		bukkitItemMeta.setDisplayName("§a§lAttribute Forge");
		bukkitItemMeta.setLore(Arrays.asList("§aTweak your gear!"));
		bukkitItemMeta.getPersistentDataContainer().set(
				NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE), PersistentDataType.INTEGER, 1);
		bukkitItem.setItemMeta(bukkitItemMeta);
		return bukkitItem;
	}
	
	public static boolean isAttributeForge(Block b) {
		if (b.getBlockData().getMaterial() == Material.STRUCTURE_BLOCK) {
			Structure s = (Structure) b.getState();
			return (s.getPersistentDataContainer().has(
					NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE), PersistentDataType.INTEGER));
		}
		return false;
	}
	
	@EventHandler
	public void onReforgeAnvilPlace(BlockPlaceEvent event) {
		if (NSKeys.hasNSKey(event.getItemInHand(), NSKVals.ATTRIBUTE_FORGE)) {
			event.getPlayer().sendMessage("woooo");
			Structure s = (Structure) event.getBlockPlaced().getState();
			s.getPersistentDataContainer().set(NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE),
					PersistentDataType.INTEGER, 1);
			s.update();
		}
	}
	
	@EventHandler
	public void onReforgeAnvilMine(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (isAttributeForge(event.getBlock())) {
			player.sendMessage("attribute forge broken!");
			event.setDropItems(false);
			player.getWorld().dropItemNaturally(event.getBlock().getLocation(),
					createAttributeForge());
		}
	}

	@EventHandler
	public void onUseReforgeAnvil(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block clickedBlock = event.getClickedBlock();
		if (clickedBlock != null)
			player.sendMessage("you clicked on a "+clickedBlock.getType().name());
		player.sendMessage("did u click a block? "+(event.getAction() == Action.RIGHT_CLICK_BLOCK));
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (isAttributeForge(clickedBlock)) {
				event.setCancelled(true);
				Inventory inv = Bukkit.createInventory(
						player, 27, "§4Attribute Forge");
				for (int i = 0; i < 27; i++) {
					if (i == 10) {
						ItemStack instructions = new ItemStack(Material.PAPER);
						ItemMeta meta = instructions.getItemMeta();
						meta.setDisplayName("§f§lHow to Use");
						meta.setLore(Arrays.asList(new String[] {
								"§fPlace §bgear §fin the left slot,",
								"§fplace a fitting §bupgrade kit §fin the right,",
								"§fand §bclick the glass block §fto forge!"
						}));
						instructions.setItemMeta(meta);
						inv.setItem(10, instructions);
					} else if (i == 16) {
						ItemStack instructions = new ItemStack(Material.GRAY_WOOL);
						ItemMeta meta = instructions.getItemMeta();
						meta.setDisplayName("§fPlace gear and upgrade kit!");
						instructions.setItemMeta(meta);
						inv.setItem(16, instructions);
					} else if (i != 12 && i != 14) {
						inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
					}
				}
				player.openInventory(inv);
				if (plugin.getInventoryMap().put(event.getPlayer(),
						new InventoryPair(inv, InventoryPair.ATTRIBUTE_FORGE)) == null) {
					player.sendMessage("new inventory!");
				} else {
					player.sendMessage("already had one??");
				}
			}
		}
	}

	@EventHandler
	public void onReforgeAnvilInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			player.sendMessage("you clicked in an inv!");
			HashMap<Player, InventoryPair> hm = plugin.getInventoryMap();
			if (hm.get(player) != null && hm.get(player).getInv() == event.getClickedInventory()) {
				player.sendMessage("you clicked in a stored thingy!");
				if (hm.get(player).getType() == InventoryPair.ATTRIBUTE_FORGE) {
					player.sendMessage("you clicked in an attr forge inv!");
					player.sendMessage("clicked slot: "+event.getSlot());
					player.sendMessage("clicked rawslot: "+event.getRawSlot());
					switch (event.getRawSlot()) {
					case 12:
						if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
							if (Gear.getGearType(event.getCursor().getType()) == -1) {
								event.setCancelled(true);
								player.sendMessage("§cPlease place gear here!");
							} else {
								player.sendMessage("tier "+Gear.getTier(event.getCursor().getType()));
							}
						}
						updateWool(event.getCursor(), event.getInventory().getItem(14), event.getInventory());
						break;
					case 14:
						if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
							if (!NSKeys.hasNSKey(event.getCursor(), NSKVals.UPGRADE_KIT)) {
								event.setCancelled(true);
								player.sendMessage("§cPlease place an upgrade kit here!");
							} else {
								player.sendMessage("tier "+event.getCursor().getItemMeta().getPersistentDataContainer()
										.get(NSKeys.getNSKey(NSKVals.UPGRADE_KIT), PersistentDataType.INTEGER));
							}
						}
						updateWool(event.getInventory().getItem(12), event.getCursor(), event.getInventory());
						break;
					case 16:
						if (Utility.itemStackExists(event.getCurrentItem())) {
							if (event.getCurrentItem().getType() == Material.GREEN_WOOL) {
								player.sendMessage("valid");
								ItemStack gear = event.getInventory().getItem(12);
								ItemMeta meta = gear.getItemMeta();
								if (meta.getAttributeModifiers() == null || meta.getAttributeModifiers().isEmpty()) {
									meta.setAttributeModifiers(Gear.GearStats.valueOf(gear.getType().name()).getMap());
								} else {
									Multimap<Attribute, AttributeModifier> multimap = meta.getAttributeModifiers();
									meta.setAttributeModifiers(null);
									for (Entry<Attribute, AttributeModifier> e : multimap.entries()) {
										if (!e.getValue().getName().equals("custom_attribute")) {
											meta.addAttributeModifier(e.getKey(), e.getValue());
										}
									}
								}
								String attrName = applier.addAttributes(meta, gear.getType());
								if (attrName == null) {
									event.setCancelled(true);
									player.sendMessage("There are no valid attributes for this gear!");
								}
								if (meta.hasLore()) {
									List<String> lore = meta.getLore();
									boolean flag = false;
									for (int i = 0; i < lore.size(); i++) {
										if (lore.get(i).contains("§fAttribute: ")) {
											flag = true;
											lore.set(i, "§fAttribute: "+attrName);
										}
									}
									if (!flag)
										lore.add("§fAttribute: "+attrName);
									meta.setLore(lore);
								} else {
									meta.setLore(Arrays.asList(new String[] {"§fAttribute: "+attrName}));
								}
								gear.setItemMeta(meta);
								
								if (event.getInventory().getItem(12).getAmount() == 1)
									event.getInventory().setItem(12, null);
								else
									event.getInventory().getItem(12).setAmount(event.getInventory().getItem(12).getAmount()-1);

								if (event.getInventory().getItem(14).getAmount() == 1)
									event.getInventory().setItem(14, null);
								else
									event.getInventory().getItem(14).setAmount(event.getInventory().getItem(14).getAmount()-1);
								event.getInventory().setItem(16, gear);
								event.setCancelled(true);
							} else if (event.getCurrentItem().getType() != Material.GRAY_WOOL &&
									event.getCurrentItem().getType() != Material.RED_WOOL) {
								event.setCancelled(true);
								event.getWhoClicked().setItemOnCursor(event.getCurrentItem());
								updateWool(event.getInventory().getItem(12), event.getInventory().getItem(14), event.getInventory());
							} else {
								event.setCancelled(true);
							}
						} else {
							event.setCancelled(true);
						}
						break;
					default:
						player.sendMessage("wha huh");
						event.setCancelled(true);
					}
				}
			} else if (hm.get(player) != null && hm.get(player).getInv() == event.getInventory() &&
					event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
				player.sendMessage("dirty");
				if (NSKeys.hasNSKey(event.getCurrentItem(), NSKVals.UPGRADE_KIT)) { // kit
					ItemStack item = event.getInventory().getItem(14);
					if (item == null || item.getType() == Material.AIR) {
						ItemStack currentItem = event.getCurrentItem();
						event.setCurrentItem(null);
						event.getInventory().setItem(14, currentItem);
						updateWool(event.getInventory().getItem(12), currentItem, event.getInventory());
					}
					event.setCancelled(true);
				} else if (Gear.getGearType(event.getCurrentItem().getType()) != -1) { // gear
					ItemStack item = event.getInventory().getItem(12);
					if (item == null || item.getType() == Material.AIR) {
						ItemStack currentItem = event.getCurrentItem();
						event.setCurrentItem(null);
						event.getInventory().setItem(12, currentItem);
						updateWool(currentItem, event.getInventory().getItem(14), event.getInventory());
					}
					event.setCancelled(true);
				} else {
					player.sendMessage("§cGear or upgrade kits only!");
					event.setCancelled(true);
				}
			}
		} else {
			System.out.println("but how");
		}
	}

	@EventHandler
	public void onReforgeAnvilInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			player.sendMessage("you closed an inv!");
			HashMap<Player, InventoryPair> hm = plugin.getInventoryMap();
			if (hm.get(player) != null && hm.get(player).getInv() == event.getInventory()) {
				player.sendMessage("you closed a stored thingy!");
				
				if (hm.get(player).getType() == InventoryPair.ATTRIBUTE_FORGE) {
					if (Utility.itemStackExists(event.getInventory().getItem(12))) {
						player.getWorld().dropItemNaturally(player.getLocation(), event.getInventory().getItem(12));
					}
					if (Utility.itemStackExists(event.getInventory().getItem(14))) {
						player.getWorld().dropItemNaturally(player.getLocation(), event.getInventory().getItem(14));
					}
				}
				
				hm.remove(player);
			}
		} else {
			System.out.println("but how");
		}
	}
	
}
