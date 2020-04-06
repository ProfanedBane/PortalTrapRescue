package com.gmail.profanedbane.PortalTrapRescue;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

// Check if players are still stuck in a Nether Portal after going through it
// Teleport them back to where they came from if so
public class TeleportedPortalTrapRescueTask extends BukkitRunnable {
    private Player player;
    private PortalTrapRescue plugin;
    private Location returnLocation;

    public TeleportedPortalTrapRescueTask(Player player, PortalTrapRescue plugin, Location returnLocation){
        this.player = player;
        this.plugin = plugin;
        this.returnLocation = returnLocation;
    }

    @Override
    public void run(){
        // PortalCooldown is locked to 10 when a player is remaining in a portal they can't travel through
        if(player.isOnline() && player.getPortalCooldown() >= 10){
            this.plugin.portalTrapRescueTaskMap.remove(player.getUniqueId());
            player.sendMessage(ChatColor.RED + this.plugin.config.getString("rescueMessage"));
            plugin.log.info(player.getName() + " teleported to " + returnLocation + " from potential portal trap at " + player.getLocation().toString());
            player.teleport(returnLocation);
        }
    }
}
