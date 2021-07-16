
package data;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class GrabBag implements Listener {
	
	@EventHandler
	public void onGrabBagUse(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
	}
	
}
