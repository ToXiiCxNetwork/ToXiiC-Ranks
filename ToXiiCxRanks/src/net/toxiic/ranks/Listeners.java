package net.toxiic.ranks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.toxiic.ranks.util.Util;
import net.toxiic.ranks.util.files.Config;
import net.toxiic.ranks.util.files.Data;

public class Listeners extends Util implements Listener {

	@EventHandler()
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (Data.getString("Players." + p.getName() + ".Rank") == null) {
			Data.setData("Players." + p.getName() + ".Rank", 'A');
			Data.setData("Players." + p.getName() + ".Prestige", 0);
		}
	}

	@EventHandler()
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("toxiicxranks.chatcolor")) {
			if (Util.getIPrestige(p) > 0) {
				e.setFormat(color(Config.getString("Config.Chat.Prestige")
						.replaceAll("%displayname", p.getDisplayName())
						.replaceAll("%rankformatted", Config.getString("Ranks." + getRank(p) + ".Format"))
						.replaceAll("%prestige", getPrestige(p))
						.replaceAll("%rank", getRank(p))
						+ e.getMessage()));
			} else {
				e.setFormat(color(Config.getString("Config.Chat.Vanilla")
						.replaceAll("%displayname", p.getDisplayName())
						.replaceAll("%rankformatted", Config.getString("Ranks." + getRank(p) + ".Format"))
						.replaceAll("%prestige", getPrestige(p))
						.replaceAll("%rank", getRank(p))
						+ e.getMessage()));
			}
		} else {
			if (Util.getIPrestige(p) > 0) {
				e.setFormat(color(Config.getString("Config.Chat.Prestige")
						.replaceAll("%displayname", p.getDisplayName())
						.replaceAll("%rankformatted", Config.getString("Ranks." + getRank(p) + ".Format"))
						.replaceAll("%prestige", getPrestige(p))
						.replaceAll("%rank", getRank(p))
						) + e.getMessage());
			} else {
				e.setFormat(color(Config.getString("Config.Chat.Vanilla")
						.replaceAll("%displayname", p.getDisplayName())
						.replaceAll("%rankformatted", Config.getString("Ranks." + getRank(p) + ".Format"))
						.replaceAll("%prestige", getPrestige(p))
						.replaceAll("%rank", getRank(p))
						) + e.getMessage());
			}
		}
	}
}
