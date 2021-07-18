package data;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;

import com.google.common.collect.ImmutableMultimap;

public class Gear {
	
	public enum GearStats {
		LEATHER_HELMET (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 1, Operation.ADD_NUMBER, EquipmentSlot.HEAD)
		}),
		CHAINMAIL_HELMET (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.HEAD)
		}),
		IRON_HELMET (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.HEAD)
		}),
		GOLDEN_HELMET (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.HEAD)
		}),
		DIAMOND_HELMET (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.HEAD),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.HEAD)
		}),
		NETHERITE_HELMET (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS,
				Attribute.GENERIC_KNOCKBACK_RESISTANCE
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.HEAD),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.HEAD),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 0.1, Operation.ADD_NUMBER, EquipmentSlot.HEAD)
		}),
		LEATHER_CHESTPLATE (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.CHEST)
		}),
		CHAINMAIL_CHESTPLATE (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 5, Operation.ADD_NUMBER, EquipmentSlot.CHEST)
		}),
		IRON_CHESTPLATE (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 6, Operation.ADD_NUMBER, EquipmentSlot.CHEST)
		}),
		GOLDEN_CHESTPLATE (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 5, Operation.ADD_NUMBER, EquipmentSlot.CHEST)
		}),
		DIAMOND_CHESTPLATE (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 8, Operation.ADD_NUMBER, EquipmentSlot.CHEST),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.CHEST)
		}),
		NETHERITE_CHESTPLATE (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS,
				Attribute.GENERIC_KNOCKBACK_RESISTANCE
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 8, Operation.ADD_NUMBER, EquipmentSlot.CHEST),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.CHEST),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 0.1, Operation.ADD_NUMBER, EquipmentSlot.CHEST)
		}),
		LEATHER_LEGGINGS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.LEGS)
		}),
		CHAINMAIL_LEGGINGS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 4, Operation.ADD_NUMBER, EquipmentSlot.LEGS)
		}),
		IRON_LEGGINGS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 5, Operation.ADD_NUMBER, EquipmentSlot.LEGS)
		}),
		GOLDEN_LEGGINGS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.LEGS)
		}),
		DIAMOND_LEGGINGS (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 6, Operation.ADD_NUMBER, EquipmentSlot.LEGS),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.LEGS)
		}),
		NETHERITE_LEGGINGS (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS,
				Attribute.GENERIC_KNOCKBACK_RESISTANCE
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 6, Operation.ADD_NUMBER, EquipmentSlot.LEGS),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.LEGS),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 0.1, Operation.ADD_NUMBER, EquipmentSlot.LEGS)
		}),
		LEATHER_BOOTS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		}),
		CHAINMAIL_BOOTS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		}),
		IRON_BOOTS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		}),
		GOLDEN_BOOTS (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		}),
		DIAMOND_BOOTS (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.FEET),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 2, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		}),
		NETHERITE_BOOTS (new Attribute[] {
				Attribute.GENERIC_ARMOR,
				Attribute.GENERIC_ARMOR_TOUGHNESS,
				Attribute.GENERIC_KNOCKBACK_RESISTANCE
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.FEET),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 3, Operation.ADD_NUMBER, EquipmentSlot.FEET),
				new AttributeModifier(UUID.randomUUID(), "base_armor", 0.1, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		}),
		ELYTRA (new Attribute[] {}, new AttributeModifier[] {}),
		WOODEN_SWORD (new Attribute[] {
				Attribute.GENERIC_ARMOR
		}, new AttributeModifier[] {
				new AttributeModifier(UUID.randomUUID(), "base_armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET)
		});
		
		// remember attribute[].length == attributemodifier[].length
		private final ImmutableMultimap<Attribute, AttributeModifier> multimap;
		private GearStats(Attribute[] attrs, AttributeModifier[] attrmods) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder
				= new ImmutableMultimap.Builder<Attribute, AttributeModifier>();
			for (int i = 0; i < attrmods.length; i++) {
				builder.put(attrs[i], attrmods[i]);
			}
			multimap = builder.build();
		}
		public ImmutableMultimap<Attribute, AttributeModifier> getMap() {
			return multimap;
		}
	}
	
	public static boolean isArmor(Material m) {
		return (
				m == Material.LEATHER_HELMET ||
				m == Material.LEATHER_CHESTPLATE ||
				m == Material.LEATHER_LEGGINGS ||
				m == Material.LEATHER_BOOTS ||
				m == Material.CHAINMAIL_HELMET ||
				m == Material.CHAINMAIL_CHESTPLATE ||
				m == Material.CHAINMAIL_LEGGINGS ||
				m == Material.CHAINMAIL_BOOTS ||
				m == Material.IRON_HELMET ||
				m == Material.IRON_CHESTPLATE ||
				m == Material.IRON_LEGGINGS ||
				m == Material.IRON_BOOTS ||
				m == Material.GOLDEN_HELMET ||
				m == Material.GOLDEN_CHESTPLATE ||
				m == Material.GOLDEN_LEGGINGS ||
				m == Material.GOLDEN_BOOTS ||
				m == Material.DIAMOND_HELMET ||
				m == Material.DIAMOND_CHESTPLATE ||
				m == Material.DIAMOND_LEGGINGS ||
				m == Material.DIAMOND_BOOTS ||
				m == Material.NETHERITE_HELMET ||
				m == Material.NETHERITE_CHESTPLATE ||
				m == Material.NETHERITE_LEGGINGS ||
				m == Material.NETHERITE_BOOTS
		);
	}
	public static boolean isSword(Material m) {
		return (m==Material.WOODEN_SWORD||m==Material.STONE_SWORD||
				m==Material.IRON_SWORD||m==Material.GOLDEN_SWORD||
				m==Material.DIAMOND_SWORD||m==Material.NETHERITE_SWORD);
	}
	public static boolean isAxe(Material m) {
		return (m==Material.WOODEN_AXE||m==Material.STONE_AXE||
				m==Material.IRON_AXE||m==Material.GOLDEN_AXE||
				m==Material.DIAMOND_AXE||m==Material.NETHERITE_AXE);
	}
	public static boolean isTool(Material m) {
		return (m==Material.WOODEN_PICKAXE||m==Material.STONE_PICKAXE||
				m==Material.IRON_PICKAXE||m==Material.GOLDEN_PICKAXE||
				m==Material.DIAMOND_PICKAXE||m==Material.NETHERITE_PICKAXE||
				m==Material.WOODEN_SHOVEL||m==Material.STONE_SHOVEL||
				m==Material.IRON_SHOVEL||m==Material.GOLDEN_SHOVEL||
				m==Material.DIAMOND_SHOVEL||m==Material.NETHERITE_SHOVEL||
				m==Material.WOODEN_HOE||m==Material.STONE_HOE||
				m==Material.IRON_HOE||m==Material.GOLDEN_HOE||
				m==Material.DIAMOND_HOE||m==Material.NETHERITE_HOE);
	}
	public static int getGearType(Material m) {
		if (isArmor(m))
			return 0;
		if (m == Material.ELYTRA)
			return 1;
		if (isSword(m))
			return 2;
		if (isAxe(m))
			return 3;
		if (m == Material.TRIDENT)
			return 4;
		if (m == Material.BOW)
			return 5;
		if (m == Material.CROSSBOW)
			return 6;
		if (m == Material.SHIELD)
			return 7;
		if (isTool(m))
			return 8;
		return -1;
	}
	public static int getTier(Material m) {
		String name = m.name();
		if (name.contains("LEATHER") || name.contains("WOODEN"))
			return 0;
		if (name.contains("CHAINMAIL") || name.contains("STONE"))
			return 1;
		if (name.contains("IRON"))
			return 2;
		if (name.contains("GOLDEN"))
			return 3;
		if (name.contains("DIAMOND"))
			return 4;
		if (name.contains("NETHERITE"))
			return 5;
		return 0;
	}
	public static EquipmentSlot getSlot(Material m) {
		if (
				m == Material.LEATHER_HELMET ||
				m == Material.CHAINMAIL_HELMET ||
				m == Material.IRON_HELMET ||
				m == Material.GOLDEN_HELMET ||
				m == Material.DIAMOND_HELMET ||
				m == Material.NETHERITE_HELMET
			) {
			return EquipmentSlot.HEAD;
		} else if (
				m == Material.LEATHER_CHESTPLATE ||
				m == Material.CHAINMAIL_CHESTPLATE ||
				m == Material.IRON_CHESTPLATE ||
				m == Material.GOLDEN_CHESTPLATE ||
				m == Material.DIAMOND_CHESTPLATE ||
				m == Material.NETHERITE_CHESTPLATE
			) {
			return EquipmentSlot.CHEST;
		} else if (
				m == Material.LEATHER_LEGGINGS ||
				m == Material.CHAINMAIL_LEGGINGS ||
				m == Material.IRON_LEGGINGS ||
				m == Material.GOLDEN_LEGGINGS ||
				m == Material.DIAMOND_LEGGINGS ||
				m == Material.NETHERITE_LEGGINGS
			) {
			return EquipmentSlot.LEGS;
		} else if (
				m == Material.LEATHER_BOOTS ||
				m == Material.CHAINMAIL_BOOTS ||
				m == Material.IRON_BOOTS ||
				m == Material.GOLDEN_BOOTS ||
				m == Material.DIAMOND_BOOTS ||
				m == Material.NETHERITE_BOOTS
			) {
			return EquipmentSlot.FEET;
		} else if (
				m == Material.SHIELD
			) {
			return EquipmentSlot.OFF_HAND;
		} else {
			return EquipmentSlot.HAND;
		}
	}
}
