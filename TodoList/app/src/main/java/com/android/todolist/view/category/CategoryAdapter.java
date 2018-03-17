package com.android.todolist.view.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.todolist.R;
import com.android.todolist.Utils.Util;
import com.android.todolist.model.categoryMap.CategoryMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryMap> categoriesList;
    private CategoryListener listener;

    public CategoryAdapter(CategoryListener listener) {
        this.categoriesList = new ArrayList<>();
        this.listener = listener;
    }

    public void updateCategories(List<CategoryMap> categories) {
        if (categories == null) {
            categoriesList = new ArrayList<>();
        } else
            categoriesList = categories;

        notifyDataSetChanged();
    }

    public void addCategoryToList(CategoryMap category) {
        if (!categoriesList.contains(category)) {
            categoriesList.add(category);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoryMap entity = categoriesList.get(position);
        holder.name.setText(Util.capitalizeWord(entity.getcategoryName()));
        holder.cnt.setText(entity.getItemsCount() + " items");

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategorySelected(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView cnt;
        View mParent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mParent = itemView;
            this.name = itemView.findViewById(R.id.category);
            this.cnt = itemView.findViewById(R.id.category_count);
        }
    }

    public interface CategoryListener {
        void onCategorySelected(CategoryMap categoryMap);
    }
}
