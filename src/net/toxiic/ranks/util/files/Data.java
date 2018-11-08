package net.toxiic.ranks.util.files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import net.toxiic.ranks.util.Util;

public class Data {
	private Data() {}	
	static Data instance = new Data();
	static Plugin p;
	static FileConfiguration data;
	static File dfile;
	
	public static void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		dfile = new File(p.getDataFolder(), "data.yml");
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			} catch(IOException e) {
				Bukkit.getServer().getLogger().severe(Util.color("&cCould not create data.yml!"));
			}
		}
		data = YamlConfiguration.loadConfiguration(dfile);
	}
	
	public static FileConfiguration getData() {
		return data;
	}
	
	public static void saveData() {
		try {
			data.save(dfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(Util.color("&cCould not save data.yml!"));
		}
	}
	
	public static void reloadData() {
		data = YamlConfiguration.loadConfiguration(dfile);
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

}
