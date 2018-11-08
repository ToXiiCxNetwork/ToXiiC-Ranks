package net.toxiic.ranks.util.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import net.toxiic.ranks.util.Util;

public class Config {
	private Config() {}	
	static Config instance = new Config();
	static Plugin p;
	static FileConfiguration config;
	static File cfile;
	
	public static void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		cfile = new File(p.getDataFolder(), "config.yml");
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(Util.color("&cCould not create config.yml!"));
			}
		}
		config = YamlConfiguration.loadConfiguration(cfile);
		newDefault();
	}
	
	public static FileConfiguration getData() {
		return config;
	}
	
	public static void saveData() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(Util.color("&cCould not save config.yml!"));
		}
	}
	
	public static void reloadData() {
		config = YamlConfiguration.loadConfiguration(cfile);
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
	
	public static boolean getBoolean(String path) {
		return getData().getBoolean(path);
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
		if (getData().getString("Config.Build") == null || !getData().getString("Config.Build").equals("1.0.1")) {
			  ArrayList<String> pcommands = new ArrayList<String>();
		      ArrayList<String> commands = new ArrayList<String>();
		      
		      commands.add("pex user %player group add %nextrank");
		      commands.add("pex user %player group remove %currentrank");
		      
		      pcommands.add("pex user %player group add A");
		      pcommands.add("pex user %player group remove %currentrank");
		      
		      setData("Config.Build", "1.0.1");
		      
		      setData("Config.Prestige.Multiplier", Double.valueOf(1.35D));
		      setData("Config.Prestige.UseRankAlgorithm", Boolean.valueOf(true));
		      setData("Config.Prestige.Commands", pcommands);
		      
		      setData("Config.Prestige.Price.Enabled", Boolean.valueOf(true));
		      setData("Config.Prestige.Price.Cost", Integer.valueOf(5000000));
		      setData("Config.Prestige.Price.Multiplier", Double.valueOf(1.5D));
		      
		      setData("Config.Ranks.LastRank", "D");
		      
		      setData("Config.Chat.Prestige", "%rankformatted&r &7%displayname &8[&6%prestige&8] &7?&f ");
		      setData("Config.Chat.Vanilla", "%rankformatted&r &7%displayname &7?&f ");
		      
		      setData("Ranks.A.Price", Integer.valueOf(5000));
		      setData("Ranks.A.Format", "&8[A]");
		      setData("Ranks.A.Commands", commands);
		      setData("Ranks.B.Price", Integer.valueOf(10000));
		      setData("Ranks.B.Format", "&8[&7B&8]");
		      setData("Ranks.B.Commands", commands);
		      setData("Ranks.C.Price", Integer.valueOf(17500));
		      setData("Ranks.C.Format", "&8[&7C&8]");
		      setData("Ranks.C.Commands", commands);
		      setData("Ranks.D.Price", Integer.valueOf(35000));
		      setData("Ranks.D.Format", "&8[&fD&8]");
		      setData("Ranks.D.Commands", commands);
		}
	}

}
