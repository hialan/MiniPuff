package com.bigpuffs.minipuff.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bigpuffs.minipuff.R;
import com.bigpuffs.minipuff.adapters.OptionsAdapter;
import com.bigpuffs.minipuff.models.Question;
import com.bigpuffs.minipuff.models.User;

import com.facebook.FacebookSdk;
import com.facebook.Profile;

import com.parse.Parse;


public class MainActivity extends AppCompatActivity {

    public static final int GET_PROFILE = 42;
    public static final String USER = "User";

    private SharedPreferences userSharedPref;
    private User user;

    private Profile fbProfile;
    private Question question;
    private OptionsAdapter aOption;

    private ListView lvOptions;

    private void initParse() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, this.getString(R.string.PARSE_APP_ID),
                this.getString(R.string.PARSE_CLIENT_KEY));
    }

    private void populateQuestions() {
        setContentView(R.layout.activity_main);

        question = new Question();

        aOption = new OptionsAdapter(this, question.options);

        lvOptions = (ListView) findViewById(R.id.lvOptions);
        lvOptions.setAdapter(aOption);

        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParse();

        FacebookSdk.sdkInitialize(getApplicationContext());
        fbProfile = Profile.getCurrentProfile();

        if (fbProfile == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivityForResult(i, GET_PROFILE);
            return;
        }

        populateQuestions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            System.err.println("Unexpected result code: " + resultCode);
            return;
        }
        if (requestCode != GET_PROFILE) {
            System.err.println("Unexpected request code: " + requestCode);
            return;
        }

        fbProfile = data.getParcelableExtra("profile");

        if (fbProfile == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivityForResult(i, GET_PROFILE);
            return;
        }

        populateQuestions();
        return;
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
            Intent i = new Intent(this, LoginActivity.class);
            startActivityForResult(i, GET_PROFILE);
        } else {
            Log.w("OPTIONS", "Unknown menu item");
        }

        return super.onOptionsItemSelected(item);
    }

}
