package com.bigpuffs.minipuff.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class Question {
    public String title;
    public ArrayList<Option> options;
    public int answer;

    public Question() {
        this.answer = -1;
        this.title = "";
        this.options = new ArrayList<>();
        this.options.add(new Option());
        this.options.add(new Option());
        this.options.add(new Option());
        this.options.add(new Option());
    }

    public Question(JSONObject json) {
        try {
            answer = json.getInt("answer");
            title = json.getString("title");

            options = new ArrayList<>();
            JSONArray jsonArray = json.getJSONArray("options");
            for(int i = 0; i < jsonArray.length(); i++) {
                options.add(new Option(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();

        try {
            json.put("title", title);
            json.put("answer", answer);

            Collection<JSONObject> opts = new ArrayList<>();
            for(Option opt : options) {
                opts.add(opt.toJSONObject());
            }
            json.put("options", opts);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
