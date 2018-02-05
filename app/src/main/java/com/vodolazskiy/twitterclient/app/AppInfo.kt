package com.vodolazskiy.twitterclient.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.vodolazskiy.twitterclient.core.di.DI
import javax.inject.Inject

class AppInfo @Inject constructor(val context: Context) : AppInfoProvider {
    @SuppressLint("HardwareIds")
    val androidIdVal = Settings.Secure.getString(DI.context.contentResolver, Settings.Secure.ANDROID_ID)
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    val deviceNameVal = if (model.startsWith(manufacturer)) model
    else manufacturer + " " + model

    override val androidId: String = androidIdVal

    override val deviceName: String = deviceNameVal
}