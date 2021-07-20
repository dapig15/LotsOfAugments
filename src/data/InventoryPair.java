package data;

import org.bukkit.inventory.Inventory;

public class InventoryPair {
	
	private Inventory inv;
	private int type, extraData = -1;
	
	public static final int ATTRIBUTE_FORGE = 0;
	public static final int AUGMENT_LIST = 1; 
	
	public InventoryPair(Inventory inv, int type) {
		this.inv = inv;
		this.type = type;
	}
	public InventoryPair(Inventory inv, int type, int extraData) {
		this.inv = inv;
		this.type = type;
		this.extraData = extraData;
	}

	public Inventory getInv() {
		return inv;
	}
	public int getType() {
		return type;
	}
	public int getExtraData() {
		return extraData;
	}
	public void setExtraData(int i) {
		extraData = i;
	}
	
}
