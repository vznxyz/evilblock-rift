package net.evilblock.rift.bukkit.server.task

import net.evilblock.rift.bukkit.RiftBukkitPlugin
import net.evilblock.rift.server.ServerHandler
import net.evilblock.cubed.util.minecraft.MinecraftReflection
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class ServerUpdateTask : BukkitRunnable() {

    override fun run() {
        val server = RiftBukkitPlugin.instance.serverInstance

        server.slots = Bukkit.getMaxPlayers()
        server.whitelisted = Bukkit.hasWhitelist()
        server.onlineMode = Bukkit.getOnlineMode()
        server.proxied = RiftBukkitPlugin.instance.readBungeeEnabled()
        server.lastHeartbeat = System.currentTimeMillis()
        server.currentUptime = System.currentTimeMillis() - net.evilblock.rift.bukkit.RiftBukkitPlugin.enabledAt
        server.currentTps = MinecraftReflection.getTPS()
        server.playerCount = Bukkit.getOnlinePlayers().size

        ServerHandler.saveServer(server)
    }

}