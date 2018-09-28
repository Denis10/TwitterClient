package com.vodolazskiy.twitterclient.core.util

import android.content.Intent

interface ActivityResultListener {

    fun onActivityResult(result: ActivityResult)

    data class ActivityResult(val requestCode: Int, val resultCode: Int, val data: Intent?)
}
