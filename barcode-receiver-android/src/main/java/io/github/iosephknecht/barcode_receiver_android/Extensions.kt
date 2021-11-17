@file:Suppress("UNUSED")

package io.github.iosephknecht.barcode_receiver_android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.github.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver

/**
 * Extension - function to connect [MutableBarcodeReceiver] with [androidx.lifecycle.Lifecycle].
 *
 * @param key barcode receiver key.
 * @param activity lifecycle owner.
 * @param subscribeStrategy by default [AlwaysSubscribeStrategy].
 *
 * @author IosephKnecht
 */
fun MutableBarcodeReceiver.manageByLifecycle(
    key: String,
    activity: FragmentActivity,
    subscribeStrategy: SubscribeStrategy = AlwaysSubscribeStrategy
) {
    activity.lifecycle.addObserver(
        BarcodeReceiverLifecycleObserver(
            key = key,
            barcodeReceiver = this,
            activity = activity,
            subscribeStrategy = subscribeStrategy
        )
    )
}

/**
 * Extension - function to connect [MutableBarcodeReceiver] with [androidx.lifecycle.Lifecycle].
 *
 * @param key barcode receiver key.
 * @param fragment lifecycle owner.
 * @param subscribeStrategy by default [AlwaysSubscribeStrategy].
 *
 * @author IosephKnecht
 */
fun MutableBarcodeReceiver.manageByLifecycle(
    key: String,
    fragment: Fragment,
    subscribeStrategy: SubscribeStrategy = AlwaysSubscribeStrategy
) {
    val activity = fragment.requireActivity()

    manageByLifecycle(key, activity, subscribeStrategy)
}