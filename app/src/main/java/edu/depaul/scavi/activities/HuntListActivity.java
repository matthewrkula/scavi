package edu.depaul.scavi.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.depaul.scavi.data.ScavengerHunt;

/**
 * Created by matt on 2/19/15.
 */
public class HuntListActivity extends ListActivity {

    ArrayAdapter<String> listAdapter;

    String[] sampleData = {
            "Chicago Landmarks",
            "Pizza Places",
            "Random Trivia"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sampleData);
        setListAdapter(listAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(HuntListActivity.this, ARActivity.class));
            }
        });
    }
}
