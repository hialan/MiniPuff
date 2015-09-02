package com.bigpuffs.minipuff.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Option {
    public boolean selected;
    public String text;

    public Option() { }

    public Option(String text, boolean selected) {
        this.text = text;
        this.selected = selected;
    }

    public Option(JSONObject json) {
        try {
            selected = json.getBoolean("selected");
            text = json.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();

        try {
            json.put("selected", selected);
            json.put("text", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
