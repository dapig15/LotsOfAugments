package data;

import org.bukkit.inventory.Inventory;

public class InventoryPair {
	
	private Inventory inv;
	private int type;
	
	public static final int ATTRIBUTE_FORGE = 0;
	
	public InventoryPair(Inventory inv, int type) {
		this.inv = inv;
		this.type = type;
	}

	public Inventory getInv() {
		return inv;
	}
	public int getType() {
		return type;
	}
	
}
