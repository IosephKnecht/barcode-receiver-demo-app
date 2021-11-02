@file:Suppress("UNUSED")

package com.project.iosephknecht.barcode_receiver_android

import com.example.barcode_receiver_android.BuildConfig

sealed interface SubscribeStrategy {

    fun check(): Boolean
}

interface LeafSubscribeStrategy : SubscribeStrategy

interface CompositeSubscribeStrategy : SubscribeStrategy

object DebugSubscribeStrategy : LeafSubscribeStrategy {
    override fun check(): Boolean = BuildConfig.DEBUG
}

object EmulatorSubscribeStrategy : LeafSubscribeStrategy {
    override fun check(): Boolean = DeviceUtils.isEmulator()
}

class OrSubscribeStrategy(
    private val leftStrategy: SubscribeStrategy,
    private val rightStrategy: SubscribeStrategy
) : CompositeSubscribeStrategy {

    override fun check(): Boolean {
        return leftStrategy.check() || rightStrategy.check()
    }
}

class AndSubscribeStrategy(
    private val leftStrategy: SubscribeStrategy,
    private val rightStrategy: SubscribeStrategy
) : CompositeSubscribeStrategy {

    override fun check(): Boolean {
        return leftStrategy.check() && rightStrategy.check()
    }
}