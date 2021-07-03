package net.evilblock.rift.bukkit.event

import net.evilblock.cubed.util.bukkit.plugin.PluginEvent
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class PrePlayerJumpToLobbyEvent(val player: Player) : PluginEvent(), Cancellable {

    private var cancelled: Boolean = false

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancelled: Boolean) {
        this.cancelled = cancelled
    }

}