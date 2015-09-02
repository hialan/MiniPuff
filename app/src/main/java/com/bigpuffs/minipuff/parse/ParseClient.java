package com.bigpuffs.minipuff.parse;


import android.util.Log;

import com.bigpuffs.minipuff.models.Question;
import com.bigpuffs.minipuff.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ParseClient {

    public static ParseObject createNewParseUser(User user) {
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
                    Log.d("success", "user saved");
                } else {
                    Log.d(getClass().getSimpleName(), "error: " + e);
                }
            }
        });
        return parseUser;
    }

    public static void saveUserQuestions(ParseObject parseUser, ArrayList<Question> questions) {
        ParseObject parseQuestions = new ParseObject("Question");
        parseQuestions.put("user", parseUser);
        parseQuestions.put("questions", questions);
        parseQuestions.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("success", "questions saved");
                } else {
                    Log.d(getClass().getSimpleName(), "error: " + e);
                }
            }
        });
    }

    public static ArrayList<ParseObject> getCandidates(User user, int offset) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereNotEqualTo("id", user.id);
        query.setLimit(5);
        query.setSkip(offset);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.d("success", list.toString());
                } else {
                    Log.d(getClass().getSimpleName(), "error: " + e);
                }
            }
        });
        return null;
    }

}
