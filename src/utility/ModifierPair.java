package utility;

import org.bukkit.attribute.AttributeModifier.Operation;

public class ModifierPair {
	private final double val;
	private final Operation oper;
	public ModifierPair(double val, Operation oper) {
		this.val = val;
		this.oper = oper;
	}
	public double getVal() {
		return val;
	}
	public Operation getOper() {
		return oper;
	}
}
