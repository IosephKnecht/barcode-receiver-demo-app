package io.github.iosephknecht.barcode_receiver_demo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import io.github.iosephknecht.barcode_receiver_android.manageByLifecycle
import io.github.iosephknecht.barcode_receiver_api.barcodes
import io.github.iosephknecht.barcode_receiver_demo.databinding.ActivityMainBinding
import io.github.iosephknecht.barcode_receiver_rxjava.RxJavaMutableBarcodeReceiver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Main Activity of app.
 *
 * @author IosephKnecht
 */
internal class MainActivity : AppCompatActivity() {

    private var snackBar: Snackbar? = null
    private val viewModel: MainActivityViewModel by viewModels()
    private val barcodeReceiver = RxJavaMutableBarcodeReceiver()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // region unnecessary code
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val rootView = binding.root.also { setContentView(it) }

        object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                snackBar?.dismiss()
                snackBar = null
                disposables.clear()
            }
        }.let(lifecycle::addObserver)

        with(viewModel) {
            title.observe(this@MainActivity) { stringResource ->
                binding.title.text = stringResource?.getString(this@MainActivity)
            }

            barcode.observe(this@MainActivity) { barcode ->
                when (barcode) {
                    null -> snackBar?.dismiss()
                    else -> {
                        snackBar?.dismiss()

                        Snackbar.make(rootView, barcode, Snackbar.LENGTH_INDEFINITE)
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    this@MainActivity,
                                    R.color.purple_700
                                )
                            )
                            .also { snackBar -> this@MainActivity.snackBar = snackBar }
                            .show()
                    }
                }
            }
        }
        // endregion

        /* Subscribing on receive barcodes. */
        barcodeReceiver.barcodes
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                viewModel::onBarcodeReceiver,
                { Log.e("MainActivity", it.message.orEmpty()) }
            )
            .let(disposables::add)

        /* Connect barcode receiver with lifecycle. */
        barcodeReceiver.manageByLifecycle(BARCODE_INTENT_FILTER_KEY, this)
    }

    private companion object {

        /**
         * APP_KEY for receive barcodes, used of constant in [android.content.IntentFilter].
         */
        const val BARCODE_INTENT_FILTER_KEY = "io.github.iosephknecht.demo_app"
    }
}