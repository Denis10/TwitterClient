package com.vodolazskiy.twitterclient.data.db.repositories

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface DBRepository<in Key, Entity> {
    fun get(id: Key): Flowable<Entity>
    fun getAll(): Flowable<List<Entity>>

    fun insert(entity: Entity): Single<Entity>
    fun insertAll(entities: List<Entity>): Single<List<Entity>>

    fun remove(entity: List<Entity>): Completable
    fun removeById(id: List<Key>): Completable
    fun removeAll(): Completable

    fun getCount(): Single<Int>
}