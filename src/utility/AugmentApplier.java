package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.google.common.collect.Multimap;

import data.Gear;
import data.NSKeys;
import data.NSKeys.NSKVals;
import main.MagicMonsters;

public class AugmentApplier {
	private final MagicMonsters plugin;
	private final Random rand = new Random();
	public AugmentApplier(MagicMonsters plugin) {
		this.plugin = plugin;
	}
	
	// CONFIRM THAT META IS ACTUALLY GEAR!
	public Augment addAttributes(ItemStack stack, Augment aug) {
		ItemMeta meta = stack.getItemMeta();
		Material m = stack.getType();
		EquipmentSlot slot = Gear.getSlot(m);
		if (meta.getAttributeModifiers() == null || meta.getAttributeModifiers().isEmpty()) {
			meta.setAttributeModifiers(Gear.GearStats.valueOf(m.name()).getMap());
		} else {
			Multimap<Attribute, AttributeModifier> multimap = meta.getAttributeModifiers();
			meta.setAttributeModifiers(null);
			for (Entry<Attribute, AttributeModifier> e : multimap.entries()) {
				if (!e.getValue().getName().equals("augment")) {
					meta.addAttributeModifier(e.getKey(), e.getValue());
				}
			}
		}
		for (Entry<Attribute, ModifierPair> entry : aug.getAttrMap().entries()) {
			if (entry.getValue().getVal() == 0) continue;
			meta.addAttributeModifier(entry.getKey(),
					new AttributeModifier(UUID.randomUUID(), "augment",
							entry.getValue().getVal(), entry.getValue().getOper(), slot));
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
		stack.setItemMeta(meta);
		return aug;
	}
	public Augment addAttributes(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		Material m = stack.getType();
		ArrayList<Augment> augs = plugin.getValidAugments(Gear.getGearType(m));
		if (augs.isEmpty())
			return null;
		if (augs.size() == 1)
			return addAttributes(stack, augs.get(0));
		int totalWeight = 0;
		String existingId = null; 
		if (meta.getPersistentDataContainer().has(NSKeys.getNSKey(NSKVals.AUGMENT_ID),
				PersistentDataType.STRING)) {
			existingId = meta.getPersistentDataContainer().get(NSKeys.getNSKey(NSKVals.AUGMENT_ID),
				PersistentDataType.STRING);
		}
		int toDeleteInd = -1;
		for (int i = 0; i < augs.size(); i++) {
			if (augs.get(i).getId() == existingId) {
				toDeleteInd = i;
			} else {
				totalWeight += augs.get(i).getWeight();
			}
		}
		if (toDeleteInd != -1)
			augs.remove(toDeleteInd);
		int random = rand.nextInt(totalWeight);
		int counter = 0;
		Augment chosenAug = augs.get(0);
		for (Augment aug : augs) {
			counter += aug.getWeight();
			if (random < counter) {
				chosenAug = aug;
				break;
			}
		}
		meta.getPersistentDataContainer().set(NSKeys.getNSKey(NSKVals.AUGMENT_ID),
				PersistentDataType.STRING, chosenAug.getId());
		return addAttributes(stack, chosenAug);
	}
}
