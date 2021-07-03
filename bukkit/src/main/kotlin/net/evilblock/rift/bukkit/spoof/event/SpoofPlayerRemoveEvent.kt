package net.evilblock.rift.bukkit.spoof.event

import net.evilblock.rift.bukkit.spoof.v1_8_R3.FakeEntityPlayer
import net.evilblock.cubed.util.bukkit.plugin.PluginEvent

class SpoofPlayerRemoveEvent(val fakePlayer: FakeEntityPlayer) : PluginEvent()