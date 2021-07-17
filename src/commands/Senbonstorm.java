package commands;

import java.util.Arrays;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import data.NSKeys;

public class Senbonstorm implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(sender instanceof Player) {
      Player player = (Player)sender;
    }
    Vector playerdir = player.getLocation().getDirection();
    int rand = (int)(Math.random()*15) + 7;
    for(int i = 0; i < rand; i++) {
      player.launchProjectile(Arrow.class,
      playerdir).setPickupStatus(PickupStatus.DISALLOWED);
    }
	}
}
