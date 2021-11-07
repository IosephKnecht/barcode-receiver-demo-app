package com.project.iosephknecht.barcode_receiver_api

import io.reactivex.rxjava3.functions.Consumer

/**
 * Contract hot - observable source.
 *
 * @author IosephKnecht
 */
interface MutableBarcodeReceiver : ImmutableBarcodeReceiver, Consumer<String>