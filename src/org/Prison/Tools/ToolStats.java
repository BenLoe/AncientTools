package org.Prison.Tools;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ToolStats {

	private double ExtraDrops, AncientChance;
	private int Enchants, Speed, efficiency, fortune, unbreaking;
	
	public ToolStats(double ExtraDrops, double AncientChance, int Enchants, int Speed, int efficiency, int fortune, int unbreaking){
		this.ExtraDrops = ExtraDrops;
		this.AncientChance = AncientChance;
		this.Enchants = Enchants;
		this.Speed = Speed;
		this.efficiency = efficiency;
		this.fortune = fortune;
		this.unbreaking = unbreaking;
	}
	
	public double getExtraDrops(){
		return ExtraDrops;
	}
	
	public double getAncientChance(){
		return AncientChance;
	}
	
	public int getEnchants(){
		return Enchants;
	}
	
	public float getSpeed(){
		return Float.valueOf(Speed);
	}
	
	public int getEfficiency(){
		return efficiency;
	}
	
	public int getFortune(){
		return fortune;
	}
	
	public int getUnbreaking(){
		return unbreaking;
	}
	
	public static ToolStats getToolStats(ItemStack item){
		ItemMeta itemm = item.getItemMeta();
		String Efficiency = "";
		String Fortune = "";
		String unbreaking = "";
		if (itemm.getLore().get(1).split(": ")[1].length() == 3){
			Efficiency = String.valueOf(itemm.getLore().get(1).split(": ")[1].charAt(2));
		}else if (itemm.getLore().get(1).split(": ")[1].length() == 4){
			Efficiency = itemm.getLore().get(1).split(": ")[1].substring(2, 4);
		}
		if (itemm.getLore().get(2).split(": ")[1].length() == 3){
			Fortune = String.valueOf(itemm.getLore().get(2).split(": ")[1].charAt(2));
		}else if (itemm.getLore().get(2).split(": ")[1].length() == 4){
			Fortune = itemm.getLore().get(2).split(": ")[1].substring(2, 4);
		}
		if (itemm.getLore().get(3).split(": ")[1].length() == 3){
			unbreaking = String.valueOf(itemm.getLore().get(3).split(": ")[1].charAt(2));
		}else if (itemm.getLore().get(3).split(": ")[1].length() == 4){
			unbreaking = itemm.getLore().get(3).split(": ")[1].substring(2, 4);
		}
		String ExtraDrops = String.valueOf(itemm.getLore().get(5).split(": ")[1].replace("%", "").substring(2, 6));
		String AncientChance = String.valueOf(itemm.getLore().get(6).split(": ")[1].replace("%", "").substring(2, 6));
		String Speed = String.valueOf(itemm.getLore().get(7).split(": ")[1].replace("+", "").charAt(2));
		String Enchants = String.valueOf(itemm.getLore().get(8).split(": ")[1].charAt(2));
		
		double Extra = Double.parseDouble(ExtraDrops);
		double Ancient = Double.parseDouble(AncientChance);
		int Speedi = Integer.parseInt(Speed);
		int Enchantsi = Integer.parseInt(Enchants);
		int Efficiencyi = Integer.parseInt(Efficiency);
		int Fortunei = Integer.parseInt(Fortune);
		int Unbreakingi = Integer.parseInt(unbreaking);
		
		return new ToolStats(Extra, Ancient, Enchantsi, Speedi, Efficiencyi, Fortunei, Unbreakingi);
	}
}
