package com.android.todolist.model.categoryMap;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;

import com.android.todolist.model.category.CategoryEntity;
import com.android.todolist.model.categoryItem.CategoryItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public class CategoryMap implements Serializable {

    @Ignore
    private static final long serialVersionUID = 3304006701255266223L;

    @Embedded
    private CategoryEntity category;

    @Relation(parentColumn = "id", entity = CategoryItemEntity.class, entityColumn = "category_id")
    private List<CategoryItemEntity> categoryItems;

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<CategoryItemEntity> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<CategoryItemEntity> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public String getcategoryName() {
        return category.getName();
    }

    public int getItemsCount() {
        return categoryItems == null ? 0 : categoryItems.size();
    }

    public long getCategoryId() {
        return category.getId();
    }

    @Override
    public String toString() {
        return "CategoryMap{" +
                "category=" + category +
                ", categoryItems=" + categoryItems +
                '}';
    }
}
