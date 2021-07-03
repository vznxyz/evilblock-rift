package net.evilblock.rift.bukkit.spoof.event

import net.evilblock.rift.bukkit.spoof.v1_8_R3.FakeEntityPlayer
import net.evilblock.cubed.util.bukkit.plugin.PluginEvent
import org.bukkit.event.Cancellable

class SpoofPlayerAddEvent(val fakePlayer: FakeEntityPlayer) : PluginEvent(), Cancellable {

    private var cancelled: Boolean = false

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancelled: Boolean) {
        this.cancelled = cancelled
    }

}