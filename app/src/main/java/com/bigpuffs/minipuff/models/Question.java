package com.bigpuffs.minipuff.models;


import java.util.ArrayList;

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
}
