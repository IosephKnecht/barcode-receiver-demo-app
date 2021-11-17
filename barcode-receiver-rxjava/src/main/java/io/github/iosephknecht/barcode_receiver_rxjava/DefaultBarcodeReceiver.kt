package io.github.iosephknecht.barcode_receiver_rxjava

import io.github.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Default implementation [MutableBarcodeReceiver].
 *
 * @author IosephKnecht
 */
internal class DefaultBarcodeReceiver : MutableBarcodeReceiver {

    private val subject: PublishSubject<String> = PublishSubject.create()

    override fun accept(barcode: String) {
        subject.onNext(barcode)
    }

    override fun subscribe(observer: Observer<in String>) {
        subject.subscribe(observer)
    }
}