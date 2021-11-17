package io.github.iosephknecht.barcode_receiver_android

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.github.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver

/**
 * Implementation [DefaultLifecycleObserver] for listening to events from broadcast receiver
 * and transmitting barcode to [MutableBarcodeReceiver].
 *
 * @param key barcode receiver key.
 * @param barcodeReceiver implementation of [MutableBarcodeReceiver].
 * @param activity
 * @param subscribeStrategy
 *
 * @author IosephKnecht
 */
internal class BarcodeReceiverLifecycleObserver(
    private val key: String,
    private val barcodeReceiver: MutableBarcodeReceiver,
    private val activity: Activity,
    private val subscribeStrategy: SubscribeStrategy
) : DefaultLifecycleObserver {

    init {
        require(key.isNotBlank()) { "must be not empty" }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            intent?.getStringExtra("EXTRA_STRING_BARCODE")
                ?.let(barcodeReceiver::accept)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        if (!subscribeStrategy.check()) {
            Log.i("BarcodeReceiver", "broadcast receiver not register")
            return
        }

        activity.registerReceiver(
            broadcastReceiver,
            IntentFilter(key)
        )
    }

    override fun onPause(owner: LifecycleOwner) {
        activity.unregisterReceiver(broadcastReceiver)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}