package edu.depaul.scavi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import edu.depaul.scavi.R;
import edu.depaul.scavi.keyboard.Dictionary;
import edu.depaul.scavi.views.Keyboard;

/**
 * Created by matt on 1/22/15.
 */
public class ARActivity extends Activity {

    Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        try {
            Dictionary.getInstance().isSetup(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        keyboard = (Keyboard)findViewById(R.id.keyboard);
        keyboard.setListener(new Keyboard.KeyboardListener() {
            @Override
            public void wordTyped(String word) {
                Log.v("ASDFASDF", word);
            }

            @Override
            public void letterTyped(String letter) {
                Log.v("ASDFASDF", letter);
            }
        });
    }
}
