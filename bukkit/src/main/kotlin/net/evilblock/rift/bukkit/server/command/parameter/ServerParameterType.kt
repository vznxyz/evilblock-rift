package net.evilblock.rift.bukkit.server.command.parameter

import net.evilblock.cubed.command.data.parameter.ParameterType
import net.evilblock.rift.bukkit.RiftBukkitPlugin
import net.evilblock.rift.server.Server
import net.evilblock.rift.server.ServerHandler
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ServerParameterType : ParameterType<Server> {

    override fun transform(sender: CommandSender, source: String): Server? {
        if (sender is Player && source.equals("self", ignoreCase = true)) {
            return RiftBukkitPlugin.instance.serverInstance
        }

        val server = ServerHandler.getServerById(source, ignoreCase = true)
        if (server == null) {
            sender.sendMessage("${ChatColor.RED}Couldn't find a server from the input: `${ChatColor.RESET}$source${ChatColor.RED}`")
        }

        return server
    }

    override fun tabComplete(player: Player, flags: Set<String>, source: String): List<String> {
        val completions = arrayListOf<String>()

        for (server in ServerHandler.getServers()) {
            if (server.id.startsWith(source, ignoreCase = true)) {
                completions.add(server.id)
            }
        }

        return completions
    }

}