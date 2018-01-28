package com.vodolazskiy.twitterclient.core.di.modules

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [(AndroidSupportInjectionModule::class)])
interface AndroidModule {
}