package utility;

import org.bukkit.attribute.Attribute;

import com.google.common.collect.ImmutableMultimap;

public class Augment {
	private final ImmutableMultimap<Attribute, ModifierPair> attrMap;
	private final String name;
	private final boolean[] applyArr;
	public Augment(ImmutableMultimap<Attribute, ModifierPair> attrMap, String name, boolean[] applyArr) {
		super();
		this.attrMap = attrMap;
		this.name = name;
		this.applyArr = applyArr;
	}
	public ImmutableMultimap<Attribute, ModifierPair> getAttrMap() {
		return attrMap;
	}
	public String getName() {
		return name;
	}
	public boolean[] getApplyArr() {
		return applyArr;
	}
}
