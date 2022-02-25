package _me.truemb.universal.minecraft.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import me.truemb.discordnotify.commands.DN_StaffCommand;
import me.truemb.discordnotify.main.DiscordNotifyMain;

public class SpongeCommandExecutor_Staff extends BukkitCommand {

	private DiscordNotifyMain instance;
	private DN_StaffCommand staffCommand;

	protected SpongeCommandExecutor_Staff(DiscordNotifyMain plugin) {
		super("staff", null, null, List.of("s"));
		this.instance = plugin;
		this.staffCommand = new DN_StaffCommand(plugin);
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(this.instance.getConfigManager().getMinecraftMessage("console", false));
			return true;
		}

		Player p = (Player) sender;
		UUID uuid = p.getUniqueId();
		
		this.staffCommand.onCommand(this.instance.getUniversalServer().getPlayer(uuid), args);
		return true;
	}
	
}
