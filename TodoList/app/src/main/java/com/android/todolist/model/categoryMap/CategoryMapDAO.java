package com.android.todolist.model.categoryMap;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
@Dao
public interface CategoryMapDAO {

    @Query("SELECT * FROM CategoryEntity")
    public List<CategoryMap> loadAllCategories();
}
