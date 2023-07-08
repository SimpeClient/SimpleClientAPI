package simpleclient.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import simpleclient.api.listener.ConnectionListener;
import simpleclient.api.listener.HandshakeMessageListener;
import simpleclient.api.listener.LegacyPvPMessageListener;

public final class SimpleClientAPIMain extends JavaPlugin {
    private static SimpleClientAPIMain plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "simpleclient:handshake");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "simpleclient:handshake", new HandshakeMessageListener());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "simpleclient:legacypvp");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "simpleclient:legacypvp", new LegacyPvPMessageListener());
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "simpleclient:handshake");
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this, "simpleclient:handshake");
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "simpleclient:legacypvp");
        Bukkit.getMessenger().unregisterIncomingPluginChannel(this, "simpleclient:legacypvp");
    }

    public static SimpleClientAPIMain getPlugin() {
        return plugin;
    }
}