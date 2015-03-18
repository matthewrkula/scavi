package edu.depaul.scavi.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by matt on 1/22/15.
 */
public class KeyboardKey extends TextView {

    boolean isSwipeable = true;

    public KeyboardKey(Context context) {
        super(context);
        init();
    }

    public KeyboardKey(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAlpha(0.8f);
        setBackgroundColor(getResources().getColor(android.R.color.black));
        setTextColor(getResources().getColor(android.R.color.white));
        setTextSize(40);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public boolean isSwipeable() {
        return isSwipeable;
    }

    public void setSwipeable(boolean isSwipeable) {
        this.isSwipeable = isSwipeable;
    }
}
