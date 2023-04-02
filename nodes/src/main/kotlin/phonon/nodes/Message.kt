package phonon.nodes

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent

/**
 * Helper functions for printing messages for players.
 */
public object Message {

    public val PREFIX = "[Nodes]"
    public val COL_MSG = ChatColor.AQUA
    public val COL_ERROR = ChatColor.RED

    /**
     * Print generic plugin message to command sender's chat (either console
     * or player).
     */
    public fun print(sender: CommandSender?, s: String) {
		if ( sender === null ) {
            System.out.println("${PREFIX} Message called with null sender: ${s}")
            return
		}

        val msg = "${COL_MSG}${s}"
        if ( sender is Player ) {
            (sender as Player).sendMessage(msg)
        }
        else {
            (sender as CommandSender).sendMessage(msg)
        }
    }

    /**
     * Print error message to a command sender's chat (either console or player).
     */
    public fun error(sender: CommandSender?, s: String) {
		if ( sender === null ) {
            System.out.println("${PREFIX} Message called with null sender: ${s}")
            return
		}

        val msg = "${COL_ERROR}${s}"
        if ( sender is Player ) {
            (sender as Player).sendMessage(msg)
        }
        else {
            (sender as CommandSender).sendMessage(msg)
        }
    }

    /**
     * Wrapper around Bukkit.broadcast to send plugin formatted messages
     * to all players.
     */
    public fun broadcast(s: String) {
        val msg = "${COL_MSG}${s}"
        Bukkit.broadcastMessage(msg)
    }

    /**
     * Wrapper around paper sendActionBar to message to player's action bar
     * above hotbar.
     */
    public fun announcement(player: Player, s: String) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(s));
    }
}