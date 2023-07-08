package simpleclient.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class SimpleClientAPI {
    public static final String MIN_SUPPORTED_VERSION = "0.2.6";
    private static final Set<Player> simpleClientPlayers = new HashSet<>();
    private static final Set<Player> blockingPlayers = new HashSet<>();
    private static boolean legacyPvPEnabled = false;

    public static boolean isUsingSimpleClient(Player player) {
        return simpleClientPlayers.contains(player);
    }

    public static boolean isBlockingWithSword(Player player) {
        return isUsingSimpleClient(player) && blockingPlayers.contains(player);
    }

    public static Set<Player> getSimpleClientPlayers() {
        return simpleClientPlayers;
    }

    public static Set<Player> getBlockingPlayers() {
        return blockingPlayers;
    }

    public static boolean isLegacyPvPEnabled() {
        return legacyPvPEnabled;
    }

    public static void setLegacyPvPEnabled(boolean enabled) {
        legacyPvPEnabled = enabled;
        Plugin plugin = SimpleClientAPIMain.getPlugin();
        if (!enabled) {
            blockingPlayers.removeIf(blocker -> {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeByte(3);
                out.writeUTF(blocker.getUniqueId().toString());
                byte[] data = out.toByteArray();
                simpleClientPlayers.forEach(p -> p.sendPluginMessage(plugin, "simpleclient:legacypvp", data));
                return true;
            });
        }
        byte[] data = new byte[] {(byte) (enabled ? 0 : 1)};
        Bukkit.getOnlinePlayers().forEach(p -> p.sendPluginMessage(plugin, "simpleclient:legacypvp", data));
    }
}