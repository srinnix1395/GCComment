package io.srinnix.gccomment.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

public class AppDebugTree extends Timber.DebugTree {

    @Nullable
    @Override
    protected String createStackElementTag(@NonNull StackTraceElement element) {
        return String.format("#Timber (%s:%s)#%s", element.getFileName(), element.getLineNumber(), Thread.currentThread().getName());
    }
}
