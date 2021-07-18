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

import data.Gear;
import main.MagicMonsters;

public class AugmentApplier {
	private final MagicMonsters plugin;
	private final Random rand = new Random();
	public AugmentApplier(MagicMonsters plugin) {
		this.plugin = plugin;
	}
	
	// CONFIRM THAT META IS ACTUALLY GEAR!
	public String addAttributes(ItemMeta meta, Augment aug, EquipmentSlot slot) {
		for (Entry<Attribute, ModifierPair> entry : aug.getAttrMap().entries()) {
			if (entry.getValue().getVal() == 0) continue;
			meta.addAttributeModifier(entry.getKey(),
					new AttributeModifier(UUID.randomUUID(), "augment",
							entry.getValue().getVal(), entry.getValue().getOper(), slot));
		}
		return aug.getName();
	}
	public String addAttributes(ItemMeta meta, Material m) {
		ArrayList<Augment> augs = plugin.getValidAugments(Gear.getGearType(m));
		if (augs.isEmpty())
			return null;
		return addAttributes(meta, augs.get(rand.nextInt(augs.size())), Gear.getSlot(m));
	}
}
