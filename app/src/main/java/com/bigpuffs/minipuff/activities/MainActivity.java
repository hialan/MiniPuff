package com.bigpuffs.minipuff.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bigpuffs.minipuff.models.Candidate;
import com.bigpuffs.minipuff.adapters.CandidatesAdapter;
import com.bigpuffs.minipuff.R;
import com.bigpuffs.minipuff.models.Question;
import com.bigpuffs.minipuff.models.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String USER = "User";
    private SharedPreferences userSharedPref;

    private User user;

    private ArrayList<Candidate> candidates;
    private CandidatesAdapter aCandidates;
    private GridView gvCandidates;

    // Facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private Profile currentFbProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        initFacebook();
        initParse();

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

    private void initFacebook() {
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile, user_birthday, user_location, user_friends");

        // update current profile
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                MainActivity.this.currentFbProfile = currentProfile;
            }
        };

        // If user has login in this Android device, then we can get Profile object
        // from persistence storage
        currentFbProfile = Profile.getCurrentProfile();
        user = User.fromFacebookProfile(currentFbProfile);
    }

    private void initParse() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, this.getString(R.string.PARSE_APP_ID),
                this.getString(R.string.PARSE_CLIENT_KEY));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
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

    public ParseObject createNewParseUser(User user) {
        ParseObject parseUser = new ParseObject("User");
        parseUser.put("id", user.id);
        parseUser.put("name", user.name);
        parseUser.put("firstName", user.firstName);
        parseUser.put("lastName", user.lastName);
        parseUser.put("ageRange", user.ageRange);
        parseUser.put("gender", user.gender);
        parseUser.put("locale", user.locale);
        parseUser.put("timezone", user.timezone);
        parseUser.put("livesIn", user.livesIn);
        parseUser.put("birthday", user.birthday);
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    // The save failed.
                    Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                    Log.d(getClass().getSimpleName(), "error: " + e);
                }
            }
        });
        return parseUser;
    }

    public void saveUserQuestions(ParseObject parseUser, ArrayList<Question> questions) {
        ParseObject parseQuestions = new ParseObject("Question");
        parseQuestions.put("user", parseUser);
        parseQuestions.put("questions", questions);
        parseQuestions.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    // The save failed.
                    Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                    Log.d(getClass().getSimpleName(), "error: " + e);
                }
            }
        });
    }
    

}
