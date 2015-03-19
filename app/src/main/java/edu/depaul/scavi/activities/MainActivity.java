package edu.depaul.scavi.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.depaul.scavi.R;
import edu.depaul.scavi.data.User;
import edu.depaul.scavi.networking.NetworkManager;

/**
 * Created by matt on 1/29/15.
 */
public class MainActivity extends Activity {

    ProgressDialog dialog;
    String url = "http://peter1123.pythonanywhere.com/ScaviHunt/default/applogin.json";

    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.field_UserID);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login() throws JSONException {
        String newUrl = url + "?" + "email=" + email.getText().toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    dialog.dismiss();
                    JSONArray array = jsonObject.getJSONArray("logged_user");
                    if (array.length() > 0) {
                        User user = new Gson().fromJson(array.getJSONObject(0).toString(), User.class);
                        User.loggedUser = user;
                        startActivity(new Intent(MainActivity.this, HuntListActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(MainActivity.class.getName(), volleyError.toString());
                dialog.dismiss();
            }
        });

        dialog = ProgressDialog.show(this, "", "Logging in...", true);
        NetworkManager.getInstance(this).addRequest(request);
    }

}
