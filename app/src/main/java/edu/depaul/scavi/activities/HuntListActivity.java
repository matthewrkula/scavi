package edu.depaul.scavi.activities;

import android.app.DownloadManager;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.depaul.scavi.data.ScavengerHunt;
import edu.depaul.scavi.networking.NetworkManager;

/**
 * Created by matt on 2/19/15.
 */
public class HuntListActivity extends ListActivity {

    ArrayAdapter<ScavengerHunt> listAdapter;
    JsonObjectRequest request;
    Gson gson = new Gson();

    ScavengerHunt[] data;
    String url = "http://peter1123.pythonanywhere.com/ScaviHunt/default/hunt_admin.json";

    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = ProgressDialog.show(this, "", "Loading...", true);
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String json = jsonObject.getJSONArray("rows").toString();
                    data = gson.fromJson(json, ScavengerHunt[].class);
                    updateList();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v(HuntListActivity.class.getName(), volleyError.toString());
            }
        });

        NetworkManager.getInstance(this).addRequest(request);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(MapActivity.getIntent(HuntListActivity.this, data[position]));
            }
        });
    }

    private void updateList() {
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(listAdapter);
    }
}
