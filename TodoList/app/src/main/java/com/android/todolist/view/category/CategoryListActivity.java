package com.android.todolist.view.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.todolist.R;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.category.CategoryEntity;
import com.android.todolist.model.categoryMap.CategoryMap;
import com.android.todolist.view.category.dialog.InputDialog;
import com.android.todolist.view.categoryItem.CategoryItemListActivity;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity implements CategoryView,
        InputDialog.DialogCallback, CategoryAdapter.CategoryListener {

    private CategoryPresenter categoryPresenter;

    private RecyclerView rView;
    private TextView emptyView;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryPresenter = new CategoryPresenterImpl(this, DBHelper.getInstance(this));
        categoryPresenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryPresenter.listAllCategories();
    }

    private void setRecyclerView() {
        rView = findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rView.setLayoutManager(llm);
        adapter = new CategoryAdapter(this);
        rView.setAdapter(adapter);
    }

    @Override
    public void setUpViews() {
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emptyView = findViewById(R.id.empty_view);

        getSupportActionBar().setTitle(R.string.title_activity_category);

        List<CategoryEntity> cc = DBHelper.getInstance(this).getCategoryDAO().getCategories();
        List<CategoryMap> cm = DBHelper.getInstance(this).getCategoryMapDAO().loadAllCategories();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFinishing()) {
                    InputDialog dialog = new InputDialog(CategoryListActivity.this,
                            CategoryListActivity.this, DBHelper.getInstance(CategoryListActivity.this));
                    dialog.show();
                }
            }
        });

        setRecyclerView();
    }

    @Override
    public void setCategories(List<CategoryMap> categories) {
        adapter.updateCategories(categories);
    }

    @Override
    public void showRecyclerView(boolean shouldShow) {
        if (!shouldShow) {
            rView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            rView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goToNextActivity(Bundle bundle) {
        Intent intent = new Intent(this, CategoryItemListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDialogResult(String categoryName) {
        categoryPresenter.addCategory(categoryName);
    }

    @Override
    public void onCategorySelected(CategoryMap categoryMap) {
        categoryPresenter.onItemClick(categoryMap);
    }

    @Override
    public void addCategoryToList(CategoryMap categoryMap) {
        adapter.addCategoryToList(categoryMap);
        showRecyclerView(true);
    }

    @Override
    public void onValidateCategory(String categoryName, boolean status) {
    }
}
