package com.bigpuffs.minipuff.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.bigpuffs.minipuff.R;
import com.bigpuffs.minipuff.adapters.OptionsAdapter;
import com.bigpuffs.minipuff.models.Candidate;
import com.bigpuffs.minipuff.models.Question;
import com.bigpuffs.minipuff.models.User;
import com.bigpuffs.minipuff.parse.ParseClient;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int GET_PROFILE = 42;

    private SharedPreferences userSharedPref;
    private User user;
    private Profile fbProfile;
    private List<Candidate> candidates;

    private OptionsAdapter aOption;

    private ListView lvOptions;

    private void initParse() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, this.getString(R.string.PARSE_APP_ID),
                this.getString(R.string.PARSE_CLIENT_KEY));
    }

    private void populateQuestions() {
        setContentView(R.layout.activity_main);


        if (user != null) {
            //// not enougth test data
            // Question question = candidates.get(0).questions.get(0);

            ArrayList<Question> questions = ParseClient.getInstance().getQuestions(user);
            Question question = questions.get(0);

            aOption = new OptionsAdapter(this, question.options);

            EditText et = (EditText) findViewById(R.id.editText);
            et.setText(question.title);

            lvOptions = (ListView) findViewById(R.id.lvOptions);
            lvOptions.setAdapter(aOption);
        }

        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParse();

        FacebookSdk.sdkInitialize(getApplicationContext());

        Profile.fetchProfileForCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();

        onFbProfileChange(profile);
        populateQuestions();
    }

    private void onFbProfileChange(Profile profile) {
        fbProfile = profile;

        if (fbProfile == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivityForResult(i, GET_PROFILE);
            return;
        }

        user = User.fromFacebookProfile(fbProfile);
        ParseObject parseUser = ParseClient.getInstance().getParseUser(user.id);

        if(parseUser == null) {
            parseUser = ParseClient.getInstance().createNewParseUser(user);
        }
        candidates = ParseClient.getInstance().getCandidate(user, 0);

        /* for generate test data
        Question q1 = new Question();
        q1.title = "What's your favorite color?";
        q1.options = new ArrayList<>();
        q1.options.add(new Option("Red", false));       // 0
        q1.options.add(new Option("White", false));     // 1
        q1.options.add(new Option("Yellow", false));    // 2
        q1.options.add(new Option("Blue", false));      // 3
        q1.answer = 3;

        Question q2 = new Question();
        q2.title = "What's your company?";
        q2.options = new ArrayList<>();
        q2.options.add(new Option("Yahoo", false));     // 0
        q2.options.add(new Option("HooYa", false));     // 1
        q2.answer = 0;

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);

        ParseClient.getInstance().saveUserQuestions(parseUser, questions);

        questions = ParseClient.getInstance().getQuestions(user);

        Log.d("DEBUG", "Test");
        */
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

        Profile profile = data.getParcelableExtra("profile");
        onFbProfileChange(profile);

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
