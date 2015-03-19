package edu.depaul.scavi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import edu.depaul.scavi.R;
import edu.depaul.scavi.data.Clue;
import edu.depaul.scavi.keyboard.Dictionary;
import edu.depaul.scavi.views.Keyboard;

/**
 * Created by matt on 1/22/15.
 */
public class ARActivity extends Activity implements SurfaceHolder.Callback {

    enum CurrentState {
        ROAMING,
        IN_RANGE,
        VIEWING_QUESTION,
        ANSWERING
    }

    int QUESTION_RADIUS = 100;

    CurrentState currentState = CurrentState.IN_RANGE;

    Keyboard keyboard;
    TextView textView, questionView;
    Button answerBtn, submitBtn;
    SurfaceView textureView;

    LocationManager locationManager;
    Location currentLocation;

    Clue clue;

    Camera camera;
    SurfaceHolder holder;

    public static Intent getIntent(Context c, Clue clue) {
        Intent i = new Intent(c, ARActivity.class);
        i.putExtra("clue", clue);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        getActionBar().hide();

        setupLocationUpdates();

        try {
            Dictionary.getInstance().isSetup(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clue = (Clue)getIntent().getSerializableExtra("clue");

        textView = (TextView)findViewById(R.id.textview);
        questionView = (TextView)findViewById(R.id.tv_question);
        questionView.setVisibility(View.INVISIBLE);
        keyboard = (Keyboard)findViewById(R.id.keyboard);
        submitBtn = (Button)findViewById(R.id.btn_submit);

        questionView.setText(clue.getQuestion());

        keyboard.setTranslationY(-360);
        keyboard.setListener(new Keyboard.KeyboardListener() {
            @Override
            public void wordTyped(String word) {
                resetTextViewIfNeeded();
                textView.setText(textView.getText() + " " + word);
            }

            @Override
            public void letterTyped(String letter) {
                resetTextViewIfNeeded();
                textView.setText(textView.getText() + letter);
            }
            @Override
            public void backspacePressed() {
                if (textView.getText().toString().equalsIgnoreCase("wrong")) {
                    textView.setText("");
                } else {
                    if (textView.getText().length() > 0) {
                        textView.setText(textView.getText().subSequence(0, textView.getText().length() - 1));
                    }
                }
            }
            private void resetTextViewIfNeeded() {
                if (textView.getText().toString().equalsIgnoreCase("wrong")) {
                    textView.setText("");
                }
            }
        });

        answerBtn = (Button)findViewById(R.id.btn_answer);
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentState) {
                    case ROAMING:
                        answerBtn.setText("100 feet away");
                        currentState = CurrentState.IN_RANGE;
                        break;
                    case IN_RANGE:
                        answerBtn.setText("Answer Question");
                        questionView.setVisibility(View.VISIBLE);
                        currentState = CurrentState.VIEWING_QUESTION;
                        break;
                    case VIEWING_QUESTION:
                        answerBtn.setVisibility(View.GONE);
                        questionView.setVisibility(View.GONE);
                        keyboard.animate().translationY(0).start();
                        textView.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.VISIBLE);
                        currentState = CurrentState.ANSWERING;
                        break;
                    case ANSWERING:
                        answerBtn.setVisibility(View.VISIBLE);
                        questionView.setText("");
                        questionView.setVisibility(View.VISIBLE);
                        keyboard.animate().translationY(-360).start();
                        textView.setVisibility(View.GONE);
                        currentState = CurrentState.ROAMING;
                        break;
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = textView.getText().toString().trim();
                if (guess.equalsIgnoreCase(clue.getAnswer())) {
                    textView.setText("CORRECT");
                    exit();
                } else {
                    textView.setText("WRONG");
                }
            }
        });

        camera = Camera.open();
        textureView = (SurfaceView)findViewById(R.id.camera_background);
        holder = textureView.getHolder();
        holder.addCallback(this);
    }

    private void exit() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        }, 1000);
    }

    LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (currentLocation == null || location.getAccuracy() >= currentLocation.getAccuracy()) {
                    currentLocation = location;
                    int distance = getDistanceInFeet() - QUESTION_RADIUS;
                    Log.v("LOG THIS", distance + " feet");
                    if (distance > 0) {
                        String buttontext = String.format("%d feet away", distance);
                        answerBtn.setText(buttontext);
                    } else {
                        answerBtn.setText("View Question");
                    }
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

    private void setupLocationUpdates() {
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 0, locationListener);
    }

    private int getDistanceInFeet() {
        return (int)(Math.sqrt(
                Math.pow(currentLocation.getLatitude() - clue.getLatitude(), 2) +
                        Math.pow(currentLocation.getLongitude() - clue.getLongitude(), 2)
        ) * 362776);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(ARActivity.class.toString(), "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null){
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e){
            Log.d(ARActivity.class.toString(), "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }
}
