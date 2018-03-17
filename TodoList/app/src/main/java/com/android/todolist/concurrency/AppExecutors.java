package com.android.todolist.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by laxman on 16/3/18.
 */
public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    private final Executor mDiskIO;

    private final Executor mMainThread;

    private AppExecutors(Executor diskIO, Executor mainThread) {
        this.mDiskIO = diskIO;
        this.mMainThread = mainThread;
    }

    private AppExecutors() {
        this(Executors.newSingleThreadExecutor(),
                new MainThreadExecutor());
    }

    public Executor diskIO() {
        return mDiskIO;
    }


    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}