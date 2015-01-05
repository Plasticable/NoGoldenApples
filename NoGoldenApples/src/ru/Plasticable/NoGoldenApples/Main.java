package ru.Plasticable.NoGoldenApples;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Main extends JavaPlugin implements Listener
{
	private FileConfiguration config;
	private String joinNotify, warning, globalNotify, goldenCarrotName, goldenAppleName, immunityMessage;
	private int potionDuration, potionAmplifier;
	private boolean globalNotifyEnabeled, joinNotifyEnabled;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		config = getConfig();
		
		joinNotifyEnabled = config.getBoolean("joinNotifyEnabled");
		joinNotify = config.getString("joinNotify");
		warning = config.getString("warning");
		globalNotify = config.getString("globalNotify");
		globalNotifyEnabeled = config.getBoolean("isGlobalNotifyEnabled");
		potionDuration = config.getInt("potionDuration");
		potionAmplifier = config.getInt("potionAmplifier");
		goldenCarrotName = config.getString("goldenCarrotName");
		goldenAppleName = config.getString("goldenAppleName");
		immunityMessage = config.getString("steelTooth");
		
	}

	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if(joinNotifyEnabled)
			e.getPlayer().sendMessage(ChatColor.YELLOW + joinNotify);
	}
	
	
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent event)
	{
		Player player = event.getPlayer();
		if(!player.hasPermission("nogoldenapples.immunity"))
		{
			if(player.getItemInHand().getType() == Material.GOLDEN_APPLE)
			{
				player.sendMessage(ChatColor.RED + warning.replace("{ITEM}", goldenAppleName));
				
				if(globalNotifyEnabeled)
					player.getServer().broadcastMessage(ChatColor.YELLOW + globalNotify.replace("{PLAYER}", player.getName()).replace("{ITEM}", goldenAppleName));
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, potionDuration*20, potionAmplifier));
				event.setCancelled(true);
			}
			if(player.getItemInHand().getType() == Material.GOLDEN_CARROT)
			{
				player.sendMessage(ChatColor.RED + warning.replace("{ITEM}", goldenCarrotName));
				
				if(globalNotifyEnabeled)
					player.getServer().broadcastMessage(ChatColor.YELLOW + globalNotify.replace("{PLAYER}", player.getName()).replace("{ITEM}", goldenCarrotName));
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, potionDuration*20, potionAmplifier));
				event.setCancelled(true);
			}
		}
		else
			player.sendMessage(ChatColor.GREEN + immunityMessage);
		
	}
}
