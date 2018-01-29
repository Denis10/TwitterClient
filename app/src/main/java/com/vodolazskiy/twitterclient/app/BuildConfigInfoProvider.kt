package com.vodolazskiy.twitterclient.app

interface BuildConfigInfoProvider {
    val isDebug: Boolean

    val applicationId: String

    val buildType: String

    val versionCode: Int

    val versionName: String

    val osVersion: String
}
