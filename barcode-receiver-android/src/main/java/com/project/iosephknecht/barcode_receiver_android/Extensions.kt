package com.project.iosephknecht.barcode_receiver_android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.project.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver

@Suppress("UNUSED")
fun MutableBarcodeReceiver.subscribe(
    key: String,
    activity: FragmentActivity,
    subscribeStrategy: SubscribeStrategy = DebugSubscribeStrategy
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

@Suppress("UNUSED")
fun MutableBarcodeReceiver.subscribe(
    key: String,
    fragment: Fragment,
    subscribeStrategy: SubscribeStrategy = DebugSubscribeStrategy
) {
    val activity = fragment.requireActivity()

    subscribe(key, activity, subscribeStrategy)
}