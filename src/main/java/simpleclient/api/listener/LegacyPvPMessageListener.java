package simpleclient.api.listener;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import simpleclient.api.SimpleClientAPI;
import simpleclient.api.network.NetworkHelper;

public class LegacyPvPMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (channel.equals("simpleclient:legacypvp")) {
            SimpleClientAPI.getSimpleClientPlayers().add(player);
            if (message.length >= 1) {
                if (message[0] == 0) {
                    SimpleClientAPI.getBlockingPlayers().add(player);
                    SimpleClientAPI.getSimpleClientPlayers().forEach(p -> NetworkHelper.sendBlockingPlayer(p, player, true));
                }
                if (message[0] == 1) {
                    SimpleClientAPI.getBlockingPlayers().remove(player);
                    SimpleClientAPI.getSimpleClientPlayers().forEach(p -> NetworkHelper.sendBlockingPlayer(p, player, false));
                }
            }
        }
    }
}