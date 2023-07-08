package simpleclient.api.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import simpleclient.api.SimpleClientAPI;
import simpleclient.api.SimpleClientAPIMain;
import simpleclient.api.network.NetworkHelper;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(SimpleClientAPIMain.getPlugin(), () -> {
            if (SimpleClientAPI.isUsingSimpleClient(p)) {
                NetworkHelper.sendLegacyPvPEnabled(p, SimpleClientAPI.isLegacyPvPEnabled());
                NetworkHelper.sendBlockingPlayers(p);
            }
        }, 20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SimpleClientAPI.isUsingSimpleClient(p)) {
            NetworkHelper.sendLegacyPvPEnabled(p, false);
            SimpleClientAPI.getSimpleClientPlayers().remove(p);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();
        if (SimpleClientAPI.isUsingSimpleClient(p)) {
            NetworkHelper.sendLegacyPvPEnabled(p, false);
            SimpleClientAPI.getSimpleClientPlayers().remove(p);
        }
    }
}