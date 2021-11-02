package com.project.iosephknecht.barcode_receiver_api

interface BarcodeReceiverObserver {

    fun onNext(barcode: String)

    fun onError(throwable: Throwable)

    fun onComplete()

    fun onSubscribed(subscription: Subscription)
}