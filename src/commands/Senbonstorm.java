package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Senbonstorm implements CommandExecutor {

	@Override
	//gj sifting through the documentation
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if(sender instanceof Player) {
      		Player player = (Player)sender;
      		Vector playerdir = player.getLocation().getDirection();
    		int rand = (int)(Math.random()*15) + 7;
    		for(int i = 0; i < rand; i++) {
      			player.launchProjectile(Arrow.class, playerdir)
      				.setPickupStatus(PickupStatus.DISALLOWED);
    		}
    		return true;
    	} else {
    		System.out.print("oh noooooo...anyway");
    		//you are not a player!!!!
    		return false;
    	}
	}
}
