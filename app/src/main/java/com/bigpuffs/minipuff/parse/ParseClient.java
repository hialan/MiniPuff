package com.bigpuffs.minipuff.parse;


import com.bigpuffs.minipuff.models.Candidate;
import com.bigpuffs.minipuff.models.Question;
import com.bigpuffs.minipuff.models.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ParseClient {

    final public static String TABLE_USER = "User";
    final public static String TABLE_MATCH_USER = "MatchUser";
    final public static String TABLE_QUESTION = "Question";

    private static ParseClient parseClient = null;

    // singleton
    private ParseClient () {}

    public static ParseClient getInstance() {
        if (parseClient == null) {
            parseClient = new ParseClient();
        }
        return parseClient;
    }

    public ParseObject createNewParseUser(User user) {
        ParseObject parseUser = new ParseObject(TABLE_USER);
        parseUser.put("id", user.id);
        parseUser.put("name", user.name);
        parseUser.put("firstName", user.firstName);
        parseUser.put("lastName", user.lastName);
        /* use put too much cause fail ?
         *
        parseUser.put("ageRange", user.ageRange);
        parseUser.put("gender", user.gender);
        parseUser.put("locale", user.locale);
        parseUser.put("timezone", user.timezone);
        parseUser.put("livesIn", user.livesIn);
        parseUser.put("birthday", user.birthday);
        */
        try {
            parseUser.save();
            return parseUser;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParseObject getParseUser(String userId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_USER);
        query.whereEqualTo("id", userId);

        try {
            ParseObject parseUser = query.getFirst();
            return parseUser;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveUserQuestions(ParseObject parseUser, ArrayList<Question> questions) {
        ParseObject parseQuestions = new ParseObject(TABLE_QUESTION);
        parseQuestions.put("user", parseUser);
        parseQuestions.put("userId", parseUser.getString("id"));

        JSONArray questionsJson = new JSONArray();
        for(Question q : questions) {
            questionsJson.put(q.toJSONObject());
        }
        parseQuestions.put("questions", questionsJson);
        try {
            parseQuestions.save();
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Question> getQuestions(User user) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_QUESTION);
        query.whereEqualTo("userId", user.id);
        try {
            ParseObject parseObject = query.getFirst();
            ArrayList<Question> questions = new ArrayList<>();

            JSONArray questionsJSON = parseObject.getJSONArray("questions");
            for (int i = 0; i < questionsJSON.length(); i++) {
                questions.add(new Question(questionsJSON.getJSONObject(i)));
            }
            return questions;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Candidate> getCandidate(User user, int offset) {
        HashMap<String, Boolean> matchResult = getMatchUserList(user);
        String[] excludeUserIds = matchResult.keySet().toArray(new String[matchResult.size()]);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_QUESTION);
        query.whereNotContainedIn("userId", Arrays.asList(excludeUserIds));
        query.setLimit(50);
        query.setSkip(offset);
        try {
            List<ParseObject> candidateList = query.find();
            List<Candidate> results = new ArrayList<>();
            for(ParseObject parseCand : candidateList) {
                Candidate candidate = new Candidate();
                candidate.userId = parseCand.getString("userId");
                candidate.parseUser = parseCand.getParseObject("user");
                candidate.questions = new ArrayList<>();

                JSONArray questionsJSON = parseCand.getJSONArray("questions");
                for(int i = 0; i < questionsJSON.length(); i++) {
                    candidate.questions.add(new Question(questionsJSON.getJSONObject(i)));
                }

                results.add(candidate);
            }
            return results;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveMatchResult(User user1, User user2, boolean match) {
        ParseObject parseObject = new ParseObject(TABLE_MATCH_USER);
        parseObject.put("id1", user1.id); // String
        parseObject.put("id2", user2.id); // String
        parseObject.put("match", match);  // boolean

        try {
            parseObject.save();
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<String, Boolean> getMatchUserList(User user) {
        HashMap<String, Boolean> matchList = new HashMap<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_MATCH_USER);
        query.whereEqualTo("id1", user.id);
        try {
            List<ParseObject> candidateList = query.find();
            for(ParseObject u : candidateList) {
                matchList.put(u.getString("id2"), u.getBoolean("match"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        query = ParseQuery.getQuery(TABLE_MATCH_USER);
        query.whereEqualTo("id2", user.id);
        try {
            List<ParseObject> candidateList = query.find();
            for(ParseObject u : candidateList) {
                matchList.put(u.getString("id1"), u.getBoolean("match"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return matchList;
    }

}
