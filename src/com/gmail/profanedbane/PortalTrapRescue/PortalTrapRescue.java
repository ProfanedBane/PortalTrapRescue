package com.gmail.profanedbane.PortalTrapRescue;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortalTrapRescue extends JavaPlugin {
    FileConfiguration config;
    Logger log = getLogger();

    // store active rescue checks to correctly replace them if a player manages to start another
    ConcurrentHashMap<UUID, BukkitTask> portalTrapRescueTaskMap = new ConcurrentHashMap<UUID, BukkitTask>();

    @Override
    public void onEnable(){
        // Setup configs
        saveDefaultConfig();
        config = this.getConfig();

        // Check if spawnWorld is valid
        if (getServer().getWorld(config.getString("spawnWorld")) == null) {
            log.log(Level.SEVERE, "Invalid world set for 'spawnWorld', undefined behaviour may occur!");
        }

        // Setup listeners
        getServer().getPluginManager().registerEvents( new PortalTrapRescueListener(this), this);
    }
}


