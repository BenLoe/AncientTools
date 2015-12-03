package org.Prison.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.Prison.Main.Currency.CrystalAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchanter {
	
	public static HashMap<String,ItemStack> levelCheck  = new HashMap<String,ItemStack>();
	public static String tag = ChatColor.YELLOW + "[" + ChatColor.AQUA + "Enchanter" + ChatColor.YELLOW + "]: ";
	
	public static void attempt(Player p){
		ItemStack item = p.getItemInHand();
		ToolStats t = ToolStats.getToolStats(item);
		if (t.getEnchants() > 0){
			p.sendMessage(tag + ChatColor.GREEN + "So you want this enchanted, what level? The higher the level the more chance of a good enchant.");
			p.sendMessage("§a§oClick one:");
			Main.sendTellRaw(p, "{text: 'Level 1: 700 Crystals',color: aqua,hoverEvent: {action: show_text,value: 'Enchant level 1.'},clickEvent: {action: run_command, value: '/enchantlvl 1'}}");
			Main.sendTellRaw(p, "{text: 'Level 2: 1500 Crystals',color: aqua,hoverEvent: {action: show_text,value: 'Enchant level 2.'},clickEvent: {action: run_command, value: '/enchantlvl 2'}}");
			Main.sendTellRaw(p, "{text: 'Level 3: 2500 Crystals',color: aqua,hoverEvent: {action: show_text,value: 'Enchant level 3.'},clickEvent: {action: run_command, value: '/enchantlvl 3'}}");
			levelCheck.put(p.getName(), p.getItemInHand());
		}else{
			p.sendMessage(tag + ChatColor.RED + "Sorry but I can't enchant that.");
		}
	}
	
	public static void enchant(Player p, int lvl){
		if (levelCheck.get(p.getName()).getItemMeta().getDisplayName() == p.getItemInHand().getItemMeta().getDisplayName()){
			if (lvl == 1){
				if (CrystalAPI.getCrystals(p) >= 700){
				CrystalAPI.removeCrystals(p, 700);
				}else{
					p.sendMessage(ChatColor.RED + "You don't have enough crystals, need more? You can buy them at our website.");
					return;
				}
			}
			if (lvl == 2){
				if (CrystalAPI.getCrystals(p) >= 1500){
					CrystalAPI.removeCrystals(p, 1500);
					}else{
						p.sendMessage(ChatColor.RED + "You don't have enough crystals, need more? You can buy them at our website.");
						return;
					}
			}
			if (lvl == 3){
				if (CrystalAPI.getCrystals(p) >= 2500){
					CrystalAPI.removeCrystals(p, 2500);
					}else{
						p.sendMessage(ChatColor.RED + "You don't have enough crystals, need more? You can buy them at our website.");
						return;
					}
			}
			ToolStats t = ToolStats.getToolStats(p.getItemInHand());
			int efficiency = t.getEfficiency();
			int fortune = t.getFortune();
			int unbreaking = t.getUnbreaking();
			double extradrops = t.getExtraDrops();
			double Ancient = t.getAncientChance();
			Random r = new Random();
			int percent = r.nextInt(100) + 1;
			int needed = 0;
			if (lvl == 1){
				needed = 65;
			}
			if (lvl == 2){
				needed = 30;
			}
			if (lvl == 3){
				needed = 10;
			}
			
			int type = r.nextInt(5) + 1;
			if  (!(percent <= needed)){
				String change = "";
				switch(type){
				case 1:{
					change = ChatColor.GRAY + "Efficiency: " + ChatColor.GREEN + t.getEfficiency() + ChatColor.AQUA + " >> " + ChatColor.GRAY + "Efficiency: " + ChatColor.GREEN + "" + (t.getEfficiency() + 1);
					efficiency = t.getEfficiency() + 1;
				}
				break;
				case 2:{
					change = ChatColor.GRAY + "Fortune: " + ChatColor.GREEN + t.getFortune() + ChatColor.AQUA + " >> " + ChatColor.GRAY + "Fortune: " + ChatColor.GREEN +"" + (t.getFortune()+ 1);
					fortune = t.getFortune() + 1;
				}
				break;
				case 3:{
					change = ChatColor.GRAY + "Unbreaking: " + ChatColor.GREEN + t.getUnbreaking() + ChatColor.AQUA + " >> " + ChatColor.GRAY + "Unbreaking: " + ChatColor.GREEN +"" + (t.getUnbreaking() + 1);
					unbreaking = t.getUnbreaking() + 1;
				}
				break;
				case 4:{
					change = ChatColor.GRAY + "ExtraDrops: " + ChatColor.RED + t.getExtraDrops() + "%" + ChatColor.AQUA + " >> " + ChatColor.GRAY + "ExtraDrops: " + ChatColor.RED + ""+ getNormalDouble(extradrops + 0.04) + "%";
					extradrops = extradrops + 0.04;
				}
				break;
				case 5:{
					change = ChatColor.GRAY + "AncientChance: " + ChatColor.RED + t.getAncientChance() + "%" + ChatColor.AQUA + " >> " + ChatColor.GRAY + "AncientChance: " + ChatColor.RED + ""+ getNormalDouble(Ancient + 0.04) + "%";
					Ancient = Ancient + 0.04;
				}
				}
				p.sendMessage(tag + ChatColor.GREEN + "The enchant was sucessful: ");
				p.sendMessage(change);
			}else{
				String change = "";
				outerloop:
				for (int i = 1; i < 40; i++){
				switch(type){
				case 1:{
					change = ChatColor.GRAY + "Efficiency: " + ChatColor.GREEN + t.getEfficiency() + ChatColor.AQUA + " >> " + ChatColor.GRAY + "Efficiency: " + ChatColor.GREEN +"" + (t.getEfficiency() - 1);
					if (t.getEfficiency() > 0){
						efficiency = t.getEfficiency() - 1;
						break;
					}
				}
				break;
				case 2:{
					change = ChatColor.GRAY + "Fortune: " + ChatColor.GREEN + t.getFortune() + ChatColor.AQUA + " >> " + ChatColor.GRAY + "Fortune: " + ChatColor.GREEN +"" + (t.getFortune() - 1);
					if (t.getFortune() > 0){
						fortune = t.getFortune() - 1;
						break;
					}
				}
				break;
				case 3:{
					change = ChatColor.GRAY + "Unbreaking: " + ChatColor.GREEN + t.getUnbreaking() + ChatColor.AQUA + " >> " + ChatColor.GRAY + "Unbreaking: " + ChatColor.GREEN +"" + (t.getUnbreaking() - 1);
					if (t.getUnbreaking() > 0){
					unbreaking = t.getUnbreaking() - 1;
					break;
					}
				}
				break;
				case 4:{
					change = ChatColor.GRAY + "ExtraDrops: " + ChatColor.RED + getNormalDouble(t.getExtraDrops()) + "%" + ChatColor.AQUA + " >> " + ChatColor.GRAY + "ExtraDrops: " + ChatColor.RED + "" + getNormalDouble(extradrops + -0.04) + "%";
					extradrops = extradrops + -0.04;
					break outerloop;
				}
				case 5:{
					change = ChatColor.GRAY + "AncientChance: " + ChatColor.RED + getNormalDouble(t.getAncientChance()) + "%" + ChatColor.AQUA + " >> " + ChatColor.GRAY + "AncientChance: " + ChatColor.RED + ""  + getNormalDouble(Ancient + -0.04) + "%";
					Ancient = Ancient + -0.04;
					break outerloop;
				}
				}
				}
				p.sendMessage(tag + ChatColor.RED + "The enchant failed: ");
				p.sendMessage(change);
			}
			ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName(levelCheck.get(p.getName()).getItemMeta().getDisplayName());
			List<String> lore = new ArrayList<String>();
			lore.add("§9§lStatistics:");
			lore.add(ChatColor.GRAY + " Efficiency: " + ChatColor.GREEN + efficiency);
			lore.add(ChatColor.GRAY + " Fortune: " + ChatColor.GREEN + "" + fortune);
			lore.add(ChatColor.GRAY + " Unbreaking: " + ChatColor.GREEN + unbreaking);
			lore.add(" ");
			lore.add(ChatColor.GRAY + " Extra Drops: " + ChatColor.RED + getNormalDouble(extradrops) + "%");
			lore.add(ChatColor.GRAY + " Ancient Chance: " + ChatColor.RED + getNormalDouble(Ancient) + "%");
			lore.add(ChatColor.GRAY + " Speed: " + ChatColor.YELLOW + "+" + (int) t.getSpeed());
			lore.add(ChatColor.GREEN + " Enchants Left: " + ChatColor.YELLOW + (t.getEnchants() - 1));
			itemm.setLore(lore);
			itemm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(itemm);
			item.setDurability(p.getItemInHand().getDurability());
			item.addUnsafeEnchantment(Enchantment.DIG_SPEED, efficiency);
			item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, fortune);
			item.addUnsafeEnchantment(Enchantment.DURABILITY, unbreaking);
			p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
			p.updateInventory();
			levelCheck.remove(p.getName());
		}else{
			p.sendMessage(tag + ChatColor.RED + "You are no longer holding the item you wish to enchant.");
		}
	}
	
	public static String getNormalDouble(double d){
		String s = "";
		for (int i = 0; i < 4 ; i++){
			if ((String.valueOf(d).length()) <= i){
				break;
			}
			String number = String.valueOf(String.valueOf(d).charAt(i));
			s += number;
		}
		if (s.length() == 3){
			s = s + "0";
		}
		return s;
	}
}
