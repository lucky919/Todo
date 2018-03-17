package com.android.todolist.model.categoryItem;

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
public interface CategoryItemDAO {

    @Query("SELECT * FROM CategoryItemEntity")
    List<CategoryItemEntity> getAll();

    @Query("SELECT * FROM CategoryItemEntity WHERE id = :id")
    CategoryItemEntity getCategoryItemById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CategoryItemEntity item);

    @Update
    int update(CategoryItemEntity item);

    @Query("DELETE FROM CategoryItemEntity WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM CategoryItemEntity")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM CategoryItemEntity WHERE category_id = :categoryId")
    int getCountOfCategoryItems(String categoryId);

    @Query("SELECT * FROM CategoryItemEntity WHERE category_id = :categoryId")
    List<CategoryItemEntity> getItemsByCategory(long categoryId);

    @Query("UPDATE CategoryItemEntity SET item_name = :title WHERE id = :id")
    int updateTitle(int id, String title);

    @Query("UPDATE CategoryItemEntity SET item_description = :description WHERE id = :id")
    int updateDescription(int id, String description);

    @Query("UPDATE CategoryItemEntity SET item_completed = :status WHERE id = :id")
    int updateStatus(int id, int status);

//    @Query("UPDATE CategoryItemEntity SET item_image = :data WHERE id = :id")
//    int updateImage(int id, byte[] data);

    @Query("UPDATE CategoryItemEntity SET item_image_path = :path WHERE id = :id")
    int updateImagePath(int id, String path);
}
