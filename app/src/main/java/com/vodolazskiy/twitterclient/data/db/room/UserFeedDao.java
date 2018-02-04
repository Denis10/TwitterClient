package com.vodolazskiy.twitterclient.data.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserFeedDao {

    @Query("select * from UserFeed where id = :id")
    Flowable<UserFeedDbEntity> getById(@NonNull Long id);

    @Query("select * from UserFeed order by id")
    Flowable<List<UserFeedDbEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull UserFeedDbEntity offenderDbEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull List<UserFeedDbEntity> offenderDbEntities);

    @Query("delete from UserFeed where id in (:ids)")
    void deleteById(@NonNull List<Long> ids);

    @Query("delete from UserFeed")
    void deleteAll();

    @Query("select count(*) from UserFeed")
    int getCount();

    @Query("select * from UserFeed where id < :maxId order by id desc limit :limit")
    Flowable<List<UserFeedDbEntity>> getBefore(@NonNull Long maxId, int limit);
}
