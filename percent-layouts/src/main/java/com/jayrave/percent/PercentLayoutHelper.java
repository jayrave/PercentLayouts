package com.jayrave.percent;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.percent.PercentLayoutHelper.PercentLayoutInfo;
import android.support.percent.PercentLayoutHelper.PercentLayoutParams;
import android.view.ViewGroup;

public class PercentLayoutHelper {

    private PercentLayoutHelper() {}

    /**
     * A dimension value from XML could be positive, 0, -1 (MATCH_PARENT) or -2 (WRAP_CONTENT).
     * Make sure to use some other value
     */
    public static final int INVALID_LAYOUT_DIM_FROM_XML = Integer.MIN_VALUE;

    /**
     * Helper method to be called from {@link ViewGroup.LayoutParams#setBaseAttributes} override
     * that reads layout_width and layout_height attribute values without throwing an exception if
     * they aren't present
     */
    public static void fetchWidthAndHeight(
            @NonNull ViewGroup.LayoutParams params, @NonNull TypedArray array,
            int widthAttr, int heightAttr) {

        params.width = array.getLayoutDimension(widthAttr, INVALID_LAYOUT_DIM_FROM_XML);
        params.height = array.getLayoutDimension(heightAttr, INVALID_LAYOUT_DIM_FROM_XML);
    }

    /**
     * Informs whether the passed in layout params is valid
     */
    public static <LP extends ViewGroup.LayoutParams & PercentLayoutParams> void
    checkLayoutParamsFromXml(@NonNull LP lp) {
        int invalidDim = INVALID_LAYOUT_DIM_FROM_XML;
        PercentLayoutInfo percentInfo = lp.getPercentLayoutInfo();
        boolean invalid = ((lp.width == invalidDim) && (percentInfo.widthPercent < 0)) ||
                ((lp.height == invalidDim) && (percentInfo.heightPercent < 0));

        if (invalid) {
            throw new IllegalArgumentException(
                    "width: layout_width or layout_widthPercent (>= 0) must be mentioned. " +
                            "height: layout_height or layout_heightPercent (>= 0) must be mentioned"
            );
        }
    }
}
