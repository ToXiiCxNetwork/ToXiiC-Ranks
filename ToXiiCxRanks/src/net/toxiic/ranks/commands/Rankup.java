package net.toxiic.ranks.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.toxiic.ranks.Main;
import net.toxiic.ranks.util.Util;
import net.toxiic.ranks.util.files.Config;
import net.toxiic.ranks.util.files.Data;
import net.toxiic.ranks.util.files.Messages;

public class Rankup extends Util implements CommandExecutor {
	public static boolean canRankup(Player p) {
		if (Config.getBoolean("Config.Prestige.UseRankAlgorithm")) {
			if (Main.getEconomy().getBalance(p) >= getRankupPrice(p)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (Main.getEconomy().getBalance(p) >= Config.getDouble("Ranks." + getRank(p) + ".Price")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) { // So console can't access command.
			sender.sendMessage("You must be a player to do this command!");

			return false;
		}

		Player p = (Player) sender;

		if (!getRank(p).equals(String.valueOf(getLastRank()))) {
			if (canRankup(p)) {
				if (Config.getBoolean("Config.Prestige.UseRankAlgorithm")) {
					for (String c : Config.getList("Ranks." + getRank(p) + ".Commands")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), c.replaceAll("%player", p.getName()).replaceAll("%currentrank", getRank(p)).replaceAll("%nextrank", String.valueOf(nextRank(p))));
					}

					Main.getEconomy().withdrawPlayer(p, getRankupPrice(p));
					Data.setData("Players." + p.getName() + ".Rank", String.valueOf(nextRank(p)));
					msg(p, Messages.getMessage("Messages.Rankup.Success.Private", p, null));
					Bukkit.getServer().broadcastMessage(Messages.getMessage("Messages.Rankup.Success.Global", p, null));
					return true;
				} else {
					for (String c : Config.getList("Ranks." + getRank(p) + ".Commands")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), c.replaceAll("%player", p.getName()).replaceAll("%currentrank", getRank(p)).replaceAll("%nextrank", String.valueOf(nextRank(p))));
					}

					Main.getEconomy().withdrawPlayer(p, Config.getDouble("Ranks." + getRank(p) + ".Price"));
					Data.setData("Players." + p.getName() + ".Rank", String.valueOf(nextRank(p)));
					msg(p, Messages.getMessage("Messages.Rankup.Success.Private", p, null));
					Bukkit.getServer().broadcastMessage(Messages.getMessage("Messages.Rankup.Success.Global", p, null));
					return true;
				}
			} else {
				msg(p, Messages.getMessage("Messages.Rankup.Error.Money", p, null));
			}
		} else {
			msg(p, Messages.getMessage("Messages.Rankup.Error.Rank", p, null));
			return true;
		}

		return false;
	}
}
