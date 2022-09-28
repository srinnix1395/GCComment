package io.srinnix.gccomment;

import android.app.Application;
import android.os.StrictMode;

import io.srinnix.gccomment.utils.AppDebugTree;
import timber.log.Timber;

public class GCCommentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    private void initTimber() {
        Timber.plant(new AppDebugTree());

        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()
                        .penaltyLog()
                        .build()
        );

        StrictMode.setVmPolicy(
                 new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .build()
        );
    }
}