package com.ferdz.infectedplayers;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ferdz.infectedplayers.handler.PlayerHandler;

public class InfectedPlayers extends JavaPlugin {

	public static InfectedPlayers instance;
	public static ArrayList<UUID> infectedPlayers;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		infectedPlayers = new ArrayList<UUID>();
		
		FileConfiguration fc = this.getConfig();
		Settings.TURNCHANCE = fc.getInt("turnchance");
		Settings.TURNMESSAGE = ChatColor.translateAlternateColorCodes('&', fc.getString("turnmessage"));
		
		Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
		this.getCommand("infectedplayers").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;
	}
}
