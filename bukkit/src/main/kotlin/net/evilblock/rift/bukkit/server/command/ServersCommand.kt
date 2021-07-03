package net.evilblock.rift.bukkit.server.command

import net.evilblock.rift.bukkit.server.menu.ServersMenu
import net.evilblock.rift.bukkit.util.Permissions
import net.evilblock.cubed.command.Command
import org.bukkit.entity.Player

object ServersCommand {

    @Command(
        names = ["rift servers"],
        description = "Opens a display of all servers",
        permission = Permissions.SERVER_EDITOR
    )
    @JvmStatic
    fun execute(player: Player) {
        ServersMenu().openMenu(player)
    }

}