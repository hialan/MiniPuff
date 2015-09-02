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
        try {
            parseUser.save();
            return parseUser;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveUserQuestions(ParseObject parseUser, ArrayList<Question> questions) {
        ParseObject parseQuestions = new ParseObject("Question");
        parseQuestions.put("user", parseUser);
        parseQuestions.put("questions", questions);
        try {
            parseQuestions.save();
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ParseObject> getCandidates(User user, int offset) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereNotEqualTo("id", user.id);
        query.setLimit(5);
        query.setSkip(offset);
        try {
            List<ParseObject> candidateList = query.find();
            return candidateList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



}
