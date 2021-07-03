package net.evilblock.rift.bukkit.server.group.menu

import net.evilblock.rift.bukkit.server.menu.EditServerMenu
import net.evilblock.rift.bukkit.server.menu.button.ServerButton
import net.evilblock.rift.server.Server
import net.evilblock.rift.server.ServerGroup
import net.evilblock.rift.server.ServerHandler
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.menus.ConfirmMenu
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView

class GroupServersMenu(private val group: ServerGroup) : PaginatedMenu() {

    init {
        updateAfterClick = true
    }

    override fun getPrePaginatedTitle(player: Player): String {
        return "Servers - ${group.displayName}"
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            for (server in group.servers) {
                buttons[buttons.size] = GroupServerButton(server)
            }
        }
    }

    override fun getMaxItemsPerPage(player: Player): Int {
        return 45
    }

    override fun onClose(player: Player, manualClose: Boolean) {
        if (manualClose) {
            Tasks.delayed(1L) {
                EditGroupMenu(group).openMenu(player)
            }
        }
    }

    private inner class GroupServerButton(server: Server) : ServerButton(server) {
        override fun getDescription(player: Player): List<String> {
            return (super.getDescription(player) as MutableList<String>).also { desc ->
                desc.add("")
                desc.add(styleAction(ChatColor.GREEN, "LEFT-CLICK", "to edit server"))
                desc.add(styleAction(ChatColor.RED, "RIGHT-CLICK", "to delete server"))
            }
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            if (clickType.isLeftClick) {
                EditServerMenu(this@GroupServersMenu, server).openMenu(player)
            } else if (clickType.isRightClick) {
                ConfirmMenu { confirmed ->
                    if (confirmed) {
                        Tasks.async {
                            group.servers.remove(server)
                            ServerHandler.servers.remove(server.id.toLowerCase())
                            ServerHandler.deleteServer(server)
                        }

                        this@GroupServersMenu.openMenu(player)
                    }
                }.openMenu(player)
            }
        }
    }

}