package net.evilblock.rift.plugin

import net.evilblock.cubed.store.redis.Redis
import net.evilblock.rift.queue.Queue
import net.evilblock.rift.queue.QueueEntry
import java.io.File
import java.util.logging.Logger

interface Plugin {

    fun getLogger(): Logger

    fun getDirectory(): File

    fun getRedis(): Redis

    fun hasPresence(): Boolean

    fun getInstanceID(): String

    fun onJoinQueue(queue: Queue, entry: QueueEntry) {

    }

    fun onLeaveQueue(queue: Queue, entry: QueueEntry) {

    }

}