package simpleclient.api.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import simpleclient.api.SimpleClientAPI;
import simpleclient.api.SimpleClientAPIMain;

public class HandshakeMessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (channel.equals("simpleclient:handshake")) {
            String clientVersion = new String(message);
            if (compareVersions(SimpleClientAPI.MIN_SUPPORTED_VERSION, clientVersion)) {
                SimpleClientAPI.getSimpleClientPlayers().add(player);
            }
        }
    }

    private boolean compareVersions(String serverVersionName, String clientVersionName) {
        String[] serverVersion = serverVersionName.split("\\.");
        String[] clientVersion = clientVersionName.split("\\.");
        for (int i = 0, j = Math.min(serverVersion.length, clientVersion.length); i < j; i++) {
            int serverVersionPart = Integer.parseInt(serverVersion[i]);
            int clientVersionPart = Integer.parseInt(clientVersion[i]);
            if (clientVersionPart > serverVersionPart) return true;
            if (clientVersionPart < serverVersionPart) return false;
        }
        return clientVersion.length >= serverVersion.length;
    }
}