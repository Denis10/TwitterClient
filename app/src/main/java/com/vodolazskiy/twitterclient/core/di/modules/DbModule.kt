package com.vodolazskiy.twitterclient.core.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.vodolazskiy.twitterclient.BuildConfig
import com.vodolazskiy.twitterclient.core.converter.IConvertersContext
import com.vodolazskiy.twitterclient.data.db.DB
import com.vodolazskiy.twitterclient.data.db.DBImpl
import com.vodolazskiy.twitterclient.data.db.DbWrapper
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by denis on 1/28/18.
 */
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDbImpl(context: Context): DBImpl =
            Room.databaseBuilder(context, DBImpl::class.java, BuildConfig.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton
    @Provides
    fun provideDbWrapper(): DB = DbWrapper()

    @Singleton
    @Provides
    fun provideUserFeedRepo(dbImpl: DBImpl, converter: IConvertersContext): UserFeedRepository
            = UserFeedRepositoryImpl(dbImpl.getUserFeedDao(), converter)
}