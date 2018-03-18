package com.android.todolist.view.categoryItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.todolist.R;
import com.android.todolist.model.categoryItem.CategoryItemEntity;
import com.android.todolist.utils.Util;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxman on 16/3/18.
 */
public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {

    private List<CategoryItemEntity> categoryItems;
    private Context context;
    private CategoryItemPresenter presenter;

    public CategoryItemAdapter(Context context, CategoryItemPresenter presenter) {
        this.context = context;
        this.categoryItems = new ArrayList<>();
        this.presenter = presenter;
    }

    public void updateCategories(List<CategoryItemEntity> items) {
        if (items == null) {
            this.categoryItems = new ArrayList<>();
        } else
            this.categoryItems = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_category_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoryItemEntity entity = categoryItems.get(position);
        holder.item.setText(entity.getItemName());
        if (entity.isCompleted()) {
            holder.status.setText(Util.KEY_DONE);
            holder.status.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.status.setText(Util.KEY_PENDING);
            holder.status.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.onItemClicked(entity);
            }
        });

        if (entity.getImagePath() != null) {
            Picasso.get()
                    .load(new File(entity.getImagePath()))
                    .config(Bitmap.Config.RGB_565)
                    .fit().centerCrop()
                    .placeholder(android.R.color.darker_gray)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(android.R.color.darker_gray);
        }
    }

    public void updateStatus(int position, boolean isLeft) {
        CategoryItemEntity entity = categoryItems.get(position);
        if (entity.isCompleted() != isLeft) {
            entity.setCompleted(isLeft);
            presenter.updateItem(entity);
        }
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mParent;
        private TextView item;
        private TextView status;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mParent = itemView;
            item = itemView.findViewById(R.id.textView2);
            status = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.include);
        }
    }
}
