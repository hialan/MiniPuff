package com.bigpuffs.minipuff.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bigpuffs.minipuff.Candidate;
import com.bigpuffs.minipuff.CandidatesAdapter;
import com.bigpuffs.minipuff.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Candidate> candidates;
    private CandidatesAdapter aCandidates;
    private GridView gvCandidates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        candidates = new ArrayList<>();
        aCandidates = new CandidatesAdapter(this, candidates);

        gvCandidates = (GridView) findViewById(R.id.gvCandidates);
        gvCandidates.setAdapter(aCandidates);
        gvCandidates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miProfile) {
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
