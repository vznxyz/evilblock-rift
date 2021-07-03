package net.evilblock.rift

import net.evilblock.rift.server.ServerMessages
import net.evilblock.rift.server.ServerHandler
import net.evilblock.pidgin.Pidgin
import net.evilblock.pidgin.PidginOptions
import net.evilblock.rift.plugin.Plugin
import net.evilblock.rift.proxy.ProxyHandler
import net.evilblock.rift.proxy.ProxyMessages
import net.evilblock.rift.queue.QueueHandler
import net.evilblock.rift.queue.QueueMessages
import net.evilblock.cubed.serializers.Serializers

class Rift(val plugin: Plugin) {

    companion object {
        @JvmStatic lateinit var instance: Rift
    }

    lateinit var mainChannel: Pidgin
    lateinit var proxyChannel: Pidgin

    init {
        instance = this
    }

    fun initialLoad() {
        mainChannel = Pidgin("Rift-Main", plugin.getRedis().jedisPool!!, Serializers.gson, PidginOptions(async = true))

        ProxyHandler.initialLoad()
        ServerHandler.initialLoad()
        QueueHandler.initialLoad()

        mainChannel.registerListener(ProxyMessages)
        mainChannel.registerListener(ServerMessages)
        mainChannel.registerListener(QueueMessages)

        proxyChannel = Pidgin("Rift-Proxy", plugin.getRedis().jedisPool!!, Serializers.gson, PidginOptions(async = true))
    }

}