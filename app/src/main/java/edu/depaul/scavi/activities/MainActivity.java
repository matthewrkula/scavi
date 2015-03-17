package edu.depaul.scavi.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import edu.depaul.scavi.R;
import edu.depaul.scavi.data.ScavengerHunt;
import edu.depaul.scavi.data.User;
import edu.depaul.scavi.networking.NetworkManager;

/**
 * Created by matt on 1/29/15.
 */
public class MainActivity extends Activity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                startActivity(new Intent(MainActivity.this, HuntListActivity.class));
            }
        });


        findViewById(R.id.btn_ozzie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OzzieActivity.class));
            }
        });
        findViewById(R.id.btn_ozzie).setVisibility(View.INVISIBLE);
    }

    private void login() {
        //TODO: Login URL
        String url = "LOGIN URL";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //TODO: Login here
                    data = gson.fromJson(jsonObject.toString(), User.class);
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(MainActivity.class.getName(), volleyError.toString());
            }
        });

        NetworkManager.getInstance(this).addRequest(request);
        dialog = ProgressDialog.show(this, "", "Logging in...", true);
    }

}
