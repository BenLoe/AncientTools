package org.Prison.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.BenLoe.Blackmarket.Stats.Stats;
import net.md_5.bungee.api.ChatColor;

import org.Prison.Main.ParticleEffect;
import org.Prison.Main.Currency.CrystalAPI;
import org.Prison.Main.Currency.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;

@SuppressWarnings("deprecation")
public class ExtraDrops {

	public static void giveExtra(Player p, Location brokenBlock){
		List<String> types = new ArrayList<String>();
		types.add("Money");
		Random r = new Random();
		int percent = r.nextInt(100) + 1;
		if (percent <= 30){
			types.add("Pickaxe Shard");
		}
		if (percent <= 50){
			types.add("Haste");
		}
		if (percent <= 40){
			types.add("Mushroom");
			types.add("Crystals");
		}
		String type = types.get(r.nextInt(types.size()));
		String name = "";
		brokenBlock.getBlock().setType(Material.AIR);
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
		ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.DIAMOND_BLOCK, (byte)0), 0.3f, 0.3f, 0.3f, 0.05f, 100, brokenBlock.clone().add(0.5, 0, 0.5), 5);
		ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.EMERALD_BLOCK, (byte)0), 0.3f, 0.3f, 0.3f, 0.05f, 100, brokenBlock.clone().add(0.5, 0, 0.5), 5);
		ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.GOLD_BLOCK, (byte)0), 0.3f, 0.3f, 0.3f, 0.05f, 100, brokenBlock.clone().add(0.5, 0, 0.5), 5);
		switch(type){
		case "Money":{
			int amount = r.nextInt(200) + 200;
			MoneyAPI.addMoney(p, amount);
			name = ChatColor.GREEN + "" + amount + " Dollars";
		}
		break;
		case "Pickaxe Shard":{
			int amount = r.nextInt(5) + 1;
			name = ChatColor.GRAY + "" + amount + " Pickaxe Shards";
			Stats.getStats(p.getName()).addShards(amount);
		}
		break;
		case "Crystals":{
			int amount = r.nextInt(100) + 20;
			CrystalAPI.addCrystals(p, amount);
			name = ChatColor.AQUA + "" + amount + " Crystals";
		}
		break;
		case "Haste":{
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 5, 5));
			name = ChatColor.YELLOW + "Haste lvl 5 for 5 seconds";
		}
		break;
		case "Mushroom":{
			int amount = r.nextInt(3) + 1;
			name = ChatColor.BLUE + "" + amount + " Mushroom Soups";
			for (int i = 1; i <= amount; i++){
				p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
			}
		}
		}
		final Hologram h = HolographicDisplaysAPI.createHologram(Main.getPlugin(Main.class), brokenBlock.clone().add(0.5, 1.7, 0.5), name);
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
			public void run(){
				h.delete();
			}
		}, 40l);
	}
}
