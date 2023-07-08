package simpleclient.api.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import simpleclient.api.SimpleClientAPI;
import simpleclient.api.SimpleClientAPIMain;

public class LegacyPvPMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        Plugin plugin = SimpleClientAPIMain.getPlugin();
        if (channel.equals("simpleclient:legacypvp")) {
            SimpleClientAPI.getSimpleClientPlayers().add(player);
            if (message.length >= 1) {
                if (message[0] == 0) {
                    SimpleClientAPI.getBlockingPlayers().add(player);
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeByte(2);
                    out.writeUTF(player.getUniqueId().toString());
                    byte[] data = out.toByteArray();
                    SimpleClientAPI.getSimpleClientPlayers().forEach(p -> p.sendPluginMessage(plugin, "simpleclient:legacypvp", data));
                }
                if (message[0] == 1) {
                    SimpleClientAPI.getBlockingPlayers().remove(player);
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeByte(3);
                    out.writeUTF(player.getUniqueId().toString());
                    byte[] data = out.toByteArray();
                    SimpleClientAPI.getSimpleClientPlayers().forEach(p -> p.sendPluginMessage(plugin, "simpleclient:legacypvp", data));
                }
            }
        }
    }
}