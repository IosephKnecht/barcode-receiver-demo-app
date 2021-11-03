@file:Suppress("UNUSED")

package com.project.iosephknecht.barcode_receiver_android

import androidx.annotation.MainThread
import androidx.annotation.Size

/**
 * Contract for subscribe strategy.
 *
 * Strategies are used to understand whether applications need
 * to be signed to listen for barcode events.
 *
 * @author IosephKnecht
 */
fun interface SubscribeStrategy {

    @MainThread
    fun check(): Boolean
}

/**
 * Stub - strategy for subscribing always.
 *
 * @author IosephKnecht
 */
object AlwaysSubscribeStrategy : SubscribeStrategy {
    override fun check(): Boolean = true
}

/**
 * Strategy for subscribing only if device is an emulator.
 *
 * @author IosephKnecht
 */
object EmulatorSubscribeStrategy : SubscribeStrategy {
    override fun check(): Boolean = DeviceUtils.isEmulator()
}

/**
 * Composite strategy via condition OR.
 *
 * @param strategies array of strategies.
 *
 * @author IosephKnecht
 */
class OrSubscribeStrategy(
    @Size(min = 2)
    private val strategies: Array<SubscribeStrategy>
) : SubscribeStrategy {

    init {
        require(strategies.size > 1) { "array of strategies must have at least two strategies." }
    }

    override fun check(): Boolean {
        return strategies.any { subscribeStrategy -> subscribeStrategy.check() }
    }
}

/**
 * Composite strategy via condition AND.
 *
 * @param strategies array of strategies.
 *
 * @author IosephKnecht
 */
class AndSubscribeStrategy(
    @Size(min = 2)
    private val strategies: Array<SubscribeStrategy>
) : SubscribeStrategy {

    init {
        require(strategies.size > 1) { "array of strategies must have at least two strategies." }
    }

    override fun check(): Boolean {
        return !strategies.any { subscribeStrategy -> !subscribeStrategy.check() }
    }
}