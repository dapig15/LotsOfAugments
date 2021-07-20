package utility;

import org.bukkit.attribute.Attribute;

import com.google.common.collect.ImmutableMultimap;

public class Augment {
	private final ImmutableMultimap<Attribute, ModifierPair> attrMap;
	private final String id, name, desc;
	private final boolean[] applyArr;
	private final int weight;
	public Augment(ImmutableMultimap<Attribute, ModifierPair> attrMap, String id, String name, String desc, boolean[] applyArr, int weight) {
		super();
		this.attrMap = attrMap;
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.applyArr = applyArr;
		this.weight = weight;
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
	public String getId() {
		return id;
	}
}
