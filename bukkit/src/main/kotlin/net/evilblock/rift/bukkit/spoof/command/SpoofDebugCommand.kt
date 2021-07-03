package net.evilblock.rift.bukkit.spoof.command

import net.evilblock.cubed.command.Command
import net.evilblock.rift.bukkit.spoof.SpoofHandler
import org.bukkit.command.CommandSender

object SpoofDebugCommand {

    @Command(
        names = ["rs debug"],
        description = "Debug the RS system",
        permission = "op",
        async = true
    )
    @JvmStatic
    fun execute(sender: CommandSender) {
        SpoofHandler.DEBUG = !SpoofHandler.DEBUG
        sender.sendMessage("debug: ${SpoofHandler.DEBUG}")
    }

}