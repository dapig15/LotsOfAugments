package utility;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
	public Augment addAttributes(ItemMeta meta, Augment aug, EquipmentSlot slot) {
		for (Entry<Attribute, ModifierPair> entry : aug.getAttrMap().entries()) {
			if (entry.getValue().getVal() == 0) continue;
			meta.addAttributeModifier(entry.getKey(),
					new AttributeModifier(UUID.randomUUID(), "augment",
							entry.getValue().getVal(), entry.getValue().getOper(), slot));
		}
		return aug;
	}
	public Augment addAttributes(ItemMeta meta, Material m) {
		ArrayList<Augment> augs = plugin.getValidAugments(Gear.getGearType(m));
		if (augs.isEmpty())
			return null;
		if (augs.size() == 1)
			return addAttributes(meta, augs.get(0), Gear.getSlot(m));
		int totalWeight = 0;
		int existingId = -1, toDeleteInd = -1;
		if (meta.getPersistentDataContainer().has(NSKeys.getNSKey(NSKVals.AUGMENT_NAME),
				PersistentDataType.INTEGER)) {
			existingId = meta.getPersistentDataContainer().get(NSKeys.getNSKey(NSKVals.AUGMENT_NAME),
				PersistentDataType.INTEGER);
		}
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
		meta.getPersistentDataContainer().set(NSKeys.getNSKey(NSKVals.AUGMENT_NAME),
				PersistentDataType.INTEGER, chosenAug.getId());
		return addAttributes(meta, chosenAug, Gear.getSlot(m));
	}
}
