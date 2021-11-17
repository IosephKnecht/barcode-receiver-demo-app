package io.github.iosephknecht.barcode_receiver_api

import io.reactivex.rxjava3.core.ObservableSource

/**
 * Contract [ObservableSource] for receive barcodes.
 *
 * @author IosephKnecht
 */
interface ImmutableBarcodeReceiver : ObservableSource<String>