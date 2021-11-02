package com.example.barcoderecevierdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.barcoderecevierdemo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.project.iosephknecht.barcode_receiver_android.subscribe
import com.project.iosephknecht.barcode_receiver_rxjava.RxJavaMutableBarcodeReceiver
import com.project.iosephknecht.barcode_receiver_rxjava.barcodes
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val barcodeReceiver = RxJavaMutableBarcodeReceiver()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView = ActivityMainBinding.inflate(layoutInflater)
            .root
            .also { setContentView(it) }

        barcodeReceiver.subscribe(BARCODE_INTENT_FILTER_KEY, this)

        barcodeReceiver.barcodes
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { barcode ->
                    Snackbar.make(rootView, barcode, Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setBackgroundTint(ContextCompat.getColor(this, R.color.purple_700))
                        .show()
                },
                { Log.e("MainActivity", it.message.orEmpty()) }
            )
            .let(disposables::add)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private companion object {
        const val BARCODE_INTENT_FILTER_KEY = "com.project.iosephknecht.demo_app"
    }
}