package net.evilblock.rift.bukkit.queue.event

import net.evilblock.rift.queue.Queue
import net.evilblock.cubed.util.bukkit.plugin.PluginEvent
import org.bukkit.entity.Player

open class PlayerJoinQueueEvent(val player: Player, val queue: Queue) : PluginEvent()