package utility;

import org.bukkit.attribute.Attribute;

import com.google.common.collect.ImmutableMultimap;

public class Augment {
	private final ImmutableMultimap<Attribute, ModifierPair> attrMap;
	private final String name, desc;
	private final boolean[] applyArr;
	private final int weight, id;
	public Augment(ImmutableMultimap<Attribute, ModifierPair> attrMap, String name, String desc, boolean[] applyArr, int weight, int id) {
		super();
		this.attrMap = attrMap;
		this.name = name;
		this.desc = desc;
		this.applyArr = applyArr;
		this.weight = weight;
		this.id = id;
	}
	public ImmutableMultimap<Attribute, ModifierPair> getAttrMap() {
		return attrMap;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public boolean[] getApplyArr() {
		return applyArr;
	}
	public int getWeight() {
		return weight;
	}
	public int getId() {
		return id;
	}
}
