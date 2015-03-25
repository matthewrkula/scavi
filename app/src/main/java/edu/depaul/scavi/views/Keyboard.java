package edu.depaul.scavi.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;

import edu.depaul.scavi.R;
import edu.depaul.scavi.keyboard.TemplatePrune;

/**
 * Created by matt on 1/25/15.
 */
public class Keyboard extends LinearLayout {

    ArrayList<String> algoList = new ArrayList<>();
//    Path path = new Path();
    Paint paint = new Paint();

    KeyboardListener listener;

    KeyboardKey backspaceKey;
    KeyboardKey spaceKey;

    public Keyboard(Context context) {
        super(context);
        init();
    }

    public Keyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.keyboard, null);
        this.addView(view);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        backspaceKey = (KeyboardKey)findViewById(R.id.backspace);
        backspaceKey.setSwipeable(false);
        backspaceKey.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.backspacePressed();
                }
            }
        });

        backspaceKey = (KeyboardKey)findViewById(R.id.space);
        backspaceKey.setSwipeable(false);
        backspaceKey.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.letterTyped(" ");
                }
            }
        });
    }

    public void setListener(KeyboardListener listener) {
        this.listener = listener;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        path.lineTo(ev.getX(), ev.getY());
        GridLayout grid = (GridLayout)getChildAt(0);
        for(int _numChildren = grid.getChildCount() - 1; _numChildren >= 0; --_numChildren) {
            KeyboardKey child = (KeyboardKey)grid.getChildAt(_numChildren);
            Rect bounds = new Rect();
            child.getHitRect(bounds);
            if (bounds.contains((int)ev.getX(), (int)ev.getY())) {
                if (child.isSwipeable()) {
                    if (algoList.size() == 0 || !algoList.get(algoList.size()-1).equals(child.getText().toString())) {
                        algoList.add(child.getText().toString());
                    }
                }
            }
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                algoList = new ArrayList<>();
//                path.reset();
//                path.moveTo(ev.getX(), ev.getY());
                break;
            case MotionEvent.ACTION_UP:
                try {
                    if (algoList.size() > 1) {
                        String[] str = TemplatePrune.findMatch(algoList, getContext());
                        if (listener != null && str[0] != null) {
                            listener.wordTyped(str[0]);
                        }
                    } else if (algoList.size() == 1) {
                        if (listener != null) {
                            listener.letterTyped(algoList.get(0));
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        postInvalidate();
        return true;
    }

    public interface KeyboardListener {
        public void wordTyped(String word);
        public void letterTyped(String letter);
        public void backspacePressed();
    }
}
