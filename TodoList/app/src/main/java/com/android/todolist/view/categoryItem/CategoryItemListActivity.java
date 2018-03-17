package com.android.todolist.view.categoryItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.android.todolist.R;
import com.android.todolist.Utils.Util;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.categoryItem.CategoryItemEntity;
import com.android.todolist.model.categoryMap.CategoryMap;
import com.android.todolist.view.addItem.AddCategoryItemActivity;

import java.util.List;

public class CategoryItemListActivity extends AppCompatActivity implements
        CategoryItemView {

    private RecyclerView recyclerView;
    private CategoryItemAdapter adapter;
    private TextView emptyView;
    private CategoryItemPresenter presenter;
    private CategoryMap category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_list);
        presenter = new CategoryItemPresenterImpl(DBHelper.getInstance(this), this);
        presenter.onCreate();
    }

    @Override
    public boolean validateIntentObjects() {
        if (getIntent().getExtras() == null) {
            finish();
            return false;
        }

        category = (CategoryMap) getIntent().getExtras().get(Util.KEY_CATEGORY);

        if (category == null) {
            finish();
            return false;
        }

        return true;
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);

        getSupportActionBar().setTitle(Util.capitalizeWord(category.getcategoryName()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putBoolean(Util.KEY_ADD, true);
                b.putSerializable(Util.KEY_CATEGORY, category.getCategory());
                goToNextActivity(b);
            }
        });

        setUpRView();
    }

    @Override
    public void initialLoadingViaBundle() {
        setItems(category.getCategoryItems());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.listAllItems(category.getCategoryId());
    }

    private void setUpRView() {
        recyclerView = findViewById(R.id.recycler_view);
        emptyView = findViewById(R.id.empty_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new CategoryItemAdapter(this, presenter);
        recyclerView.setAdapter(adapter);
        setUpSwipingBehaviour();
    }

    @Override
    public void setUpViews() {
        setUI();
    }

    @Override
    public void setItems(List<CategoryItemEntity> items) {
        adapter.updateCategories(items);
    }

    @Override
    public void showRecyclerView(boolean shouldShow) {
        if (shouldShow) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void goToNextActivity(Bundle bundle) {
        Intent intent = new Intent(this, AddCategoryItemActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setUpSwipingBehaviour() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    adapter.updateStatus(pos, true);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    adapter.updateStatus(pos, false);
                }
            }

        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
}
