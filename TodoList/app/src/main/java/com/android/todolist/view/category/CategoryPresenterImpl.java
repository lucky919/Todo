package com.android.todolist.view.category;

import android.os.Bundle;

import com.android.todolist.concurrency.AppExecutors;
import com.android.todolist.Utils.Util;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.category.CategoryEntity;
import com.android.todolist.model.categoryMap.CategoryMap;

import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public class CategoryPresenterImpl implements CategoryPresenter {

    private CategoryView categoryView;
    private DBHelper dbHelper;
    private AppExecutors executors;

    public CategoryPresenterImpl(CategoryView categoryView, DBHelper dbHelper) {
        this.categoryView = categoryView;
        this.dbHelper = dbHelper;
        this.executors = AppExecutors.getInstance();
    }

    @Override
    public void onCreate() {
        categoryView.setUpViews();
    }

    @Override
    public void onItemClick(CategoryMap categoryMap) {
        Bundle b = new Bundle();
        b.putSerializable(Util.KEY_CATEGORY, categoryMap);
        categoryView.goToNextActivity(b);
    }

    @Override
    public void addCategory(final String categoryName) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final CategoryEntity entity = new CategoryEntity();
                entity.setName(categoryName);
                final long id = dbHelper.getCategoryDAO().insertCategoryEntity(entity);
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        CategoryMap map = new CategoryMap();
                        entity.setId(id);
                        map.setCategory(entity);
                        categoryView.addCategoryToList(map);
                    }
                });
            }
        });
    }

    @Override
    public void validate(final String categoryName) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final CategoryEntity entity = dbHelper.getCategoryDAO().getCategoryByName(categoryName);
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        categoryView.onValidateCategory(categoryName, entity != null);
                    }
                });
            }
        });
    }

    @Override
    public void listAllCategories() {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<CategoryMap> categories = dbHelper.getCategoryMapDAO().loadAllCategories();
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (Util.isEmpty(categories)) {
                            categoryView.showRecyclerView(false);
                        } else {
                            categoryView.setCategories(categories);
                            categoryView.showRecyclerView(true);
                        }
                    }
                });
            }
        });
    }
}
