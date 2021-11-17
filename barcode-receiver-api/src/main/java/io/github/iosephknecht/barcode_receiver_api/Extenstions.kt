package io.github.iosephknecht.barcode_receiver_api

import io.reactivex.rxjava3.core.Observable

/**
 * Extension - value for get [Observable] from [MutableBarcodeReceiver].
 *
 * Example of usage:
 * ```
 * class SomeClass {
 *
 *  @set:Inject
 *  lateinit var mutableBarcodeReceiver:MutableBarcodeReceiver
 *
 *  fun main() {
 *      mutableBarcodeReceiver.barcodes
 *          .subscribe {
 *              // do something
 *          }
 *  }
 * }
 * ```
 *
 * @author IosephKnecht
 */
val ImmutableBarcodeReceiver.barcodes: Observable<String>
    get() = Observable.wrap(::subscribe)