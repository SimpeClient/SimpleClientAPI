package simpleclient.api;

import org.bukkit.entity.Player;
import simpleclient.api.network.NetworkHelper;

import java.util.HashSet;
import java.util.Set;

public class SimpleClientAPI {
    /**
     * Minimum SimpleClient version supported by the plugin
     */
    public static final String MIN_SUPPORTED_VERSION = "0.2.7";
    private static final Set<Player> simpleClientPlayers = new HashSet<>();
    private static final Set<Player> blockingPlayers = new HashSet<>();
    private static boolean legacyPvPEnabled = false;

    /**
     * Checks if a player is using SimpleClient
     * @param player The player
     * @return True if the player is using SimpleClient
     */
    public static boolean isUsingSimpleClient(Player player) {
        return simpleClientPlayers.contains(player);
    }

    /**
     * Checks if a player is using SimpleClient and blocking with a sword
     * @param player The player
     * @return True if the player is using SimpleClient and blocking with a sword
     */
    public static boolean isBlockingWithSword(Player player) {
        return isUsingSimpleClient(player) && blockingPlayers.contains(player);
    }

    /**
     * Gets the players who are using SimpleClient
     * @return Set of players who are using SimpleClient
     */
    public static Set<Player> getSimpleClientPlayers() {
        return simpleClientPlayers;
    }

    /**
     * Gets the players who are using SimpleClient and blocking with a sword
     * @return Set of players who are using SimpleClient and blocking with a sword
     */
    public static Set<Player> getBlockingPlayers() {
        return blockingPlayers;
    }

    /**
     * Weather legacy PvP is enabled or not
     * @return True if legacy PvP is enabled
     */
    public static boolean isLegacyPvPEnabled() {
        return legacyPvPEnabled;
    }

    /**
     * Enabled or disables legacy PvP
     * @param enabled Weather legacy PvP should be enabled or not
     */
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