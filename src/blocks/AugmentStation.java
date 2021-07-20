
package blocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlastFurnace;
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
import utility.Augment;
import utility.AugmentApplier;
import utility.Utility;

public class AugmentStation implements Listener {
	
	// TODO REMEMBER THAT MOST OF THESE LISTENERS SHOULD GO INTO THEIR OWN CLASS
	// TODO IF YOU END UP ADDING MORE BLOCKS LIKE THIS
	
	private final MagicMonsters plugin;
	private final AugmentApplier applier;
	public AugmentStation(MagicMonsters plugin, AugmentApplier applier) {
		this.plugin = plugin;
		this.applier = applier;
	}
	
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
	
	public static boolean isAttributeForge(Block b) {
		if (b.getBlockData().getMaterial() == Material.BLAST_FURNACE) {
			BlastFurnace s = (BlastFurnace) b.getState();
			return (s.getPersistentDataContainer().has(
					NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE), PersistentDataType.INTEGER));
		}
		return false;
	}
	
	@EventHandler
	public void onReforgeAnvilPlace(BlockPlaceEvent event) {
		if (NSKeys.hasNSKey(event.getItemInHand(), NSKVals.ATTRIBUTE_FORGE)) {
			event.getPlayer().sendMessage("woooo");
			BlastFurnace s = (BlastFurnace) event.getBlockPlaced().getState();
			s.getPersistentDataContainer().set(NSKeys.getNSKey(NSKVals.ATTRIBUTE_FORGE),
					PersistentDataType.INTEGER, 1);
			s.update();
		}
	}
	
	@EventHandler
	public void onReforgeAnvilMine(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (isAttributeForge(event.getBlock())) {
			event.setDropItems(false);
			player.getWorld().dropItemNaturally(event.getBlock().getLocation(),
					createAttributeForge());
		}
	}

	@EventHandler
	public void onUseReforgeAnvil(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block clickedBlock = event.getClickedBlock();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (isAttributeForge(clickedBlock)) {
				event.setCancelled(true);
				Inventory inv = Bukkit.createInventory(
						player, 27, "§4Augmentation Station");
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
						ItemStack forgeGlass = new ItemStack(Material.LIME_STAINED_GLASS);
						ItemMeta meta = forgeGlass.getItemMeta();
						meta.setDisplayName("§a§lFORGE!");
						forgeGlass.setItemMeta(meta);
						inv.setItem(16, forgeGlass);
					} else if (i != 12 && i != 14) {
						inv.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
					}
				}
				player.openInventory(inv);
				plugin.getInventoryMap().put(event.getPlayer(),
						new InventoryPair(inv, InventoryPair.ATTRIBUTE_FORGE));
			}
		}
	}

	@EventHandler
	public void onReforgeAnvilInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			HashMap<Player, InventoryPair> hm = plugin.getInventoryMap();
			if (hm.get(player) != null && hm.get(player).getInv() == event.getClickedInventory()) {
				if (hm.get(player).getType() == InventoryPair.ATTRIBUTE_FORGE) {
					if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
						ItemStack forgeGlass = new ItemStack(Material.LIME_STAINED_GLASS);
						ItemMeta meta = forgeGlass.getItemMeta();
						meta.setDisplayName("§a§lFORGE!");
						forgeGlass.setItemMeta(meta);
						if (event.getCursor().isSimilar(forgeGlass)) {
							event.setCancelled(true);
							return;
						}
					}
					switch (event.getRawSlot()) {
					case 12:
						if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
							if (Gear.getGearType(event.getCursor().getType()) == -1) {
								event.setCancelled(true);
								player.sendMessage("§cPlease place gear here!");
							}
						}
						break;
					case 14:
						if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
							if (!NSKeys.hasNSKey(event.getCursor(), NSKVals.UPGRADE_KIT)) {
								event.setCancelled(true);
								player.sendMessage("§cPlease place an upgrade kit here!");
							}
						}
						break;
					case 16:
						if (Utility.itemStackExists(event.getCurrentItem())) {
							if (event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS) {
								ItemStack gear = event.getInventory().getItem(12),
										kit = event.getInventory().getItem(14);
								if (!Utility.itemStackExists(gear) || !Utility.itemStackExists(kit)) {
									player.sendMessage("§cPlace gear and an upgrade kit!");
									event.setCancelled(true);
									return;
								}
								int kitData = kit.getItemMeta()
										.getPersistentDataContainer()
										.get(NSKeys.getNSKey(NSKVals.UPGRADE_KIT), PersistentDataType.INTEGER);
								if (Gear.getTier(gear.getType()) != kitData%10 ||
										Gear.getGearType(gear.getType()) != kitData/10) {
									player.sendMessage("§cGear and kit mismatch...");
									event.setCancelled(true);
									return;
								}
								ItemMeta meta = gear.getItemMeta();
								if (meta.getAttributeModifiers() == null || meta.getAttributeModifiers().isEmpty()) {
									meta.setAttributeModifiers(Gear.GearStats.valueOf(gear.getType().name()).getMap());
								} else {
									Multimap<Attribute, AttributeModifier> multimap = meta.getAttributeModifiers();
									meta.setAttributeModifiers(null);
									for (Entry<Attribute, AttributeModifier> e : multimap.entries()) {
										if (!e.getValue().getName().equals("augment")) {
											meta.addAttributeModifier(e.getKey(), e.getValue());
										}
									}
								}
								Augment aug = applier.addAttributes(meta, gear.getType());
								if (aug == null) {
									event.setCancelled(true);
									player.sendMessage("§c§lThere are no valid attributes for this gear!");
									return;
								}
								String attrName = aug.getName();
								if (meta.hasLore()) {
									List<String> lore = meta.getLore();
									boolean flag = false;
									for (int i = 0; i < lore.size(); i++) {
										if (lore.get(i).contains("§fTrait: ")) {
											flag = true;
											lore.set(i, "§fTrait: "+attrName);
											if (i+1 < lore.size()) {
												lore.set(i+1, aug.getDesc());
											} else {
												lore.add(aug.getDesc());
											}
											break;
										}
									}
									if (!flag) {
										lore.add("§fTrait: "+attrName);
										lore.add(aug.getDesc());
									}
									meta.setLore(lore);
								} else {
									meta.setLore(Arrays.asList(new String[] {
											"§fTrait: "+attrName,
											aug.getDesc()
									}));
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
							} else {
								event.setCancelled(true);
								event.getWhoClicked().setItemOnCursor(event.getCurrentItem());
								ItemStack forgeGlass = new ItemStack(Material.LIME_STAINED_GLASS);
								ItemMeta meta = forgeGlass.getItemMeta();
								meta.setDisplayName("§a§lFORGE!");
								forgeGlass.setItemMeta(meta);
								event.getInventory().setItem(16, forgeGlass);
							}
						} else {
							player.sendMessage("how");
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
				if (NSKeys.hasNSKey(event.getCurrentItem(), NSKVals.UPGRADE_KIT)) { // kit
					ItemStack item = event.getInventory().getItem(14);
					if (item == null || item.getType() == Material.AIR) {
						ItemStack currentItem = event.getCurrentItem();
						event.setCurrentItem(null);
						event.getInventory().setItem(14, currentItem);
					}
					event.setCancelled(true);
				} else if (Gear.getGearType(event.getCurrentItem().getType()) != -1) { // gear
					ItemStack item = event.getInventory().getItem(12);
					if (item == null || item.getType() == Material.AIR) {
						ItemStack currentItem = event.getCurrentItem();
						event.setCurrentItem(null);
						event.getInventory().setItem(12, currentItem);
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
			HashMap<Player, InventoryPair> hm = plugin.getInventoryMap();
			if (hm.get(player) != null && hm.get(player).getInv() == event.getInventory()) {
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
