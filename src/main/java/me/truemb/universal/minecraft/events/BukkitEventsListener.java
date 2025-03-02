package me.truemb.universal.minecraft.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.truemb.discordnotify.main.DiscordNotifyMain;
import me.truemb.universal.player.BukkitPlayer;
import me.truemb.universal.player.UniversalPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public class BukkitEventsListener implements Listener {
	
	private DiscordNotifyMain plugin;
	private BukkitAudiences adventure;
	
	public BukkitEventsListener(DiscordNotifyMain plugin, BukkitAudiences adventure) {
		this.plugin = plugin;
		this.adventure = adventure;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		if(e.isCancelled())
			return;
		
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		UniversalPlayer up = this.plugin.getUniversalServer().getPlayer(uuid);
		String message = e.getMessage();

		if(message.startsWith("/"))
			return;
		
		this.plugin.getListener().onPlayerMessage(up, message);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		
		UniversalPlayer up = new BukkitPlayer(p, this.adventure);

		this.plugin.getUniversalServer().addPlayer(up);
		this.plugin.getListener().onPlayerJoin(up, null);
		
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		//
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		
		UUID uuid = p.getUniqueId();
		
		UniversalPlayer up = this.plugin.getUniversalServer().getPlayer(uuid);
		if(up == null)
			return;

		this.plugin.getUniversalServer().removePlayer(up);
		this.plugin.getListener().onPlayerQuit(up, null);
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();
		
		UUID uuid = p.getUniqueId();
		UniversalPlayer up = this.plugin.getUniversalServer().getPlayer(uuid);
		String deathMessage = e.getDeathMessage();
		
		this.plugin.getListener().onPlayerDeath(up, deathMessage);
	}
	
}
