package net.evilblock.rift.proxy

import net.evilblock.rift.Rift
import net.evilblock.pidgin.message.Message
import java.util.concurrent.ConcurrentHashMap

object ProxyHandler {

    const val PROXY_UPDATE = "ProxyUpdate"

    private val proxies: MutableMap<String, net.evilblock.rift.proxy.Proxy> = ConcurrentHashMap()

    fun initialLoad() {
        loadProxies()
    }

    /**
     * Retrieves a list of [Proxy]s from Redis.
     */
    private fun loadProxies() {
        try {
            Rift.instance.plugin.getRedis().runRedisCommand { redis ->
                for (proxyId in redis.smembers("Rift:Proxies")) {
                    val proxy = fetchProxyById(proxyId) ?: continue
                    proxies[proxy.id.toLowerCase()] = proxy
                }
            }
        } catch (e: Exception) {
            Rift.instance.plugin.getLogger().severe("Failed to load proxies!")
            e.printStackTrace()
        }
    }

    /**
     * Retrieves the [Proxy]s that are currently cached.
     */
    fun getProxies(): Collection<net.evilblock.rift.proxy.Proxy> {
        return proxies.values
    }

    /**
     * Attempts to retrieve a cached [Proxy] for the given [id].
     */
    fun getProxyById(id: String, ignoreCase: Boolean = true): net.evilblock.rift.proxy.Proxy? {
        return proxies[id.toLowerCase()]
    }

    /**
     * Attempts to retrieve a [Proxy] from Redis.
     */
    fun fetchProxyById(id: String): net.evilblock.rift.proxy.Proxy? {
        return Rift.instance.plugin.getRedis().runRedisCommand { client ->
            val map = client.hgetAll("Rift:Proxy:$id")
            if (map.isEmpty()) {
                return@runRedisCommand null
            } else {
                return@runRedisCommand net.evilblock.rift.proxy.Proxy(map)
            }
        }
    }

    /**
     * Saves the given [proxy] to Redis.
     */
    fun saveProxy(proxy: net.evilblock.rift.proxy.Proxy) {
        Rift.instance.plugin.getRedis().runRedisCommand { redis ->
            redis.sadd("Rift:Proxies", proxy.id)
            redis.hmset("Rift:Proxy:${proxy.id}", proxy.toMap())
        }

        Rift.instance.mainChannel.sendMessage(Message(PROXY_UPDATE, mapOf("Proxy" to proxy.id)))
    }

    /**
     * Deletes the given [proxy] from Redis.
     */
    fun deleteProxy(proxy: net.evilblock.rift.proxy.Proxy) {
        Rift.instance.plugin.getRedis().runRedisCommand { redis ->
            redis.srem("Rift:Proxies", proxy.id)
            redis.del("Rift:Proxy:${proxy.id}")
        }
    }

    /**
     * Attempts to retrieve a [Proxy] for the given [id] from Redis. If the key does not exist,
     * a new [Proxy] is created, saved, and returned.
     */
    fun loadOrCreateProxy(id: String): net.evilblock.rift.proxy.Proxy {
        return Rift.instance.plugin.getRedis().runRedisCommand { redis ->
            val exists = redis.exists("Rift:Proxy:$id")

            val proxy = if (exists) {
                net.evilblock.rift.proxy.Proxy(redis.hgetAll("Rift:Proxy:$id"))
            } else {
                net.evilblock.rift.proxy.Proxy(id)
            }

            if (!exists) {
                saveProxy(proxy)
            }

            proxies[proxy.id.toLowerCase()] = proxy

            return@runRedisCommand proxy
        }
    }

    fun getTotalPlayerCount(): Int {
        return proxies.values.sumBy { it.playerCount }
    }

}