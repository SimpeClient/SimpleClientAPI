package simpleclient.api.network;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import simpleclient.api.SimpleClientAPI;
import simpleclient.api.SimpleClientAPIMain;

public class NetworkHelper {
    public static void sendLegacyPvPEnabled(Player p, boolean enabled) {
        byte[] data = new byte[] {(byte) (enabled ? 0 : 1)};
        p.sendPluginMessage(SimpleClientAPIMain.getPlugin(), "simpleclient:legacypvp", data);
    }

    public static void sendBlockingPlayer(Player p, Player blocker, boolean blocking) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(blocking ? 2 : 3);
        out.writeLong(blocker.getUniqueId().getMostSignificantBits());
        out.writeLong(blocker.getUniqueId().getLeastSignificantBits());
        byte[] data = out.toByteArray();
        p.sendPluginMessage(SimpleClientAPIMain.getPlugin(), "simpleclient:legacypvp", data);
    }

    public static void sendBlockingPlayers(Player p) {
        SimpleClientAPI.getBlockingPlayers().forEach(blocker -> sendBlockingPlayer(p, blocker, true));
    }
}