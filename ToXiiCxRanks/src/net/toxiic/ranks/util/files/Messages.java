package net.toxiic.ranks.util.files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import net.toxiic.ranks.Main;
import net.toxiic.ranks.util.Util;

public class Messages {
	private Messages() {
	}

	static Messages instance = new Messages();
	static Plugin p;
	static FileConfiguration messages;
	static File mfile;

	public static void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		mfile = new File(p.getDataFolder(), "messages.yml");
		if (!mfile.exists()) {
			try {
				mfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger().severe(Util.color("&cCould not create messages.yml!"));
			}
		}
		messages = YamlConfiguration.loadConfiguration(mfile);
		newDefault();
	}

	public static FileConfiguration getData() {
		return messages;
	}

	public static void saveData() {
		try {
			messages.save(mfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(Util.color("&cCould not save messages.yml!"));
		}
	}

	public static void reloadData() {
		messages = YamlConfiguration.loadConfiguration(mfile);
	}

	public static PluginDescriptionFile getDesc() {
		return p.getDescription();
	}

	public static int getInt(String path) {
		return Integer.valueOf(getString(path));
	}

	public static double getDouble(String path) {
		return Double.valueOf(getString(path));
	}

	public static long getLong(String path) {
		return getData().getLong(path);
	}

	public static List<String> getList(String path) {
		return getData().getStringList(path);
	}

	public static String getString(String path) {
		return getData().getString(path);
	}

	public static Object get(String path) {
		return getData().get(path);
	}

	public static ItemStack getItemStack(String path) {
		return getData().getItemStack(path);
	}

	public static void setData(String path, Object obj) {
		getData().set(path, obj);
		saveData();
		reloadData();
	}

	public static void newDefault() {
		if (getData().get("Messages") == null) {
			// Rankup
			setData("Messages.Rankup.Error.Rank", "&4You are already at the last rank!");
			setData("Messages.Rankup.Error.Money", "&4You need &c$%rankcost&4 to rankup to &c%nextrank&4!");

			setData("Messages.Rankup.Success.Private", "&2Congrats, &a%player&2, you ranked up to, &a%playerrank&2! Current balance: &a$%balance");
			setData("Messages.Rankup.Success.Global", "&6&l%player&7&l ranked up to &6&l%playerrank&7&l!");

			// Prestige
			setData("Messages.Prestige.Error.Rank", "&4You need to be rank &c%lastrank&4 to prestige!");
			setData("Messages.Prestige.Error.Money", "&4You need &c%prestigecost&4 to prestige to &c%nextprestige&4!");

			setData("Messages.Prestige.Success.Private", "&2Congrats, &a%player&2, you are now prestige &a%prestige&2!");
			setData("Messages.Prestige.Success.Global", "&6&l%player&7&l is now prestige &6&l%prestige&7&l!");

			setData("Messages.Prestige.Set.Self.Error.Permission", "&4You do not have permission to set your prestige!");

			setData("Messages.Prestige.Set.Self.Success", "&2You successfully set your prestige to &a%prestige");

			setData("Messages.Prestige.Set.Other.Error.Permission", "&4You do not have permission to set other player's prestige!");
			setData("Messages.Prestige.Set.Other.Error.Player", "&4The player, &c%targetplayer&4, does not exist!");

			setData("Messages.Prestige.Set.Other.Success.Executor", "&2You successfully set &a%targetplayer's&2 prestige to &a%prestige&2!");
			setData("Messages.Prestige.Set.Other.Success.Target", "&2Your prestige was set to &a%prestige&2!");

			// Rank
			setData("Messages.Rank.List.Header", "&7-----&aRanks&7-----");
			setData("Messages.Rank.List.Format.Previous", "&a %rank &2Price: &a%cost");
			setData("Messages.Rank.List.Format.Current", "&7 %rank &8Price: &7%cost");
			setData("Messages.Rank.List.Format.Next", "&c %rank &4Price: &c%cost");
			
			//Sell
			setData("Messages.Sell.Header", "&7----&aToXiiC Sell&7----");
			setData("Messages.Sell.amt", "You sold all avalible items");
			setData("Messages.Sell.val", "You recieved %value");
			setData("Messages.Sell.Footer", "&7-------------------");
			setData("Messages.Rank.Set.Self.Error.Permission", "&4You do not have permission to set your rank!");
			setData("Messages.Rank.Set.Self.Error.Rank", "&4No such rank exists!");

			setData("Messages.Rank.Set.Self.Success", "&2You successfully set your rank to &a%playerrank&2!");

			setData("Messages.Rank.Set.Other.Error.Permission", "&4You do not have permission to set your rank!");
			setData("Messages.Rank.Set.Other.Error.Rank", "&4No such rank exists!");
			setData("Messages.Rank.Set.Other.Error.Player", "&4The player, &c%targetplayer&4, does not exist!");

			setData("Messages.Rank.Set.Other.Success.Executor", "&2You successfully set &a%targetplayer's&2 rank to &a%targetplayerrank&2!");
			setData("Messages.Rank.Set.Other.Success.Target", "&2Your rank was set to &a%targetplayerrank&2!");
		}
	}

	public static String getMessage(String path, Player player, String target) {
		if (target != null) {
			return Util.color(getString(path)
					.replaceAll("%playerrank", Util.getRank(player))
					.replaceAll("%targetplayerrank", Util.getRank(Bukkit.getPlayer(target)))
					.replaceAll("%targetplayerprestige", Util.getPrestige(Bukkit.getPlayer(target)))
					.replaceAll("%targetplayer", target)
					.replaceAll("%prestigecost", "" + (Config.getInt("Config.Prestige.Price.Cost") * Math.pow(Config.getDouble("Config.Prestige.Price.Multiplier"), Util.getIPrestige(player))))
					.replaceAll("%rankcost", "" + (Config.getDouble("Ranks." + Util.getRank(player) + ".Price") * (Math.pow(Config.getDouble("Config.Prestige.Multiplier"), Data.getInt("Players." + player.getName() + ".Prestige")))))
					.replaceAll("%nextrank", String.valueOf(Util.nextRank(player)))
					.replaceAll("%lastrank", Config.getString("Config.LastRank"))
					.replaceAll("%nextprestige", String.valueOf(Util.getIPrestige(player) + 1))
					.replaceAll("%player", player.getName())
					.replaceAll("%balance", String.valueOf(Main.getEconomy().getBalance(player)))
					.replaceAll("%prestige", String.valueOf(Util.getPrestige(player)))
					);
		} else {
			return Util.color(getString(path)
					.replaceAll("%playerrank", Util.getRank(player))
					.replaceAll("%prestigecost", "" + (Config.getInt("Config.Prestige.Price.Cost") * Math.pow(Config.getDouble("Config.Prestige.Price.Multiplier"), Util.getIPrestige(player))))
					.replaceAll("%rankcost", "" + (Config.getDouble("Ranks." + Util.getRank(player) + ".Price") * (Math.pow(Config.getDouble("Config.Prestige.Multiplier"), Data.getInt("Players." + player.getName() + ".Prestige")))))
					.replaceAll("%nextrank", String.valueOf(Util.nextRank(player)))
					.replaceAll("%lastrank", String.valueOf(Util.getLastRank()))
					.replaceAll("%nextprestige", String.valueOf(Util.getIPrestige(player) + 1))
					.replaceAll("%player", player.getName())
					.replaceAll("%balance", String.valueOf(Main.getEconomy().getBalance(player)))
					.replaceAll("%prestige", String.valueOf(Util.getPrestige(player)))
					);
		}
	}

}
