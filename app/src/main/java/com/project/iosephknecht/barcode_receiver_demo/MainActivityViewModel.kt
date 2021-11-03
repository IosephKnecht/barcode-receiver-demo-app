package com.project.iosephknecht.barcode_receiver_demo

import android.util.Log
import androidx.annotation.IntRange
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * ViewModel with dummy barcode handling.
 *
 * @author IosephKnecht
 */
internal class MainActivityViewModel(
    @IntRange(from = 1L)
    private val timeout: Long = 3L
) : ViewModel() {

    private val barcodeSubject: PublishSubject<String> = PublishSubject.create()

    private val waitingBarcodeStringResource = StringResource.Resource(R.string.app_waiting_barcode)
    private val successfullyBarcodeStringResource =
        StringResource.Resource(R.string.app_barcode_received_successfully)
    private val successfullyBarcodesStringResource =
        StringResource.Resource(R.string.app_barcodes_received_successfully)

    private val disposable = CompositeDisposable()

    val title = MutableLiveData<StringResource?>(waitingBarcodeStringResource)
    val barcode = MutableLiveData<String?>()

    init {
        barcodeSubject.switchMap { receivedBarcode ->
            Observable.fromCallable { receivedBarcode.split(BARCODE_LIST_SEPARATOR) }
                .map { barcodes ->
                    when (barcodes.size) {
                        0, 1 -> Result.ReceivedBarcode(barcodes.firstOrNull().orEmpty())
                        else -> Result.ReceivedBarcodes(receivedBarcode)
                    }
                }
                .mergeWith(Observable.just(Result.CloseBarcode).delay(timeout, TimeUnit.SECONDS))
        }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                title.value = waitingBarcodeStringResource
                barcode.value = null
            }
            .subscribe(
                { result ->
                    title.value = when (result) {
                        Result.CloseBarcode -> waitingBarcodeStringResource
                        is Result.ReceivedBarcode -> successfullyBarcodeStringResource
                        is Result.ReceivedBarcodes -> successfullyBarcodesStringResource
                    }

                    barcode.value = when (result) {
                        Result.CloseBarcode -> null
                        is Result.ReceivedBarcode -> result.barcode
                        is Result.ReceivedBarcodes -> result.barcodeString
                    }
                },
                {
                    Log.e("MainActivityViewModel", it.message.orEmpty())
                }
            )
            .let(disposable::add)
    }

    fun onBarcodeReceiver(barcode: String) {
        barcodeSubject.onNext(barcode)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    private sealed interface Result {
        object CloseBarcode : Result
        class ReceivedBarcode(val barcode: String) : Result
        class ReceivedBarcodes(val barcodeString: String) : Result
    }

    private companion object {
        val BARCODE_LIST_SEPARATOR = "\n|\r\n".toRegex()
    }
}