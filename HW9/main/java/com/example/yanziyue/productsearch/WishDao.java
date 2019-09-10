package com.example.yanziyue.productsearch;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WishDao {

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM wish_table")
    void deleteAllWish();

    @Query("SELECT * FROM wish_table")
    LiveData<List<Item>> getAllWish();
}
