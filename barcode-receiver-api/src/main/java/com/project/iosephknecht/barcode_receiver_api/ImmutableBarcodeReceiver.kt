package com.project.iosephknecht.barcode_receiver_api

interface ImmutableBarcodeReceiver {

    fun subscribe(observer: BarcodeReceiverObserver)
}