package blocks;

import java.util.Random;
import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

public enum CustomAttribute {
	
	PHENOMENAL (new double[] {0.5, 5, 0.3, 0.25, 0.25, 0.5, 10, 0.2},
				new byte[]   {2, 0, 2, 2, 2, 2, 0, 2},
				"§d§lPHENOMENAL"),
	STUBBORN (new double[] {0, 0, 0, 0, 0.1, 0, 0, 0},
			new byte[]   {0, 0, 0, 0, 0, 0, 0, 0},
			"§a§lSTUBBORN"),
	STEADY (new double[] {0, 0, 0, 0, 0.2, 0, 0, 0},
			new byte[]   {0, 0, 0, 0, 0, 0, 0, 0},
			"§9§lSTEADY"),
	STALWART (new double[] {0, 0, 0, 0, 0.3, 0, 0, 0},
			new byte[]   {0, 0, 0, 0, 0, 0, 0, 0},
			"§5§lSTALWART"),
	LIGHT (new double[] {-0.25, 0, 0, 0, 0, 0, 0, 0.25},
			new byte[]   {2, 0, 0, 0, 0, 0, 0, 2},
			"§a§lLIGHT");
	
	private final double[] doubleArr;
	private final byte[] operation;
	private final String name;
	CustomAttribute(double[] doubleArr, byte[] operation, String name) {
		this.doubleArr = doubleArr;
		this.operation = operation;
		this.name = name;
	}
	
	// REMEMBER THIS ORDER
	private static final Attribute[] attributeArr = new Attribute[] {
			Attribute.GENERIC_ARMOR,
			Attribute.GENERIC_ARMOR_TOUGHNESS,
			Attribute.GENERIC_ATTACK_DAMAGE,
			Attribute.GENERIC_ATTACK_SPEED,
			Attribute.GENERIC_KNOCKBACK_RESISTANCE,
			Attribute.GENERIC_LUCK,
			Attribute.GENERIC_MAX_HEALTH,
			Attribute.GENERIC_MOVEMENT_SPEED
	};
	private static final Operation[] operations = new Operation[] {
			Operation.ADD_NUMBER,
			Operation.ADD_SCALAR,
			Operation.MULTIPLY_SCALAR_1
	};
	private static final CustomAttribute[] values = CustomAttribute.values();
	private static final Random random = new Random(); 
	public static String addAttributes(ItemMeta meta, CustomAttribute attr, EquipmentSlot slot) {
		for (int i = 0; i < attributeArr.length; i++) {
			if (attr.doubleArr[i] == 0) continue;
			meta.addAttributeModifier(attributeArr[i],
					new AttributeModifier(UUID.randomUUID(), "custom_attribute",
							attr.doubleArr[i], operations[attr.operation[i]], slot));
		}
		return attr.name;
	}
	public static String addAttributes(ItemMeta meta, EquipmentSlot slot) {
		return addAttributes(meta, values[random.nextInt(values.length)], slot);
	}
	public static String addNewAttribute(ItemMeta meta, CustomAttribute attr, EquipmentSlot slot) {
		CustomAttribute newAttr = values[random.nextInt(values.length)];
		while (newAttr.name.equals(attr.name)) {
			newAttr = values[random.nextInt(values.length)];
		}
		return addAttributes(meta, values[random.nextInt(values.length)], slot);
	}
	
}
