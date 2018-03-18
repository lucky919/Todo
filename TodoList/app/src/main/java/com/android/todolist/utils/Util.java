package com.android.todolist.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by laxman on 15/3/18.
 */
public class Util {

    public static final String KEY_CATEGORY_ITEM = "categoryItem";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_ADD = "add";
    public static final String KEY_DONE = "Done";
    public static final String KEY_PENDING = "Pending";
    public static final String MSG_EMPTY_CATEGORY = "Category cant be empty!";
    public static final String MSG_CATEGORY_EXISTS = "Category already exists!";
    public static final String MSG_INSUFFICIENT_PERMISSIONS = "Insufficient permissions to access image.";
    public static boolean isEmpty(Collection<? extends Object> collection) {
        return collection == null || collection.size() == 0;
    }

    public static String capitalizeFirstChar(String str) {
        String c = str != null ? str.trim() : "";
        String[] words = c.split(" ");
        StringBuilder res = new StringBuilder();
        for (String w : words)
            res.append(w.length() > 1 ? w.substring(0, 1).toUpperCase(Locale.US) + w.substring(1, w.length()).toLowerCase(Locale.US) : w + " ");
        return res.toString().trim();
    }

    public static String capitalizeWord(String str) {
        String word = str != null ? str.trim() : "";
        StringBuilder sb = new StringBuilder();
        sb.append(word.length() > 1 ? word.substring(0, 1).toUpperCase(Locale.US) + word.substring(1, word.length()).toLowerCase(Locale.US) : word);
        return sb.toString();
    }

    public static Drawable convertToDrawable(Context context, byte[] arr) {
        return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(arr, 0, arr.length));
    }
}
