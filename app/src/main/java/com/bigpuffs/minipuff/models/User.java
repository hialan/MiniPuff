package com.bigpuffs.minipuff.models;


import com.facebook.Profile;

import java.util.ArrayList;

public class User {
    /**
     * Following information is from public_profile of Facebook
     */
    public String id;
    public String name;
    public String firstName;
    public String lastName;
    public String ageRange; // TODO: check type
    public String gender;
    public String locale;
    public float timezone; // The person's current timezone offset from UTC (min: -24) (max: 24)

    /**
     * Following information is from user_location of Facebook
     */
    public String livesIn; // TODO: check type

    /**
     * Following information is from user_birthday of Facebook
     */
    public String birthday; // TODO: check type

    /**
     * Following information is from user_friends of Facebook
     *
     * Return format from Facebook API:
     *
     * {
     *  "data": [
     *            {
     *              "name": "",
     *              "id": ""
     *            },
     *          ]
     *  }
     */
    public ArrayList<UserFacebookFriend> userFriends;

    public static User fromFacebookProfile(Profile profile) {

        if (profile == null) {
            return null;
        }

        User user = new User();

        user.id = profile.getId();
        user.name = profile.getName();
        user.firstName = profile.getFirstName();
        user.lastName = profile.getLastName();

        return user;
    }
}
