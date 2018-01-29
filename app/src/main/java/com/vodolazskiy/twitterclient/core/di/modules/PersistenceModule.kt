package com.vodolazskiy.twitterclient.core.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.vodolazskiy.twitterclient.BuildConfig
import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.data.db.DBImpl
import com.vodolazskiy.twitterclient.data.db.DbManager
import com.vodolazskiy.twitterclient.data.db.DbManagerImpl
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepository
import com.vodolazskiy.twitterclient.data.db.repositories.UserFeedRepositoryImpl
import com.vodolazskiy.twitterclient.data.prefs.PersistenceStorage
import com.vodolazskiy.twitterclient.data.prefs.PersistenceStorageImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideDbImpl(context: Context): DBImpl =
            Room.databaseBuilder(context, DBImpl::class.java, BuildConfig.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton
    @Provides
    fun provideDbWrapper(): DbManager = DbManagerImpl()

    @Singleton
    @Provides
    fun provideUserFeedRepo(dbImpl: DBImpl, converter: ConvertersContext): UserFeedRepository = UserFeedRepositoryImpl(dbImpl.getUserFeedDao(), converter)

    @Singleton
    @Provides
    fun providePrefs(context: Context): PersistenceStorage = PersistenceStorageImpl(context)
}