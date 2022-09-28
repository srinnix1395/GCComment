package io.srinnix.gccomment.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ViewUtils {

    public static void expandClickArea(View view) {
        expandClickArea(view, 12F, 12F, 12F, 12F);
    }

        /**
         * @param left dp unit
         * @param top dp unit
         * @param right dp unit
         * @param bottom dp unit
         */
    public static void expandClickArea(View view, float left, float top, float right, float bottom) {
        if (view.getParent() == null || view.getContext() == null) {
            return;
        }

        View parent = (View) view.getParent(); // button: the view you want to enlarge hit area
        Rect rect = new Rect();
        view.getHitRect(rect);
        rect.left -= DisplayUtils.dpToPx(left); // increase left hit area
        rect.top -= DisplayUtils.dpToPx(top); // increase top hit area
        rect.bottom += DisplayUtils.dpToPx(bottom); // increase bottom hit area
        rect.right += DisplayUtils.dpToPx(right); // increase right hit area
        parent.setTouchDelegate(new TouchDelegate(rect, view));
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(EditText editText) {
        hideKeyboard(editText, true);
    }

    public static void hideKeyboard(EditText editText, boolean clearFocus) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        if (clearFocus) {
            editText.clearFocus();
        }
    }

}
