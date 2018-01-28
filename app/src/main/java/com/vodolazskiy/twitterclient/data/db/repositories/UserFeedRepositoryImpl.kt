package com.vodolazskiy.twitterclient.data.db.repositories

import com.vodolazskiy.twitterclient.core.converter.ConvertersContext
import com.vodolazskiy.twitterclient.data.converter.convert
import com.vodolazskiy.twitterclient.data.converter.convertCollectionToOut
import com.vodolazskiy.twitterclient.data.converter.convertToOut
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDao
import com.vodolazskiy.twitterclient.data.db.room.UserFeedDbEntity
import com.vodolazskiy.twitterclient.data.modelinterfaces.UserFeed
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableFromCallable
import io.reactivex.internal.operators.single.SingleFromCallable

internal class UserFeedRepositoryImpl constructor(private val dao: UserFeedDao, private val converter: ConvertersContext) :
        UserFeedRepository {
    override fun get(id: String): Flowable<UserFeed> = dao.getById(id).convert(converter)

    override fun getAll(): Flowable<List<UserFeed>> = dao.all.convert(converter)

    override fun insert(entity: UserFeed): Completable = CompletableFromCallable {
        val convertedEntity: UserFeedDbEntity = converter.convertToOut(entity)
        dao.insert(convertedEntity)
    }

    override fun insertAll(entities: List<UserFeed>): Completable = CompletableFromCallable {
        dao.insert(converter.convertCollectionToOut(entities))
    }

    override fun remove(entity: List<UserFeed>): Completable = removeById(entity.map { it.id })

    override fun removeById(id: List<String>): Completable = CompletableFromCallable {
        if (id.isNotEmpty()) {
            dao.deleteById(id)
        }
    }

    override fun removeAll(): Completable = CompletableFromCallable { dao.deleteAll() }

    override fun getCount(): Single<Int> = SingleFromCallable { dao.count }
}