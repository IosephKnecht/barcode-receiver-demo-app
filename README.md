## Overview

`barcode-receiver-demo-app` demo project to demonstrate mechanism of receive barcodes
from [BarcodeSenderPlugin](https://github.com/IosephKnecht/barcode-sender-plugin).


### Quick run with [dryrun](https://github.com/cesarferreira/dryrun)
```
dryrun https://github.com/IosephKnecht/barcode-receiver-demo-app
```

![preview](images/preview.gif)

## Libraries

The application uses libraries:

* `com.project.iosephknecht.barcode-receiver-api` - declaration module for interfaces.
* `com.project.iosephknecht.barcode-receiver-rxjava` - implementation of barcodes receiver.
* `com.project.iosephknecht.barcode-receiver-android` - implementation of lifecycle observer for bind barcodes receiver
  with lifecycle.

The major idea is for `MutableBarcodeReceiver` to be at the application level as singleton and connect to the lifecycle
of the main activity. Consumers get `ImmutableBarcodeReceiver` and subscribe to it is as if he were an ordinary
observable.

## Setup

Add repository to module:

```groovy
repositories {
    maven {
        url uri("https://maven.pkg.github.com/IosephKnecht/barcode-receiver-demo-app")
    }
}
```

Add required modules to your module`s build.gradle file:

```groovy
implementation 'com.project.iosephknecht:barcode-receiver-api:v1.0.0-alpha01'
implementation 'com.project.iosephknecht:barcode-receiver-rxjava:v1.0.0-alpha01'
implementation 'com.project.iosephknecht:barcode-receiver-android:v1.0.0-alpha01'
```

## Example of usage

<details>
<summary>Kotlin</summary>

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver
import com.project.iosephknecht.barcode_receiver_android.manageByLifecycle
import com.project.iosephknecht.barcode_receiver_api.barcodes
import com.project.iosephknecht.barcode_receiver_rxjava.RxJavaMutableBarcodeReceiver
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val barcodeReceiver: MutableBarcodeReceiver = RxJavaMutableBarcodeReceiver()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
      // omitted code

      barcodeReceiver.barcodes
        .subscribe {
            // do something
        }
        .let(disposables::add)
      
      barcodeReceiver.manageByLifecycle(BARCODE_INTENT_FILTER_KEY, this)
    }

    override fun onDestroy() {
      disposables.clear()
      super.onDestroy()
    }

    private companion object {
      const val BARCODE_INTENT_FILTER_KEY = "YOUR_APP_ID_RECEIVER_KEY"
    }
}
```

</details>

<details>
<summary>Java</summary>

```java
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.project.iosephknecht.barcode_receiver_api.MutableBarcodeReceiver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.project.iosephknecht.barcode_receiver_api.ExtenstionsKt.getBarcodes;
import static com.project.iosephknecht.barcode_receiver_rxjava.ExtensionsKt.RxJavaMutableBarcodeReceiver;
import static com.project.iosephknecht.barcode_receiver_android.ExtensionsKt.manageByLifecycle;

public class MainActivityLegacy extends AppCompatActivity {

    private static final String BARCODE_INTENT_FILTER_KEY = "YOUR_APP_ID_RECEIVER_KEY";
    
    private final MutableBarcodeReceiver barcodeReceiver = RxJavaMutableBarcodeReceiver();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // omitted code
        Disposable disposable = getBarcodes(barcodeReceiver)
                .subscribe((barcode) -> {
                    // do something
                });

        disposables.add(disposable);
        
        manageByLifecycle(barcodeReceiver, BARCODE_INTENT_FILTER_KEY, this);
    }

    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}

```

</details>