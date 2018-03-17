package com.android.todolist.view.category;

import com.android.todolist.model.categoryMap.CategoryMap;

/**
 * Created by laxman on 16/3/18.
 */
public interface CategoryPresenter {
    void onCreate();
    void onItemClick(CategoryMap categoryMap);
    void addCategory(String categoryName);
    void validate(String categoryName);
    void listAllCategories();
}
