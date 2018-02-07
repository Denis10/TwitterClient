package com.vodolazskiy.twitterclient.data.db.repositories

import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.core.converter.convert
import com.vodolazskiy.twitterclient.core.converter.convertCollectionToOut
import com.vodolazskiy.twitterclient.core.converter.convertToOut
import com.vodolazskiy.twitterclient.core.di.annotation.DataConverterQualifier
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDao
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.componentinterfaces.UserFeedRepository
import com.vodolazskiy.twitterclient.domain.datalayerobjects.modelinterfaces.UserFeedEntity
import com.vodolazskiy.twitterclient.domain.datalayerobjects.request.GetUserFeedsDataRequest
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableFromCallable
import io.reactivex.internal.operators.single.SingleFromCallable

internal class UserFeedRepositoryImpl constructor(private val dao: UserFeedDao,
                                                  @DataConverterQualifier private val converter: ConvertersContext) :
        UserFeedRepository {
    override fun get(id: Long): Flowable<UserFeedEntity> = dao.getById(id).convert(converter)

    override fun getAll(): Flowable<List<UserFeedEntity>> = dao.all.convert(converter)

    override fun insert(entity: UserFeedEntity): Single<UserFeedEntity> = SingleFromCallable {
        val convertedEntity: UserFeedDbEntity = converter.convertToOut(entity)
        dao.insert(convertedEntity)
        entity
    }

    override fun insertAll(entities: List<UserFeedEntity>): Single<List<UserFeedEntity>> = SingleFromCallable {
        dao.insert(converter.convertCollectionToOut(entities))
        entities
    }

    override fun remove(entity: List<UserFeedEntity>): Completable = removeById(entity.map { it.id })

    override fun removeById(id: List<Long>): Completable = CompletableFromCallable {
        if (id.isNotEmpty()) {
            dao.deleteById(id)
        }
    }

    override fun removeAll(): Completable = CompletableFromCallable { dao.deleteAll() }

    override fun getCount(): Single<Int> = SingleFromCallable { dao.count }

    override fun getFeeds(request: GetUserFeedsDataRequest): Flowable<List<UserFeedEntity>> {
        return dao.getBefore(request.maxId ?: Long.MAX_VALUE, request.limit).convert(converter)
    }
}