package net.evilblock.rift.bukkit.spoof.command

import net.evilblock.cubed.command.Command
import net.evilblock.cubed.command.data.parameter.Param
import net.evilblock.rift.bukkit.RiftBukkitPlugin
import org.bukkit.command.CommandSender

object SpoofMultiCommand {

    @Command(
        names = ["rs mx", "rs multi"],
        permission = "op",
        description = "Sets GS multi"
    )
    @JvmStatic
    fun execute(sender: CommandSender, @Param(name = "multi") multi: Double) {
        RiftBukkitPlugin.instance.setSpoofMultiplier(multi)
        sender.sendMessage("multi: ${RiftBukkitPlugin.instance.readSpoofMultiplier()}")
    }

}