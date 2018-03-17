package com.android.todolist.view.category;

import android.os.Bundle;

import com.android.todolist.model.categoryMap.CategoryMap;

import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public interface CategoryView {
    void setUpViews();
    void setCategories(List<CategoryMap> categories);
    void showRecyclerView(boolean shouldShow);
    void goToNextActivity(Bundle bundle);
    void onValidateCategory(String categoryName, boolean status);
    void addCategoryToList(CategoryMap categoryMap);
}
