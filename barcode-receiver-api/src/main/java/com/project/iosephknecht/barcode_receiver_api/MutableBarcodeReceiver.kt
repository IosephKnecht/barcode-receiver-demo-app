package com.project.iosephknecht.barcode_receiver_api

interface MutableBarcodeReceiver : ImmutableBarcodeReceiver {

    fun accept(barcode: String)
}