package com.gmail.profanedbane.PortalTrapRescue;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getWorld;

public class LoginPortalTrapRescueTask extends BukkitRunnable {
    private Player player;
    private PortalTrapRescue plugin;
    private Location portalLocation;

    public LoginPortalTrapRescueTask(Player player, PortalTrapRescue plugin, Location portalLocation){
        this.player = player;
        this.plugin = plugin;
        this.portalLocation = portalLocation;
    }

    @Override
    public void run(){
        // PortalCooldown is locked to 10 when a player remains in a portal
        if(player.isOnline() && player.getPortalCooldown() >= 10){
            this.plugin.portalTrapRescueTaskMap.remove(player.getUniqueId());
            player.sendMessage(ChatColor.RED + this.plugin.config.getString("rescueMessage"));
            plugin.log.info(player.getName() + " teleported to spawn from potential portal trap at " + portalLocation);
            player.teleport(getWorld(this.plugin.config.getString("spawnWorld")).getSpawnLocation());
        }
    }
}
