package io.srinnix.gccomment.utils;

import android.content.res.Resources;

class DisplayUtils {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPx(float dp) {
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float pxToDp(float px) {
        return (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
