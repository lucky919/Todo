package com.android.todolist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.todolist.model.category.CategoryDAO;
import com.android.todolist.model.category.CategoryEntity;
import com.android.todolist.model.categoryItem.CategoryItemDAO;
import com.android.todolist.model.categoryItem.CategoryItemEntity;
import com.android.todolist.model.categoryMap.CategoryMapDAO;

/**
 * Created by laxman on 16/3/18.
 */
@Database(entities = {CategoryEntity.class, CategoryItemEntity.class}, version = 1, exportSchema = false)
public abstract class DBHelper extends RoomDatabase {

    public abstract CategoryDAO getCategoryDAO();
    public abstract CategoryItemDAO getCategoryItemDAO();
    public abstract CategoryMapDAO getCategoryMapDAO();

    private static DBHelper ourInstance;

    public static DBHelper getInstance(Context applicationContext) {
        if (ourInstance == null) {
            ourInstance = Room.databaseBuilder(applicationContext.getApplicationContext(),
                    DBHelper.class, "todo-list")
                    .allowMainThreadQueries()
                    .build();
        }
        return ourInstance;
    }
}
