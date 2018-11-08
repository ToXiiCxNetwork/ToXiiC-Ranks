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

public class Prestige extends Util implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) { // So console can't access command.
			sender.sendMessage("You must be a player to do this command!");

			return false;
		}

		Player p = (Player) sender;
		if (args.length == 0) {
			if (getRank(p).equals(String.valueOf(getLastRank()))) {
				if (Config.getBoolean("Config.Prestige.Price.Enabled")) {
					if (Main.getEconomy().getBalance(p) >= (Config.getInt("Config.Prestige.Price.Cost") * Math.pow(Config.getDouble("Config.Prestige.Price.Multiplier"), Util.getIPrestige(p)))) {
						Data.setData("Players." + p.getName() + ".Prestige", Data.getInt("Players." + p.getName() + ".Prestige") + 1);

						for (String s : Config.getList("Config.Prestige.Commands")) {
							Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player", p.getName()).replaceAll("%currentrank", getRank(p)).replaceAll("%prestige", Data.getString("Players." + p.getName() + ".Prestige")));
						}
						
						Data.setData("Players." + p.getName() + ".Rank", 'A');
						Main.getEconomy().withdrawPlayer(p, (Config.getInt("Config.Prestige.Price.Cost") * Math.pow(Config.getDouble("Config.Prestige.Price.Multiplier"), Util.getIPrestige(p))));
						msg(p, Messages.getMessage("Messages.Prestige.Success.Private", p, null));
						Bukkit.getServer().broadcastMessage(Messages.getMessage("Messages.Prestige.Success.Global", p, null));
						return true;
					} else {
						msg(p, Messages.getMessage("Messages.Prestige.Error.Money", p, null));
						return true;
					}
				} else {
					Data.setData("Players." + p.getName() + ".Prestige", Data.getInt("Players." + p.getName() + ".Prestige") + 1);

					for (String s : Config.getList("Config.Prestige.Commands")) {
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player", p.getName()).replaceAll("%currentrank", getRank(p)).replaceAll("%prestige", Data.getString("Players." + p.getName() + ".Prestige")));
					}
					
					Data.setData("Players." + p.getName() + ".Rank", 'A');
					msg(p, Messages.getMessage("Messages.Prestige.Success.Private", p, null));
					Bukkit.getServer().broadcastMessage(Messages.getMessage("Messages.Prestige.Success.Global", p, null));
					return true;
				}
			} else {
				msg(p, Messages.getMessage("Messages.Prestige.Error.Rank", p, null));
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set") && p.hasPermission("toxiicxranks.prestige.set")) {
				Data.setData("Players." + p.getName() + ".Prestige", args[1]);
				msg(p, Messages.getMessage("Messages.Prestige.Set.Self.Success", p, null));
				return true;
			} else {
				msg(p, Messages.getMessage("Messages.Prestige.Set.Self.Error.Permission", p, null));
				return true;
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("set") && p.hasPermission("toxiicxranks.prestige.set.other")) {
				if (Data.getString("Players." + args[1]) != null) {
					Data.setData("Players." + args[1] + ".Prestige", args[2]);
					msg(p, Messages.getMessage("Messages.Prestige.Set.Other.Success.Executor", p, args[1]));
					return true;
				} else {
					msg(p, Messages.getMessage("Messages.Prestige.Set.Other.Error.Player", p, args[1]));
					return true;
				}
			} else {
				msg(p, Messages.getMessage("Messages.Prestige.Set.Other.Error.Permission", p, args[1]));
				return true;
			}
		} else {
			return true;
		}
	}
}
