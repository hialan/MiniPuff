package com.bigpuffs.minipuff.models;

import com.parse.ParseObject;

import java.util.ArrayList;

public class Candidate{
    public String userId;
    public ParseObject parseUser;
    public ArrayList<Question> questions;
}
