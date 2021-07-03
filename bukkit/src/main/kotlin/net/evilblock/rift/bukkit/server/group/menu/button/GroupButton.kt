package net.evilblock.rift.bukkit.server.group.menu.button

import net.evilblock.rift.server.ServerGroup
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.math.Numbers
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

open class GroupButton(protected val group: ServerGroup) : Button() {

    override fun getName(player: Player): String {
        return "${ChatColor.BLUE}${ChatColor.BOLD}${group.displayName}"
    }

    override fun getDescription(player: Player): List<String> {
        return arrayListOf<String>().also { desc ->
            desc.add(buildString {
                append("${ChatColor.GRAY}Servers: ")
                append("${ChatColor.GREEN}${Numbers.format(group.getOnlineServers().size)}")
                append("/${Numbers.format(group.servers.size)}")
            })

            desc.add("${ChatColor.GRAY}Players: ${ChatColor.GREEN}${Numbers.format(group.getOnlineServersPlayerCount())}")
        }
    }

    override fun getMaterial(player: Player): Material {
        return Material.HOPPER
    }

}