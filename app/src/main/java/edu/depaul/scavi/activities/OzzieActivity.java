package edu.depaul.scavi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import edu.depaul.scavi.R;

/**
 * Created by matt on 2/15/15.
 */
public class OzzieActivity extends Activity {

    TextView latitudeView, longitudeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozzie);

        latitudeView = (TextView)findViewById(R.id.latitude);
        longitudeView = (TextView)findViewById(R.id.longitude);
    }
}
