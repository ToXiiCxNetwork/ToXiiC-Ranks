package net.toxiic.ranks.util;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.toxiic.ranks.util.files.Config;
import net.toxiic.ranks.util.files.Data;

public class Util {
	public static HashMap<String, Object> idLink;

	public static String color(String msg) { //convert color codes
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static void msg(Player p, String msg) { //send a player a colored message
		p.sendMessage(color(msg));
	}
	
	public static double getRankupPrice(Player p) {
		return Config.getDouble("Ranks." + getRank(p) + ".Price") * (Math.pow(Config.getDouble("Config.Prestige.Multiplier"), Data.getInt("Players." + p.getName() + ".Prestige")));
	}

	public static String getRank(Player p) {
		return Data.getString("Players." + p.getName() + ".Rank");
	}
	
	public static String getPrestige(Player p) {
		return Data.getString("Players." + p.getName() + ".Prestige");
	}
	
	public static Integer getIPrestige(Player p) {
		return Data.getInt("Players." + p.getName() + ".Prestige");
	}

	public static void loadAll() {
		idLink = new HashMap<String, Object>();
	}

	public static void inform(String text) {
		Logger logger = Logger.getLogger("Minecraft");
		logger.info("[" + "ToXiiCxRanks" + "] " + text);
	}

	public static void warn(String text) {
		Logger logger = Logger.getLogger("Minecraft");
		logger.warning("[" + "ToXiiCxRanks" + "] " + text);
	}

	public static void severe(String text) {
		Logger logger = Logger.getLogger("Minecraft");
		logger.severe("[" + "ToXiiCxRanks" + "] " + text);
	}
	
	public static char nextRank(Player p) {
		char c = Data.getString("Players." + p.getName() + ".Rank").charAt(0);
		
		for (char a = 'A'; a <= getLastRank(); a++) {
			if (a == c) {
				a++;
				return a;
			}
		}
		return c;
	}
	
	public static char getLastRank() {
		return Config.getString("Config.Ranks.LastRank").charAt(0);
	}
}
