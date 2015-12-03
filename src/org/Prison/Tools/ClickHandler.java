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
						ToolAPI.givePickaxe(p);
						return;
					}
					if (p.getItemInHand().getItemMeta().getDisplayName().contains("Ancient Sword")){
						ToolAPI.giveSword(p);
						return;
					}
					String name = p.getItemInHand().getItemMeta().getDisplayName();
					if (name.contains("Ancient Boots") || name.contains("Ancient Chestplate") || name.contains("Ancient Leggings") || name.contains("Ancient Helmet")){
						ToolAPI.giveArmor(p);
						return;
					}
				}
			}
			p.sendMessage(ToolAPI.tag + "§cI can't identify that, give me an ancient tool.");
		}
		if (Files.config().getInt("EnchantID") == id){
			if (event.getClicker().getItemInHand() != null){
				ItemStack item = event.getClicker().getItemInHand();
				if (item.hasItemMeta()){
					ItemMeta itemm = item.getItemMeta();
					if (itemm.getLore().size() > 1){
					if (itemm.getLore().get(1).contains("Efficiency")){
						Enchanter.attempt(p);
						return;
					}
					}
				}
			}
			p.sendMessage(Enchanter.tag + "§cSorry but I can't enchant that, bring me an echantable tool.");
		}
	}
}
