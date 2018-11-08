package net.toxiic.ranks.commands;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.toxiic.ranks.util.Util;
import net.toxiic.ranks.util.files.Config;
import net.toxiic.ranks.util.files.Data;
import net.toxiic.ranks.util.files.Messages;

public class Ranks extends Util implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) { // So console can't access command.
			sender.sendMessage("You must be a player to do this command!");

			return false;
		}

		Player p = (Player) sender;

		if (args.length == 0) {
			msg(p, Messages.getMessage("Messages.Rank.List.Header", p, null));
			char prc = Data.getString("Players." + p.getName() + ".Rank").charAt(0);
			char ch = 'A';
			for (char a = 'A'; a <= getLastRank(); a++) {
				if (a == 'A') {
					if (getRank(p).charAt(0) == a) {
						msg(p, Messages.getMessage("Messages.Rank.List.Format.Current", p, null).replaceAll("%rank", "A").replaceAll("%cost", "0"));
					} else {
						msg(p, Messages.getMessage("Messages.Rank.List.Format.Previous", p, null).replaceAll("%rank", "A").replaceAll("%cost", "0"));
					}
				} else {
					DecimalFormat df = new DecimalFormat("#.####");

					df.setRoundingMode(RoundingMode.CEILING);
					if (getRank(p).charAt(0) == a) {
						msg(p, Messages.getMessage("Messages.Rank.List.Format.Current", p, null).replaceAll("%rank", "" + a).replaceAll("%cost", df.format(Config.getDouble("Ranks." + ch + ".Price") * (Math.pow(Config.getDouble("Config.Prestige.Multiplier"), Data.getInt("Players." + p.getName() + ".Prestige"))))));
					} else if (prc > a) {
						msg(p, Messages.getMessage("Messages.Rank.List.Format.Previous", p, null).replaceAll("%rank", "" + a).replaceAll("%cost", df.format(Config.getDouble("Ranks." + ch + ".Price") * (Math.pow(Config.getDouble("Config.Prestige.Multiplier"), Data.getInt("Players." + p.getName() + ".Prestige"))))));
					} else if (prc < a) {
						msg(p, Messages.getMessage("Messages.Rank.List.Format.Next", p, null).replaceAll("%rank", "" + a).replaceAll("%cost", df.format(Config.getDouble("Ranks." + ch + ".Price") * (Math.pow(Config.getDouble("Config.Prestige.Multiplier"), Data.getInt("Players." + p.getName() + ".Prestige"))))));
					}
				}
				ch = a;
			}
			return true;
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set") && p.hasPermission("toxiicxranks.rank.set")) {
				if (args[1].charAt(0) <= getLastRank()) {
					for (String s : Config.getList("SetRank.Commands")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player", p.getName()).replaceAll("%toRank", args[1]).replaceAll("%fromRank", getRank(p)));
					}
					Data.setData("Players." + p.getName() + ".Rank", args[1]);
					msg(p, Messages.getMessage("Messages.Rank.Set.Self.Success", p, null));
					return true;
				} else {
					msg(p, Messages.getMessage("Messages.Rank.Set.Self.Error.Rank", p, null));
					return true;
				}
			} else {
				msg(p, Messages.getMessage("Messages.Rank.Set.Self.Error.Permission", p, null));
				return true;
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("set")) {
				if (p.hasPermission("toxiicxranks.rank.set.other")) {
					if (Data.getString("Players." + args[1]) != null) {
						if (args[2].charAt(0) <= getLastRank()) {
							for (String s : Config.getList("SetRank.Commands")) {
								Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player", args[1]).replaceAll("%toRank", args[2]).replaceAll("%fromRank", Data.getString("Players." + args[1] + ".Rank")));
							}
							Data.setData("Players." + args[1] + ".Rank", args[2]);
							msg(p, Messages.getMessage("Messages.Rank.Set.Other.Success.Executor", p, args[1]));
							msg(Bukkit.getServer().getPlayer(args[1]), Messages.getMessage("Messages.Rank.Set.Other.Success.Target", p, args[1]));
							return true;
						} else {
							msg(p, Messages.getMessage("Messages.Rank.Set.Other.Error.Rank", p, null));
							return true;
						}
					} else {
						msg(p, Messages.getMessage("Messages.Rank.Set.Other.Error.Player", p, args[1]));
						return true;
					}
				} else {
					msg(p, Messages.getMessage("Messages.Rank.Set.Other.Error.Permission", p, args[1]));
					return true;
				}
			}
		}
		return false;
	}
}
