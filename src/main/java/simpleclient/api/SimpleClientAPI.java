package simpleclient.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import simpleclient.api.network.NetworkHelper;

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
        if (!enabled) {
            blockingPlayers.removeIf(blocker -> {
                simpleClientPlayers.forEach(p -> NetworkHelper.sendBlockingPlayer(p, blocker, false));
                return true;
            });
        }
        simpleClientPlayers.forEach(p -> NetworkHelper.sendLegacyPvPEnabled(p, enabled));
    }
}