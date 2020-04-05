package com.gmail.profanedbane.PortalTrapRescue;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;

// Schedule rescue tasks when relevant events are triggered, time is taken from config
public class PortalTrapRescueListener implements Listener {
    private final PortalTrapRescue plugin;

    PortalTrapRescueListener(PortalTrapRescue plugin){
        this.plugin = plugin;
    }

    // Players teleporting through a Nether portal
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void playerPortalled(PlayerPortalEvent event) {
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            Player player = event.getPlayer();
            BukkitTask task = new TeleportedPortalTrapRescueTask(player, this.plugin, player.getLocation()).runTaskLater(this.plugin, this.plugin.config.getInt("rescueDelay"));

            // If a player already has an active task this replaces it
            if(this.plugin.portalTrapRescueTaskMap.containsKey(player.getUniqueId())){
                this.plugin.portalTrapRescueTaskMap.put(player.getUniqueId(), task).cancel();
            } else {
                this.plugin.portalTrapRescueTaskMap.put(player.getUniqueId(), task);
            }
        }
    }

    // Players logging in
    // Check if PortalCooldown >= 10, this indicates they are inside a portal block unable to teleport out
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void playerJoined(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.getPortalCooldown() >= 10) {
            BukkitTask task = new LoginPortalTrapRescueTask(player, this.plugin, player.getLocation()).runTaskLater(this.plugin, this.plugin.config.getInt("rescueDelay"));
            player.sendMessage(ChatColor.RED + this.plugin.config.getString("spawnMessage"));

            // If a player already has an active task this replaces it
            if(this.plugin.portalTrapRescueTaskMap.containsKey(player.getUniqueId())){
                this.plugin.portalTrapRescueTaskMap.put(player.getUniqueId(), task).cancel();
            } else {
                this.plugin.portalTrapRescueTaskMap.put(player.getUniqueId(), task);
            }
        }
    }
}
