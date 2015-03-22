package org.Prison.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.entity.Player;

public class Cooldown {

		public static boolean hasCooldown(Player p, String type){
			if(Files.config().contains("Players." + p.getName() + ".Cooldowns." + type)){
				return true;
			}else{
				return false;
			}
		}
		
		public static void setCooldown(Player p, String type){
			Calendar c = Calendar.getInstance();
			int days = Files.config().getInt("Cooldown." + type + ".Day");
			int hours = Files.config().getInt("Cooldown." + type + ".Hour");
			int minutes = Files.config().getInt("Cooldown." + type + ".Minute");

			int currentday = c.get(Calendar.DAY_OF_YEAR);
			int currenthours = c.get(Calendar.HOUR_OF_DAY);
			int currentminutes = c.get(Calendar.MINUTE);
			
			int newday = days + currentday;
			int newhours = hours + currenthours;
			int newminutes = minutes + currentminutes;
			
			if (newminutes >= 60){
				newminutes = newminutes - 60;
				newhours = newhours + 1;
			}
			if (newhours >= 24){
				newhours = newhours - 24;
				newday = newday + 1;
			}
			Files.config().set("Players." + p.getName() + ".Cooldowns." + type + ".Day", newday);
			Files.config().set("Players." + p.getName() + ".Cooldowns." + type + ".Hour", newhours);
			Files.config().set("Players." + p.getName() + ".Cooldowns." + type + ".Minute", newminutes);
			Files.config().set("Players." + p.getName() + ".Cooldowns." + type + ".Second", c.get(Calendar.SECOND));
			Files.saveConfig();
		}
		
		public static String getTimeLeft(Player p, String type){
			Calendar c = Calendar.getInstance();
			int day = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Day");
			int hours = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Hour");
			int minutes = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Minute");
			int seconds = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Second");
			String dateStart = c.get(Calendar.DAY_OF_YEAR) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
			String dateStop = day + "/" + c.get(Calendar.YEAR) + " " + hours + ":" + minutes + ":" + seconds;
	 
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("dd/yyyy HH:mm:ss");
	 
			Date d1 = null;
			Date d2 = null;
	 
			try {
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);
	 
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
				
				
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				return "§9 " + diffMinutes + " §9§lMins§9 " + diffSeconds + " §9§lSecs";
	 
			} catch (Exception e) {
				e.printStackTrace();
				return "BROKEN";
			}
		}
		
		public static boolean checkCooldown(Player p, String type){
			Calendar c = Calendar.getInstance();
			int day = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Day");
			int hours = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Hour");
			int minutes = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Minute");
			int seconds = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Second");
			String dateStart = c.get(Calendar.DAY_OF_YEAR) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
			String dateStop = day + "/" + c.get(Calendar.YEAR) + " " + hours + ":" + minutes + ":" + seconds;
	 
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("dd/yyyy HH:mm:ss");
	 
			Date d1 = null;
			Date d2 = null;
	 
			try {
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);
	 
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
				if (diff <= 0){
					Files.config().set("Players." + p.getName() + ".Cooldowns." + type, null);
					Files.saveConfig();
					return true;
				}else{
					return false;
				}
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public static String getCooldown(String type){
			int days = Files.config().getInt("Cooldown." + type + ".Day");
			int hours = Files.config().getInt("Cooldown." + type + ".Hour");
			int minutes = Files.config().getInt("Cooldown." + type + ".Minute");
			
			if (days > 0 && hours > 0 && minutes > 0){
				return " §e§l" + days + "D " + hours + "H " + minutes + "M"; 
			}
			if (days > 0 && hours > 0 && minutes == 0){
				return " §e§l" + days + "D " + hours + "H";
			}
			if (hours > 0 && minutes > 0 && days == 0){
				return " §e§l" + hours + "H " + minutes + "M";
			}
			if (days > 0 && minutes > 0 && hours == 0){
				return " §e§l" + days + "D " + minutes + "M";
			}
			if (days > 0 && hours == 0 && minutes == 0){
				return " §e§l" + days + " Days"; 
			}
			if (hours > 0 && days == 0 && minutes == 0){
				return " §e§l" + days + " Hours"; 
			}
			if (minutes > 0 && days == 0 && hours == 0){
				return " §e§l" + minutes + " Minutes"; 
			}
			return "BROKEN";
		}
		public static int getMinutes(String type, Player p){
			if (hasCooldown(p, type)){
				Calendar c = Calendar.getInstance();
				int day = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Day");
				int hours = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Hour");
				int minutes = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Minute");
				int seconds = Files.config().getInt("Players." + p.getName() + ".Cooldowns." + type + ".Second");
				String dateStart = c.get(Calendar.DAY_OF_YEAR) + "/" + c.get(Calendar.YEAR) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
				String dateStop = day + "/" + c.get(Calendar.YEAR) + " " + hours + ":" + minutes + ":" + seconds;
		 
				//HH converts hour in 24 hours format (0-23), day calculation
				SimpleDateFormat format = new SimpleDateFormat("dd/yyyy HH:mm:ss");
		 
				Date d1 = null;
				Date d2 = null;
		 
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
		 
					//in milliseconds
					long diff = d2.getTime() - d1.getTime();
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					return (int) ((int) (diffMinutes * 60) + diffSeconds);
				}catch(Exception e){
					return 0;
				}
			}
			return 0;
		}
		
		public static void resetCooldown(String type, Player p){
			Files.config().set("Players." + p.getName() + ".Cooldowns." + type, null);
			Files.saveConfig();
		}
}
