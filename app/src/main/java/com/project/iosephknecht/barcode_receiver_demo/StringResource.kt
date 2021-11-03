@file:Suppress("UNUSED")

package com.project.iosephknecht.barcode_receiver_demo

import android.content.Context
import androidx.annotation.StringRes

/**
 * Extension value for get string from abstraction.
 *
 * @param context
 *
 * @author IosephKnecht
 */
internal fun StringResource.getString(context: Context): String? {
    return when (this) {
        is StringResource.Resource -> context.resources.getString(resourceId)
        is StringResource.String -> this.value
    }
}

/**
 * Dummy abstraction for strings.
 *
 * @author IosephKnecht
 */
internal sealed interface StringResource {
    data class String(val value: kotlin.String?) : StringResource

    data class Resource(@StringRes val resourceId: Int) : StringResource
}