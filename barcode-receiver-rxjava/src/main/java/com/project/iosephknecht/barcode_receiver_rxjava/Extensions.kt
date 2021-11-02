package com.project.iosephknecht.barcode_receiver_rxjava

import com.project.iosephknecht.barcode_receiver_api.BarcodeReceiverObserver
import com.project.iosephknecht.barcode_receiver_api.ImmutableBarcodeReceiver
import com.project.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver
import com.project.iosephknecht.barcode_receiver_api.Subscription
import com.project.iosephknecht.barcode_receiver_rxjava.DefaultBarcodeReceiver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

@Suppress("FunctionName")
fun RxJavaMutableBarcodeReceiver(): MutableBarcodeReceiver = DefaultBarcodeReceiver()

@Suppress("UNUSED")
val ImmutableBarcodeReceiver.barcodes: Observable<String>
    get() = Observable.wrap { observer ->
        subscribe(
            object : BarcodeReceiverObserver {
                override fun onNext(barcode: String) {
                    observer.onNext(barcode)
                }

                override fun onError(throwable: Throwable) {
                    observer.onError(throwable)
                }

                override fun onComplete() {
                    observer.onComplete()
                }

                override fun onSubscribed(subscription: Subscription) {
                    observer.onSubscribe(object : Disposable {
                        override fun dispose() {
                            subscription.unsubscribe()
                        }

                        override fun isDisposed(): Boolean = subscription.isSubscribed()
                    })
                }
            }
        )
    }