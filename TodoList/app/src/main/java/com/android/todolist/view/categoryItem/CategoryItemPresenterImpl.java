package com.android.todolist.view.categoryItem;

import android.os.Bundle;

import com.android.todolist.concurrency.AppExecutors;
import com.android.todolist.Utils.Util;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.categoryItem.CategoryItemEntity;

import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public class CategoryItemPresenterImpl implements CategoryItemPresenter {

    private DBHelper dbHelper;
    private CategoryItemView itemView;
    private AppExecutors executors;

    public CategoryItemPresenterImpl(DBHelper dbHelper, CategoryItemView itemView) {
        this.dbHelper = dbHelper;
        this.itemView = itemView;
        this.executors = AppExecutors.getInstance();
    }

    @Override
    public void onCreate() {
        if (itemView.validateIntentObjects()) {
            itemView.setUpViews();
            itemView.initialLoadingViaBundle();
        }
    }

    @Override
    public void updateItem(final CategoryItemEntity item) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int res = dbHelper.getCategoryItemDAO().update(item);
            }
        });
    }

    @Override
    public void add(final CategoryItemEntity item) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dbHelper.getCategoryItemDAO().insert(item);
            }
        });
    }

    @Override
    public void listAllItems(final long categoryId) {
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<CategoryItemEntity> items = dbHelper.getCategoryItemDAO().getItemsByCategory(categoryId);
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (Util.isEmpty(items)) {
                            itemView.showRecyclerView(false);
                        } else {
                            itemView.setItems(items);
                            itemView.showRecyclerView(true);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemClicked(CategoryItemEntity itemEntity) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Util.KEY_ADD, false);
        bundle.putSerializable(Util.KEY_CATEGORY_ITEM, itemEntity);
        itemView.goToNextActivity(bundle);
    }
}
