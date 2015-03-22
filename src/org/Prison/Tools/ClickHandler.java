package org.Prison.Tools;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickHandler {

	public static void click(Player p, NPCRightClickEvent event){
		int id = event.getNPC().getId();
		if (Files.config().getInt("IdentifyID") == id){
			if (p.getInventory().getItemInHand() != null){
				if (p.getInventory().getItemInHand().hasItemMeta()){
					if (p.getItemInHand().getItemMeta().getDisplayName().contains("Ancient Pickaxe")){
						ToolAPI.giveTools(p);
					}
				}
			}
		}
		if (Files.config().getInt("EnchantID") == id){
			if (event.getClicker().getItemInHand() != null){
				ItemStack item = event.getClicker().getItemInHand();
				if (item.hasItemMeta()){
					ItemMeta itemm = item.getItemMeta();
					if (itemm.getLore().size() > 1){
					if (itemm.getLore().get(1).contains("Efficiency")){
						Enchanter.attempt(p);
					}
					}
				}
			}
		}
	}
}
