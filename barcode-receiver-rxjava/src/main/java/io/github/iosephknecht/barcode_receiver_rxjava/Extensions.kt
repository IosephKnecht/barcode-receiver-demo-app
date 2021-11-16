@file:Suppress("UNUSED")

package io.github.iosephknecht.barcode_receiver_rxjava

import io.github.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver

/**
 * Extension - function for get [MutableBarcodeReceiver].
 *
 * @author IosephKnecht
 */
@Suppress("FunctionName")
fun RxJavaMutableBarcodeReceiver(): MutableBarcodeReceiver = DefaultBarcodeReceiver()