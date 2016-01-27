package org.Prison.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.md_5.bungee.api.ChatColor;

import org.Prison.Lucky.Game;
import org.Prison.Main.Traits.SpeedTrait;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;


public class Events implements Listener{
	public static Main plugin;
	public Events(Main instance){
		plugin = instance;
	}
	
	@EventHandler
	public void NpcClickEvent(NPCRightClickEvent event){
		ClickHandler.click(event.getClicker(), event);
	}
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void breakBlock(final BlockBreakEvent event){
		final Player p = event.getPlayer();
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
			public void run(){
				if (p.getItemInHand() != null && event.getBlock().getType() == Material.AIR){
					ItemStack item = p.getItemInHand();
					if (item.hasItemMeta()){
						ItemMeta itemm = item.getItemMeta();
						if (itemm.hasLore() && itemm.getLore().size() > 1){
						if (itemm.getLore().get(1).contains("Efficiency")){
							ToolStats t = ToolStats.getToolStats(item);
							Random r = new Random();
							double percent = 0 + (100 - 0) * r.nextDouble();
							double percent1 = 0 + (100 - 0) * r.nextDouble();
							if (percent <= t.getAncientChance() && !event.isCancelled()){
								int util = r.nextInt(10) + 1;
								switch(util){
								case 1:
								case 2:
								case 3:
								case 4:
								case 5:
									ToolAPI.giveAncientPicks(p, 1);
									p.sendMessage(ChatColor.GREEN + "Found: " + ChatColor.YELLOW + "Ancient Pickaxe");
									break;
								case 6:
									ToolAPI.giveAncientSwords(p, 1);
									p.sendMessage(ChatColor.GREEN + "Found: " + ChatColor.YELLOW + "Ancient Sword");
									break;
								case 7:
									ToolAPI.giveArmor(p, 1, "Boots");
									p.sendMessage(ChatColor.GREEN + "Found: " + ChatColor.YELLOW + "Ancient Boots");
									break;
								case 8:
									ToolAPI.giveArmor(p, 1, "Leggings");
									p.sendMessage(ChatColor.GREEN + "Found: " + ChatColor.YELLOW + "Ancient Leggings");
									break;
								case 9:
									ToolAPI.giveArmor(p, 1, "Chestplate");
									p.sendMessage(ChatColor.GREEN + "Found: " + ChatColor.YELLOW + "Ancient Chestplate");
									break;
								case 10:
									ToolAPI.giveArmor(p, 1, "Helmet");
									p.sendMessage(ChatColor.GREEN + "Found: " + ChatColor.YELLOW + "Ancient Helmet");
									break;
								}
							}
							if (percent1 <= t.getExtraDrops() && !event.isCancelled()){
								ExtraDrops.giveExtra(p, event.getBlock().getLocation());
							}
						}
						}
					}
				}
			}
		}, 1l);
		if (p.getItemInHand() != null && event.getBlock().getType() != Material.AIR){
			ItemStack item = p.getItemInHand();
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasDisplayName()){
				if (itemm.getDisplayName().contains("Ancient")){
					event.setCancelled(true);
					p.sendMessage(ChatColor.YELLOW + "Get this tool identified by the Identifier: §9/warp Identify§e.");
					ItemStack item1 = p.getInventory().getItemInHand();
					item1.setDurability((short)0);
					p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item1);
					p.updateInventory();
				}
				}
			}
		}
	}
	@EventHandler
	public void SlotChange(PlayerItemHeldEvent event){
		Player p = event.getPlayer();
		boolean oldis = false;
		boolean newis = false;
		ToolStats old = null;
		ToolStats newt = null;
		if (!Game.playerInGame(p) && !p.getWorld().getName().equals("Build") && !p.getWorld().getName().equals("PVP")){
		if (p.getInventory().getItem(event.getNewSlot()) != null){
			ItemStack item = p.getInventory().getItem(event.getNewSlot());
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.hasLore() && itemm.getLore().size() > 2){
					if (itemm.getLore().get(1).contains("Efficiency")){
					newt = ToolStats.getToolStats(item);
						newis = true;
					}
				}
			}
		}
		if (p.getInventory().getItem(event.getPreviousSlot()) != null){
			ItemStack item = p.getInventory().getItem(event.getPreviousSlot());
			if (item.hasItemMeta()){
				ItemMeta itemm = item.getItemMeta();
				if (itemm.hasLore() && itemm.getLore().size() > 2){
					if (itemm.getLore().get(1).contains("Efficiency")){
					old = ToolStats.getToolStats(item);
					oldis = true;
				}
				}
			}
		}
		
		if (oldis && !newis){
			SpeedTrait.setCorrectSpeed(p);
			return;
		}
		if (newis && !oldis){
			float add = newt.getSpeed() * 0.05f;
			float currentspeed = p.getWalkSpeed();
			float newspeed = currentspeed + add;
			if (newspeed > 1.0f) newspeed = 1.0f;
			p.setWalkSpeed(newspeed);
			return;
		}
		if (newis && oldis && old.getSpeed() != newt.getSpeed()){
			float add = newt.getSpeed() * 0.05f;
			float currentspeed = p.getWalkSpeed();
			float newspeed = currentspeed + add;
			if (newspeed > 1.0f) newspeed = 1.0f;
			p.setWalkSpeed(newspeed);
			return;
		}
		}
	}
}
