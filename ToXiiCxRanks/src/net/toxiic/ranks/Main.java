package net.toxiic.ranks;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.AsyncCatcher;

import net.toxiic.ranks.commands.Prestige;
import net.toxiic.ranks.commands.Ranks;
import net.toxiic.ranks.commands.Rankup;
import net.toxiic.ranks.util.Placeholders;
import net.toxiic.ranks.util.Util;
import net.toxiic.ranks.util.files.Config;
import net.toxiic.ranks.util.files.Data;
import net.toxiic.ranks.util.files.Messages;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy economy = null;
	public static Main plugin;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {

		if (!setupEconomy()) { //Check if a vault based economy is setup
			log.severe(String.format("[%s] - Disabled due to no Vault based economy found! Try installing Essentials!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Bukkit.getLogger().info("[ToXiiCxRanks] This plugin is managed by ToXiiCxMonster and ToXiiCxUltra");
		Bukkit.getLogger().info("[ToXiiCxRanks] Feel free to contact us at http://discord.toxiic.net");
		plugin = this;
		Data.setup(plugin);
		Config.setup(plugin);
		Messages.setup(plugin);
		AsyncCatcher.enabled = false;

		Util.loadAll();

		registerCommands();
		registerListener(new Listeners());
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new Placeholders(this).hook();
		}
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");

		logger.info(pdfFile.getName() + " has been disable!");
	}

	private boolean setupEconomy() { //Registering Economy
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}

	public static Economy getEconomy() {
		return economy;
	}

	public void registerCommands() { //register commands
		getCommand("rankup").setExecutor(new Rankup());
		getCommand("ranks").setExecutor(new Ranks());
		getCommand("prestige").setExecutor(new Prestige());
	}

	public void registerListener(Listener listener) { //register listeners
		Bukkit.getServer().getPluginManager().registerEvents(listener, this);
	}

}
