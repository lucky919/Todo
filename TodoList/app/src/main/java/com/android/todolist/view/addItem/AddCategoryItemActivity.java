package com.android.todolist.view.addItem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.todolist.R;
import com.android.todolist.utils.Util;
import com.android.todolist.data.DBHelper;
import com.android.todolist.model.category.CategoryEntity;
import com.android.todolist.model.categoryItem.CategoryItemEntity;
import com.squareup.picasso.Picasso;

import java.io.File;

public class AddCategoryItemActivity extends AppCompatActivity implements AddItemView{

    private AddItemPresenter presenter;
    private EditText title;
    private EditText description;
    private Switch status;
    private ImageView imView;
    private CategoryEntity category;
    private CategoryItemEntity categoryItem;
    private boolean isAdd = false;
    private String imagePath;

    private final int REQ_CODE = 114;
    private final int REQ_PERM = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_item);
        presenter = new AdItemPresenterImpl(DBHelper.getInstance(this), this);
        presenter.onCreate();
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private boolean validateIntentAndSetUpViews() {
        if (getIntent().getExtras() == null) {
            finish();
            return false;
        }

        isAdd = getIntent().getBooleanExtra(Util.KEY_ADD, false);

        if (!isAdd) {
            categoryItem = (CategoryItemEntity) getIntent().getExtras().getSerializable(Util.KEY_CATEGORY_ITEM);
        } else {
            category = (CategoryEntity) getIntent().getExtras().getSerializable(Util.KEY_CATEGORY);
        }

        if (isAdd) {
            if (category == null) {
                finish();
                return false;
            }
        } else {
            if (categoryItem == null) {
                finish();
                return false;
            }
        }
        return true;
    }

    @Override
    public void setupViews() {
        setUI();
        getSupportActionBar().setTitle(isAdd ? getString(R.string.add_item) : getString(R.string.edit_item));
        imView = findViewById(R.id.imageView);
        title = findViewById(R.id.editText);
        description = findViewById(R.id.editText2);
        status = findViewById(R.id.switch1);
        Button upload = findViewById(R.id.button);
        if (!isAdd) {
            if (categoryItem != null && categoryItem.getImagePath() != null) {
                upload.setText(R.string.change_photo);
            }
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.uploadImage();
            }
        });
    }

    @Override
    public boolean validateIntentObject() {
        return validateIntentAndSetUpViews();
    }

    @Override
    public void populateViews() {
        if (!isAdd) {
            title.setText(categoryItem.getItemName());
            if (!TextUtils.isEmpty(categoryItem.getDescription())) {
                description.setText(categoryItem.getDescription());
            }
            status.setChecked(categoryItem.isCompleted());

            if (categoryItem.getImagePath() != null) {
                loadImage(categoryItem.getImagePath());
            }
        }
    }

    @Override
    public boolean checkPermissions() {
        return checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestStoragePermissions() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        MenuItem item = menu.findItem(R.id.action_delete);
        if (isAdd) {
            item.setVisible(false);
        } else {
            item.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (categoryItem != null) {
                    presenter.deleteItem(categoryItem.getId());
                }
                break;
            case R.id.action_save:
                if (title.length() == 0) {
                    title.setError(getString(R.string.msg_title_empty));
                } else {
                    if (isAdd) {
                        CategoryItemEntity c = new CategoryItemEntity();
                        c.setItemName(title.getText().toString());
                        c.setCategoryId(category.getId());
                        c.setDescription(description.getText().toString().trim());
                        c.setCompleted(status.isChecked());
                        if (imagePath != null) {
                            c.setImagePath(imagePath);
                        }

                        presenter.saveItem(c);
                    } else {
                        categoryItem.setItemName(title.getText().toString());
                        categoryItem.setDescription(description.getText().toString());
                        categoryItem.setCompleted(status.isChecked());
                        if (imagePath != null) {
                            categoryItem.setImagePath(imagePath);
                        }
                        presenter.updateItem(categoryItem);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(String msg) {
        /*Snackbar.make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .setAction("OK", null).show();*/
    }

    @Override
    public void onInsertComplete(boolean status) {
        showMessage("Save item " + (status ? "successfull" : "failed"));
        finish();
    }

    @Override
    public void onUpdateComplete(boolean status) {
        showMessage("Update item " + (status ? "successfull" : "failed"));
        finish();
    }

    @Override
    public void onDeleteComplete(boolean status) {
        showMessage("Delete item " + (status ? "successfull" : "failed"));
        finish();
    }


    @Override
    public void selectAndLoadImage() {
        Intent in = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        in.setType("image/*");
        in.putExtra("crop", "true");
        in.putExtra("outputX", 100);
        in.putExtra("outputY", 100);
        in.putExtra("scale", true);
        in.putExtra("return-data", true);
        startActivityForResult(in, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE && data != null) {
            presenter.onImageSelectResponse(resultCode == RESULT_OK, data);
        } else if (requestCode == REQ_PERM) {
            presenter.onStoragePermissionResponse(resultCode == PackageManager.PERMISSION_GRANTED);
        }
    }

    @Override
    public void inflateImage(String imagePath) {
        this.imagePath = imagePath;
        loadImage(imagePath);
    }

    private void loadImage(String selectedImage) {
        Picasso.get()
                .load(new File(selectedImage))
                .config(Bitmap.Config.RGB_565)
                .fit()
                .placeholder(android.R.color.transparent)
                .into(imView);
    }

    @Override
    public String fetchImagePathFromIntent(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};

        if (selectedImage != null) {
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(selectedImage, filePath, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int colIndex = cursor.getColumnIndex(filePath[0]);
                    return cursor.getString(colIndex);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }
        return null;
    }
}
