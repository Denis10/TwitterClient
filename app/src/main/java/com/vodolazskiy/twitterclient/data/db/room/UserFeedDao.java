package com.vodolazskiy.twitterclient.data.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Flowable;

@Dao
public interface UserFeedDao {

    @Query("select * from UserFeed where id = :id")
    Flowable<UserFeedDbEntity> getById(@NonNull String id);

    @Query("select * from UserFeed order by id")
    Flowable<List<UserFeedDbEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull UserFeedDbEntity offenderDbEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull List<UserFeedDbEntity> offenderDbEntities);

    @Query("delete from UserFeed where id in (:ids)")
    void deleteById(@Nonnull List<String> ids);

    @Query("delete from UserFeed")
    void deleteAll();

    @Query("select count(*) from UserFeed")
    int getCount();
}
