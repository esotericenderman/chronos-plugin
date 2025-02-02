package dev.enderman.minecraft.plugins.time.chronos;

import dev.enderman.minecraft.plugins.time.chronos.listener.ChunkLoadListener;
import dev.enderman.minecraft.plugins.time.chronos.listener.ChunkUnloadListener;
import dev.enderman.minecraft.plugins.time.chronos.manager.ChunkTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChronosPlugin extends JavaPlugin {

    private ChunkTimeManager chunkTimeManager;

    public ChunkTimeManager getChunkTimeManager() {
        return chunkTimeManager;
    }

    private NamespacedKey chunkLastLoadedTimeKey;

    public NamespacedKey getChunkLastLoadedTimeKey() {
        return chunkLastLoadedTimeKey;
    }

    @Override
    public void onEnable() {
        YamlConfiguration configuration = (YamlConfiguration) getConfig();

        configuration.options().copyDefaults();
        saveDefaultConfig();

        chunkLastLoadedTimeKey = new NamespacedKey(this, "last_loaded_time");

        chunkTimeManager = new ChunkTimeManager(this);

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new ChunkUnloadListener(this), this);
        pluginManager.registerEvents(new ChunkLoadListener(this), this);
    }
}
