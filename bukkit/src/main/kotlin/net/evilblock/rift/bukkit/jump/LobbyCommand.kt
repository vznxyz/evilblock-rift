package net.evilblock.rift.bukkit.jump

import net.evilblock.rift.Rift
import net.evilblock.rift.bukkit.event.PlayerJumpToLobbyEvent
import net.evilblock.rift.bukkit.event.PrePlayerJumpToLobbyEvent
import net.evilblock.cubed.command.Command
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.pidgin.message.Message
import org.bukkit.entity.Player

object LobbyCommand {

    @Command(
        names = ["lobby", "hub"],
        description = "Switch to a lobby server"
    )
    @JvmStatic
    fun execute(player: Player) {
        val preLobbyEvent = PrePlayerJumpToLobbyEvent(player)
        if (!preLobbyEvent.call()) {
            return
        }

        PlayerJumpToLobbyEvent(player).call()

        Tasks.async {
            Rift.instance.proxyChannel.sendMessage(Message(
                id = "SendToLobby",
                data = mapOf(
                    "Player" to player.uniqueId.toString()
                )
            ))
        }
    }

}