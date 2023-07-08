package simpleclient.api.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import simpleclient.api.SimpleClientAPI;
import simpleclient.api.SimpleClientAPIMain;

public class ConnectionListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player blocker : SimpleClientAPI.getBlockingPlayers()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeByte(2);
            out.writeUTF(blocker.getUniqueId().toString());
            byte[] data = out.toByteArray();
            p.sendPluginMessage(SimpleClientAPIMain.getPlugin(), "simpleclient:legacypvp", data);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (SimpleClientAPI.isUsingSimpleClient(p)) {
            p.sendPluginMessage(SimpleClientAPIMain.getPlugin(), "simpleclient:legacypvp", new byte[] {1});
            SimpleClientAPI.getSimpleClientPlayers().remove(p);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();
        if (SimpleClientAPI.isUsingSimpleClient(p)) {
            p.sendPluginMessage(SimpleClientAPIMain.getPlugin(), "simpleclient:legacypvp", new byte[] {1});
            SimpleClientAPI.getSimpleClientPlayers().remove(p);
        }
    }
}