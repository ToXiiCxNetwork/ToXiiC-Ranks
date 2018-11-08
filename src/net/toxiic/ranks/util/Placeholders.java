package net.toxiic.ranks.util;

import org.bukkit.entity.Player;

import net.toxiic.ranks.Main;
import net.toxiic.ranks.util.files.Config;

import me.clip.placeholderapi.external.EZPlaceholderHook;

@SuppressWarnings("deprecation")
public class Placeholders extends EZPlaceholderHook {
	
	public Placeholders(Main plugin) {
		super(plugin, "toxiicranks");
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("rank")) {
			return Util.getRank(p);
		}
		
		if (identifier.equals("rankformatted")) {
			return Config.getString("Ranks." + Util.getRank(p) + ".Format");
		}
		
		if (identifier.equals("prestige")) {
			return Util.getPrestige(p);
		}
		
		return null;
	}
}
