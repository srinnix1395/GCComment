package io.srinnix.gccomment.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;

public class StringUtils {

    public static void setClickableSpan(SpannableStringBuilder ssb, int start, int end, ClickableSpan span) {
        ssb.setSpan(
                span,
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }
}
