package org.Prison.Tools;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.Prison.Main.Currency.CrystalAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	Events events = new Events(this);
	Files files = new Files(this);
	public static HashMap<String,Integer> areYouSure = new HashMap<String,Integer>();

	public void onEnable(){
		getServer().getPluginManager().registerEvents(events, this);
		saveDefaultConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd,
			String Label, String[] args){
		if (sender instanceof Player){
			Player p = (Player) sender;
			
			if (Label.equalsIgnoreCase("enchantlvl")){
				if (!Enchanter.levelCheck.containsKey(p.getName())){
					p.sendMessage(ChatColor.RED + "Cannot do this at the moment.");					
					return true;
				}
				if (args.length == 0 || args.length > 1){
					p.sendMessage(ChatColor.RED + "Cannot do this at the moment.");	
				}else{
					int lvl = 0;
					try{
						lvl = Integer.parseInt(args[0]);
					}catch(Exception e){
						p.sendMessage(ChatColor.RED + "Cannot do this at the moment.");	
						return true;
					}
					if (lvl > 0 && lvl < 4){
						Enchanter.enchant(p, lvl);
						return true;
					}
					p.sendMessage(ChatColor.RED + "Cannot do this at the moment.");	
				}
			}
			
			if (Label.equalsIgnoreCase("SkipCooldown")){
				if (Cooldown.hasCooldown(p.getName(), "Identify")){
					Cooldown.checkCooldown(p.getName(), "Identify");
					if (Cooldown.hasCooldown(p.getName(), "Identify")){
					int minutes = Cooldown.getMinutes("Identify", p.getName());
					int crystals = (int) Math.round(minutes + minutes * 0.48);
					areYouSure.put(p.getName(), crystals);
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("  ");
					p.sendMessage("§f§lAre you sure?");
					p.sendMessage(ChatColor.GRAY + "Cost: " + ChatColor.AQUA + crystals + " Crystals");
					sendTellRaw(p, "{text: 'Yes',color: green,italic: true,hoverEvent: { action: show_text, value: 'Bribe the Identifier'},clickEvent: {action: run_command,value: '/yesbribe'}}");
					sendTellRaw(p, "{text: 'No',color: red,italic: true,hoverEvent: { action: show_text, value: 'Cancel Purchase'},clickEvent: {action: run_command,value: '/nobribe'}}");
					}else{
						p.sendMessage("This is a utility command.");
					}
				}else{
					p.sendMessage("This is a utility command.");
				}
			}
			
			if (Label.equalsIgnoreCase("yesbribe")){
				if (areYouSure.containsKey(p.getName())){
					int crystals = areYouSure.get(p.getName());
					if (CrystalAPI.getCrystals(p) >= crystals){
						CrystalAPI.removeCrystals(p, crystals);
						p.sendMessage(ToolAPI.tag + ChatColor.AQUA + "Well then, I guess you can identify another tool whenever you want.");
						Cooldown.resetCooldown("Identify", p.getName());
						areYouSure.remove(p.getName());
					}else{
						p.sendMessage(ChatColor.RED + "You don't have enough crystals, need more? You can buy them at our website.");
						areYouSure.remove(p.getName());
						return true;
					}
				}else{
					p.sendMessage("This is a utility command.");
				}
			}
			
			if (Label.equalsIgnoreCase("nobribe")){
				if (areYouSure.containsKey(p.getName())){
					p.sendMessage(ToolAPI.tag + ChatColor.RED + "Ok, I guess you still have to wait.");
					areYouSure.remove(p.getName());
				}else{
					p.sendMessage("This is a utility command.");
				}
			}
		}
		return true;
	}
	
	public static void sendTellRaw(Player p, String raw){
		IChatBaseComponent comp = ChatSerializer.a(raw);
        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
