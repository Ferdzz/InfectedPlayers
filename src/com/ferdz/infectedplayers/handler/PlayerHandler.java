package com.ferdz.infectedplayers.handler;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import com.ferdz.infectedplayers.InfectedPlayers;
import com.ferdz.infectedplayers.Settings;

public class PlayerHandler implements Listener {
	Random rand;

	public PlayerHandler() {
		rand = new Random();
	}

	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		if (e.isCancelled() || !(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Zombie))
			return;
		int t = rand.nextInt(Settings.TURNCHANCE);
		if (t == Settings.TURNCHANCE - 1) { // if the player should turn
			Player p = (Player) e.getEntity();
			if (!InfectedPlayers.infectedPlayers.contains(p.getUniqueId())) {
				InfectedPlayers.infectedPlayers.add(p.getUniqueId());
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (InfectedPlayers.infectedPlayers.contains(p.getUniqueId())) {
			p.sendMessage(Settings.TURNMESSAGE);
			Zombie entity = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
			entity.setCustomName(ChatColor.RED + p.getName());
			entity.setCustomNameVisible(true);
			EntityEquipment equip = p.getEquipment();
			entity.getEquipment().setBootsDropChance(1.0F);
			entity.getEquipment().setChestplateDropChance(1.0F);
			entity.getEquipment().setLeggingsDropChance(1.0F);
			entity.getEquipment().setHelmetDropChance(1.0F);
			entity.getEquipment().setArmorContents(equip.getArmorContents());
			entity.getEquipment().setItemInHand(equip.getItemInHand());
			
			for (ItemStack is : equip.getArmorContents()) {
				e.getDrops().remove(is);
			}
			
			InfectedPlayers.infectedPlayers.remove(p.getUniqueId());
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		InfectedPlayers.infectedPlayers.remove(e.getPlayer());
	}
}
