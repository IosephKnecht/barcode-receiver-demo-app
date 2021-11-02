package com.project.iosephknecht.barcode_receiver_api

interface Subscription {

    fun isSubscribed(): Boolean

    fun unsubscribe()
}