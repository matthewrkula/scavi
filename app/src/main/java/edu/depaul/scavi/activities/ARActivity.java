package edu.depaul.scavi.activities;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.TextView;

import java.io.IOException;

import edu.depaul.scavi.R;
import edu.depaul.scavi.keyboard.Dictionary;
import edu.depaul.scavi.views.Keyboard;

/**
 * Created by matt on 1/22/15.
 */
public class ARActivity extends Activity implements SurfaceHolder.Callback {

    Keyboard keyboard;
    TextView textView;
    SurfaceView textureView;

    Camera camera;
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        try {
            Dictionary.getInstance().isSetup(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        textView = (TextView)findViewById(R.id.textview);
        keyboard = (Keyboard)findViewById(R.id.keyboard);
        keyboard.setListener(new Keyboard.KeyboardListener() {
            @Override
            public void wordTyped(String word) {
                textView.setText(word);
            }

            @Override
            public void letterTyped(String letter) {
                textView.setText(textView.getText() + letter);
            }
        });

        camera = Camera.open();
        textureView = (SurfaceView)findViewById(R.id.camera_background);
        holder = textureView.getHolder();
        holder.addCallback(this);
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
    }
}
