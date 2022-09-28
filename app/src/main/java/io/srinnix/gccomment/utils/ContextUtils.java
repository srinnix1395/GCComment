package io.srinnix.gccomment.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ContextUtils {

    public static void openUrl(Context context, String url) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
