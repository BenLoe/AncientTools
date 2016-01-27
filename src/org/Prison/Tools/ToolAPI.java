package org.Prison.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.BenLoe.quest.ActiveQuest;
import me.BenLoe.quest.NeededType;
import me.BenLoe.quest.QuestAPI;

import org.Prison.Main.PlayerMode;
import org.Prison.Main.Letter.LetterType;
import org.Prison.Main.Ranks.RankType;
import org.Prison.Main.Traits.SmartTrait;
import org.Prison.Main.mkremins.fanciful.FancyMessage;
import org.PrisonMain.Achievement.AchievementAPI;
import org.PrisonMain.Achievement.Menu.AchievementMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ToolAPI {
	
	public static String tag = ChatColor.YELLOW + "[" + ChatColor.AQUA + "Identifier" + ChatColor.YELLOW + "]: ";

	public static void givePickaxe(Player p){
		Cooldown.checkCooldown(p.getName(), "Identify");
		if (Cooldown.hasCooldown(p.getName(), "Identify") && !PlayerMode.isInPlayerMode(p)){
			p.sendMessage(tag + ChatColor.RED + "Again already? Come back in " + Cooldown.getTimeLeft(p.getName(), "Identify") + ChatColor.RED + ".");
			Main.sendTellRaw(p, "{text: 'Although you can bribe me by clicking here.',color: red,hoverEvent: {action: show_text,value: 'Bribe the Identifier.'},clickEvent: {action: run_command, value: '/skipcooldown'}}");
			return;
		}
		if (QuestAPI.hasAActive(p)){
			ActiveQuest aq = ActiveQuest.getActive(p);
			if (aq.getNeededType() == NeededType.IDENTIFY){
				QuestAPI.addProgress(p, 1);
			}
		}
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), null);
		RankType rank = RankType.getRank(p);
		int seconds = 120;
		if (rank != RankType.NONE){
			if (rank == RankType.VIP){
				seconds = 50;
			}else{
				seconds = 30;
			}
		}
		if (rank == RankType.NONE || rank == RankType.VIP || rank == RankType.ELITE){
		Cooldown.setCooldown(p.getName(), seconds, "Identify");
		}
		int IntellectLevel = SmartTrait.getLevel(p);
		double RarePercent = 20 + (0.19 * IntellectLevel);
		double EpicPercent = 1.1 + (0.19 * IntellectLevel);
		double UltraPercent = 0.2 + (0.05 * IntellectLevel);
		
		String type = "";
		type = "Normal";
		
		Random r = new Random();
		double random = 0 + (100 - 0) * r.nextDouble();
		if (random <= RarePercent){
			type = "Rare";
		}
		if (random <= EpicPercent){
			type = "Epic";
		}
		if (random <= UltraPercent){
			type = "Ultra";
		}
		int efficiency = (int) Math.round(((Files.config().getInt(type + ".MinEfficiency") + (Files.config().getInt(type + ".MaxEfficiency") - Files.config().getInt(type + ".MinEfficiency")) * Math.random()) + Math.round(0.2 * LetterType.getPlayerLetter(p).getInt())));
		int fortune = (int) Math.round(((Files.config().getInt(type + ".MinFortune") + (Files.config().getInt(type + ".MaxFortune") - Files.config().getInt(type + ".MinFortune")) * Math.random()) + Math.round(0.1 * LetterType.getPlayerLetter(p).getInt())));
		int unbreaking =(int) Math.round(((Files.config().getInt(type + ".MinUnbreaking") + (Files.config().getInt(type + ".MaxUnbreaking") - Files.config().getInt(type + ".MinUnbreaking")) * Math.random())));
		String ExtraDrops = String.valueOf(getRandomDouble(Files.config().getString(type + ".MinExtraDrops"), Files.config().getString(type + ".MaxExtraDrops")));
		String AncientChance = String.valueOf(getRandomDouble(Files.config().getString(type + ".MinAncient"), Files.config().getString(type + ".MaxAncient")));
		int Speed = (int) Math.round(((Files.config().getInt(type + ".MinSpeed") + (Files.config().getInt(type + ".MaxSpeed") - Files.config().getInt(type + ".MinSpeed")) * Math.random()) + Math.round(0.03 * LetterType.getPlayerLetter(p).getInt())));
		int Enchants = (int) Math.round(((Files.config().getInt(type + ".MinEnchants") + (Files.config().getInt(type + ".MaxEnchants") - Files.config().getInt(type + ".MinEnchants")) * Math.random()) + Math.round(0.1 * LetterType.getPlayerLetter(p).getInt())));
		String extrautil = String.valueOf(ExtraDrops);
		if (extrautil.length() == 3){
			extrautil = extrautil + "0";
			ExtraDrops = extrautil;
		}
		String ancientutil = String.valueOf(AncientChance);
		if (ancientutil.length() == 3){
			ancientutil = ancientutil + "0";
			AncientChance = ancientutil;
		}
		
		List<String> names = Files.config().getStringList(type + ".Names");
		String name = names.get(r.nextInt(names.size()));
		
		ChatColor c = null;
		if (type == "Normal"){
			c = ChatColor.GREEN;
		}
		if (type == "Rare"){
			c = ChatColor.YELLOW;
		}
		if (type == "Epic"){
			c = ChatColor.DARK_PURPLE;
		}
		if (type == "Ultra"){
			c = ChatColor.DARK_RED;
			AchievementAPI.completeAchievement(p, AchievementMenu.GETTING_LUCKY);
		}
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName(c + name);
		List<String> lore = new ArrayList<String>();
		lore.add("§9§lStatistics:");
		lore.add(ChatColor.GRAY + " Efficiency: " + ChatColor.GREEN + efficiency);
		lore.add(ChatColor.GRAY + " Fortune: " + ChatColor.GREEN + "" + fortune);
		lore.add(ChatColor.GRAY + " Unbreaking: " + ChatColor.GREEN + unbreaking);
		lore.add(" ");
		lore.add(ChatColor.GRAY + " Extra Drops: " + ChatColor.RED + String.valueOf(ExtraDrops) + "%");
		lore.add(ChatColor.GRAY + " Ancient Chance: " + ChatColor.RED + AncientChance + "%");
		lore.add(ChatColor.GRAY + " Speed: " + ChatColor.YELLOW + "+" + Speed);
		lore.add(ChatColor.GREEN + " Enchants Left: " + ChatColor.YELLOW + Enchants);
		itemm.setLore(lore);
		item.setItemMeta(itemm);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, efficiency);
		item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, fortune);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, unbreaking);
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
		p.updateInventory();
		p.sendMessage(tag + ChatColor.GREEN + "Seems you got a " + c + name + ChatColor.GREEN + " here, use it wisely.");
		if (type == "Epic" || type == "Ultra"){
			for (Player p1 : Bukkit.getOnlinePlayers()){
				if (p1.getWorld().getName().equals("PrisonMap") && p1.getName() != p.getName()){
					new FancyMessage(tag + "§b" + p.getName() + "§7 got lucky and identified a ")
					.then(c + name + "").color(c).tooltip(lore)
					.then("§7.").send(p1);				
					}
			}
		}
	}
	
	public static void giveSword(Player p){
		Cooldown.checkCooldown(p.getName(), "Identify");
		if (Cooldown.hasCooldown(p.getName(), "Identify") && !PlayerMode.isInPlayerMode(p)){
			p.sendMessage(tag + ChatColor.RED + "Again already? Come back in " + Cooldown.getTimeLeft(p.getName(), "Identify") + ChatColor.RED + ".");
			Main.sendTellRaw(p, "{text: 'Although you can bribe me by clicking here.',color: red,hoverEvent: {action: show_text,value: 'Bribe the Identifier.'},clickEvent: {action: run_command, value: '/skipcooldown'}}");
			return;
		}
		if (QuestAPI.hasAActive(p)){
			ActiveQuest aq = ActiveQuest.getActive(p);
			if (aq.getNeededType() == NeededType.IDENTIFY){
				QuestAPI.addProgress(p, 1);
			}
		}
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), null);
		RankType rank = RankType.getRank(p);
		int seconds = 120;
		if (rank != RankType.NONE){
			if (rank == RankType.VIP){
				seconds = 50;
			}else{
				seconds = 30;
			}
		}
		if (rank == RankType.NONE || rank == RankType.VIP || rank == RankType.ELITE){
		Cooldown.setCooldown(p.getName(), seconds, "Identify");
		}
		int IntellectLevel = SmartTrait.getLevel(p);
		double RarePercent = 20 + (0.19 * IntellectLevel);
		double EpicPercent = 1.1 + (0.19 * IntellectLevel);
		double UltraPercent = 0.2 + (0.05 * IntellectLevel);
		
		String type = "";
		type = "Normal";
		
		Random r = new Random();
		double random = 0 + (100 - 0) * r.nextDouble();
		if (random <= RarePercent){
			type = "Rare";
		}
		if (random <= EpicPercent){
			type = "Epic";
		}
		if (random <= UltraPercent){
			type = "Ultra";
		}
		int sharpness = (int) Math.round(((Files.config().getInt(type + ".MinSharpness") + (Files.config().getInt(type + ".MaxSharpness") - Files.config().getInt(type + ".MinSharpness")) * Math.random())));
		int Flame = (int) Math.round(((Files.config().getInt(type + ".MinFlameAspect") + (Files.config().getInt(type + ".MaxFlameAspect") - Files.config().getInt(type + ".MinFlameAspect")) * Math.random())));
		int unbreaking = (int) Math.round(((Files.config().getInt(type + ".MinUnbreakingSword") + (Files.config().getInt(type + ".MaxUnbreakingSword") - Files.config().getInt(type + ".MinUnbreakingSword")) * Math.random())));		
		List<String> names = Files.config().getStringList(type + ".NamesSwords");
		String name = names.get(r.nextInt(names.size()));
		
		ChatColor c = null;
		if (type == "Normal"){
			c = ChatColor.GREEN;
		}
		if (type == "Rare"){
			c = ChatColor.YELLOW;
		}
		if (type == "Epic"){
			c = ChatColor.DARK_PURPLE;
		}
		if (type == "Ultra"){
			c = ChatColor.DARK_RED;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName(c + name);
		List<String> lore = new ArrayList<String>();
		lore.add("§9§lStatistics:");
		lore.add(ChatColor.GRAY + " Sharpness: " + ChatColor.GREEN + sharpness);
		lore.add(ChatColor.GRAY + " Fire Aspect: " + ChatColor.GREEN + "" + Flame);
		lore.add(ChatColor.GRAY + " Unbreaking: " + ChatColor.GREEN + unbreaking);
		itemm.setLore(lore);
		item.setItemMeta(itemm);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, sharpness);
		item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Flame);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, unbreaking);
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
		p.updateInventory();
		p.sendMessage(tag + ChatColor.GREEN + "Seems you got a " + c + name + ChatColor.GREEN + " here, use it wisely.");
		if (type == "Epic" || type == "Ultra"){
			for (Player p1 : Bukkit.getOnlinePlayers()){
				if (p1.getWorld().getName().equals("PrisonMap") && p1.getName() != p.getName()){
					new FancyMessage(tag + "§b" + p.getName() + "§7 got lucky and identified a ")
					.then(c + name + "").color(c).tooltip(lore)
					.then("§7.").send(p1);		
				}
			}
		}
	}
	
	public static void giveArmor(Player p){
		Cooldown.checkCooldown(p.getName(), "Identify");
		if (Cooldown.hasCooldown(p.getName(), "Identify") && !PlayerMode.isInPlayerMode(p)){
			p.sendMessage(tag + ChatColor.RED + "Again already? Come back in " + Cooldown.getTimeLeft(p.getName(), "Identify") + ChatColor.RED + ".");
			Main.sendTellRaw(p, "{text: 'Although you can bribe me by clicking here.',color: red,hoverEvent: {action: show_text,value: 'Bribe the Identifier.'},clickEvent: {action: run_command, value: '/skipcooldown'}}");
			return;
		}
		if (QuestAPI.hasAActive(p)){
			ActiveQuest aq = ActiveQuest.getActive(p);
			if (aq.getNeededType() == NeededType.IDENTIFY){
				QuestAPI.addProgress(p, 1);
			}
		}
		Material counter = Material.AIR;
		if (p.getInventory().getItemInHand().getType().equals(Material.LEATHER_BOOTS)) counter = Material.IRON_BOOTS;
		if (p.getInventory().getItemInHand().getType().equals(Material.LEATHER_HELMET)) counter = Material.IRON_HELMET;
		if (p.getInventory().getItemInHand().getType().equals(Material.LEATHER_CHESTPLATE)) counter = Material.IRON_CHESTPLATE;
		if (p.getInventory().getItemInHand().getType().equals(Material.LEATHER_LEGGINGS)) counter = Material.IRON_LEGGINGS;
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), null);
		RankType rank = RankType.getRank(p);
		int seconds = 120;
		if (rank != RankType.NONE){
			if (rank == RankType.VIP){
				seconds = 50;
			}else{
				seconds = 30;
			}
		}
		if (rank == RankType.NONE || rank == RankType.VIP || rank == RankType.ELITE){
		Cooldown.setCooldown(p.getName(), seconds, "Identify");
		}
		int IntellectLevel = SmartTrait.getLevel(p);
		double RarePercent = 20 + (0.19 * IntellectLevel);
		double EpicPercent = 1.1 + (0.19 * IntellectLevel);
		double UltraPercent = 0.2 + (0.05 * IntellectLevel);
		
		String type = "";
		type = "Normal";
		
		Random r = new Random();
		double random = 0.0 + (100.0 - 0.0) * r.nextDouble();
		if (random <= RarePercent){
			type = "Rare";
		}
		if (random <= EpicPercent){
			type = "Epic";
		}
		if (random <= UltraPercent){
			type = "Ultra";
		}
		
		if (type == "Epic" || type == "Ultra"){
			if (counter == Material.IRON_HELMET) counter = Material.DIAMOND_HELMET;
			if (counter == Material.IRON_CHESTPLATE) counter = Material.DIAMOND_CHESTPLATE;
			if (counter == Material.IRON_LEGGINGS) counter = Material.DIAMOND_LEGGINGS;
			if (counter == Material.IRON_BOOTS) counter = Material.DIAMOND_BOOTS;
		}
		
		int protection = (int) Math.round(((Files.config().getInt(type + ".MinProtection") + (Files.config().getInt(type + ".MaxProtection") - Files.config().getInt(type + ".MinProtection")) * Math.random())));
		int fireprotect = (int) Math.round(((Files.config().getInt(type + ".MinFireProtect") + (Files.config().getInt(type + ".MaxFireProtect") - Files.config().getInt(type + ".MinFireProtect")) * Math.random())));
		int unbreaking = (int) Math.round(((Files.config().getInt(type + ".MinUnbreakingArmor") + (Files.config().getInt(type + ".MaxUnbreakingArmor") - Files.config().getInt(type + ".MinUnbreakingArmor")) * Math.random())));		
		List<String> names = Files.config().getStringList(type + ".NamesArmor");
		String name = names.get(r.nextInt(names.size()));
		
		ChatColor c = null;
		if (type == "Normal"){
			c = ChatColor.GREEN;
		}
		if (type == "Rare"){
			c = ChatColor.YELLOW;
		}
		if (type == "Epic"){
			c = ChatColor.DARK_PURPLE;
		}
		if (type == "Ultra"){
			c = ChatColor.DARK_RED;
		}
		ItemStack item = new ItemStack(counter);
		ItemMeta itemm = item.getItemMeta();
		itemm.setDisplayName(c + name);
		List<String> lore = new ArrayList<String>();
		lore.add("§9§lStatistics:");
		lore.add(ChatColor.GRAY + " Protection: " + ChatColor.GREEN + protection);
		lore.add(ChatColor.GRAY + " Fire Protection: " + ChatColor.GREEN + "" + fireprotect);
		lore.add(ChatColor.GRAY + " Unbreaking: " + ChatColor.GREEN + unbreaking);
		itemm.setLore(lore);
		item.setItemMeta(itemm);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protection);
		item.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, fireprotect);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, unbreaking);
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
		p.updateInventory();
		p.sendMessage(tag + ChatColor.GREEN + "Seems you got a " + c + name + ChatColor.GREEN + " here, use it wisely.");
		if (type == "Epic" || type == "Ultra"){
			for (Player p1 : Bukkit.getOnlinePlayers()){
				if (p1.getWorld().getName().equals("PrisonMap") && p1.getName() != p.getName()){
					new FancyMessage(tag + "§b" + p.getName() + "§7 got lucky and identified a ")
					.then(c + name + "").color(c).tooltip(lore)
					.then("§7.").send(p1);
					}
			}
		}
	}
	
	public static double getRandomDouble(String min, String max){
		int mainmin = Integer.parseInt((min).split(",")[0]);
		int mainmax = Integer.parseInt((max).split(",")[0]);
		int secmin = Integer.parseInt((min).split(",")[1]);
		int secmax = Integer.parseInt((max).split(",")[1]);
		
		double main = (int) Math.round(mainmin + (mainmax - mainmin) * Math.random());
		double sec = (int) Math.round((secmin + (secmax - secmin) * Math.random()));
		
		
		double random = main + (sec/100);
		return random;
	}
	
	@SuppressWarnings("deprecation")
	public static void giveAncientPicks(Player p, int amount){
		ItemStack ancient = new ItemStack(274, amount, (byte)0);
		ItemMeta ancientm = ancient.getItemMeta();
		ancientm.setDisplayName(ChatColor.YELLOW + "Ancient Pickaxe");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Give this to the Identifier to see what");
		lore.add(ChatColor.GRAY + "type of pickaxe lies within.");
		ancientm.setLore(lore);
		ancient.setItemMeta(ancientm);
		p.getInventory().addItem(ancient);
		p.updateInventory();
	}
	
	public static void giveArmor(Player p, int amount, String type){
		Material m = Material.AIR;
		if (type == "Boots") m = Material.LEATHER_BOOTS;
		if (type == "Chestplate") m = Material.LEATHER_CHESTPLATE;
		if (type == "Leggings") m = Material.LEATHER_LEGGINGS;
		if (type == "Helmet") m = Material.LEATHER_HELMET;
		
		ItemStack ancient = new ItemStack(m);
		ancient.setAmount(amount);
		LeatherArmorMeta ancientm = (LeatherArmorMeta) ancient.getItemMeta();
		ancientm.setDisplayName(ChatColor.YELLOW + "Ancient " + type);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Give this to the Identifier to see what");
		lore.add(ChatColor.GRAY + "type of armor lies within.");
		ancientm.setLore(lore);
		ancientm.setColor(Color.GRAY);
		ancient.setItemMeta(ancientm);
		p.getInventory().addItem(ancient);
		p.updateInventory();
		
	}
	
	@SuppressWarnings("deprecation")
	public static void giveAncientSwords(Player p, int amount){
		ItemStack ancient = new ItemStack(272, amount, (byte)0);
		ItemMeta ancientm = ancient.getItemMeta();
		ancientm.setDisplayName(ChatColor.YELLOW + "Ancient Sword");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Give this to the Identifier to see what");
		lore.add(ChatColor.GRAY + "type of sword lies within.");
		ancientm.setLore(lore);
		ancient.setItemMeta(ancientm);
		p.getInventory().addItem(ancient);
		p.updateInventory();
	}
}
