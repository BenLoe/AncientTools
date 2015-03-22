package org.Prison.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.Prison.Main.PlayerMode;
import org.Prison.Main.Letter.LetterType;
import org.Prison.Main.Traits.SmartTrait;
import org.PrisonMain.Achievement.AchievementAPI;
import org.PrisonMain.Achievement.Menu.AchievementMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolAPI {
	
	public static String tag = ChatColor.YELLOW + "[" + ChatColor.AQUA + "Identifier" + ChatColor.YELLOW + "]: ";

	public static void giveTools(Player p){
		Cooldown.checkCooldown(p, "Identify");
		if (Cooldown.hasCooldown(p, "Identify") && !PlayerMode.isInPlayerMode(p)){
			p.sendMessage(tag + ChatColor.RED + "Again already? Come back in " + Cooldown.getTimeLeft(p, "Identify") + ChatColor.RED + ".");
			Main.sendTellRaw(p, "{text: 'Although you can bribe me by clicking here.',color: red,hoverEvent: {action: show_text,value: 'Bribe the Identifier.'},clickEvent: {action: run_command, value: '/skipcooldown'}}");
			return;
		}
		p.getInventory().setItem(p.getInventory().getHeldItemSlot(), null);
		Cooldown.setCooldown(p, "Identify");
		int IntellectLevel = SmartTrait.getLevel(p);
		double RarePercent = 20 + (0.19 * IntellectLevel);
		double EpicPercent = 1.5 + (0.19 * IntellectLevel);
		double UltraPercent = 0.2 + (0.1 * IntellectLevel);
		
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
					p1.sendMessage(tag + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " got lucky and identified a " + c + name);
				}
			}
		}
		if (type == "Ultra"){
			AchievementAPI.completeAchievement(p, AchievementMenu.GETTING_LUCKY);
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
}
