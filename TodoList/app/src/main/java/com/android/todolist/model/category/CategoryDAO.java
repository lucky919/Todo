package com.android.todolist.model.category;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM CategoryEntity")
    List<CategoryEntity> getCategories();

    @Query("SELECT * FROM CategoryEntity WHERE id = :id")
    CategoryEntity getCategoryById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCategoryEntity(CategoryEntity categoryEntity);

    @Update
    int updateCategoryEntity(CategoryEntity categoryEntity);

    @Query("DELETE FROM CategoryEntity")
    void deleteAll();

    @Query("DELETE FROM CategoryEntity WHERE id = :id")
    int deleteById(int id);

    @Query("SELECT * FROM CategoryEntity WHERE name = :name")
    CategoryEntity getCategoryByName(String name);

}
