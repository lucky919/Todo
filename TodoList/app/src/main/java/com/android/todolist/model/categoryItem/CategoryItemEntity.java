package com.android.todolist.model.categoryItem;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.android.todolist.model.category.CategoryEntity;

import java.io.Serializable;

/**
 * Created by laxman on 16/3/18.
 */
@Entity(indices = {@Index(value = "item_name", unique = true), @Index(value = "category_id")},
        foreignKeys = {
        @ForeignKey(entity = CategoryEntity.class, parentColumns = "id", childColumns = "category_id", onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE)
})
public class CategoryItemEntity implements Serializable {

    @Ignore
    private static final long serialVersionUID = -3354355558687686223L;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "category_id")
    private long categoryId;

    @ColumnInfo(name = "item_description")
    private String description;

    @ColumnInfo(name = "item_completed")
    private boolean completed;

//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "item_image")
//    private byte[] image;

    @ColumnInfo(name = "item_image_path")
    private String imagePath;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "CategoryItemEntity{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", completed=" + completed +
//                ", image=" + Arrays.toString(image) +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
