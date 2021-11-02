package com.project.iosephknecht.barcode_receiver_rxjava

import com.project.iosephknecht.barcode_receiver_api.BarcodeReceiverObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

internal class DefaultBarcodeReceiverObserver(
    private val observer: BarcodeReceiverObserver
) : Observer<String> {

    override fun onNext(barcode: String) {
        observer.onNext(barcode)
    }

    override fun onError(throwable: Throwable) {
        observer.onError(throwable)
    }

    override fun onComplete() {
        observer.onComplete()
    }

    override fun onSubscribe(d: Disposable) {
        observer.onSubscribed(DefaultBarcodeReceiverSubscription(d))
    }
}