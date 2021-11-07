@file:Suppress("UNUSED")

package com.project.iosephknecht.barcode_receiver_rxjava

import com.project.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver

/**
 * Extension - function for get [MutableBarcodeReceiver].
 *
 * @author IosephKnecht
 */
@Suppress("FunctionName")
fun RxJavaMutableBarcodeReceiver(): MutableBarcodeReceiver = DefaultBarcodeReceiver()