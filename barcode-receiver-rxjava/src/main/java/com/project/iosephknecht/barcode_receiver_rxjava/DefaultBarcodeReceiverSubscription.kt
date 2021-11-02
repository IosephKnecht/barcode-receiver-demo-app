package com.project.iosephknecht.barcode_receiver_rxjava

import com.project.iosephknecht.barcode_receiver_api.Subscription
import io.reactivex.rxjava3.disposables.Disposable

internal class DefaultBarcodeReceiverSubscription(
    private val disposable: Disposable
) : Subscription {

    override fun isSubscribed(): Boolean = disposable.isDisposed

    override fun unsubscribe() = disposable.dispose()
}