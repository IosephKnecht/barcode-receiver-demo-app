package com.project.iosephknecht.barcode_receiver_rxjava

import com.project.iosephknecht.barcode_receiver_api.BarcodeReceiverObserver
import com.project.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver
import io.reactivex.rxjava3.subjects.PublishSubject

internal class DefaultBarcodeReceiver : MutableBarcodeReceiver {

    private val subject: PublishSubject<String> = PublishSubject.create()

    override fun accept(barcode: String) {
        subject.onNext(barcode)
    }

    override fun subscribe(observer: BarcodeReceiverObserver) {
        subject.subscribe(DefaultBarcodeReceiverObserver(observer))
    }
}