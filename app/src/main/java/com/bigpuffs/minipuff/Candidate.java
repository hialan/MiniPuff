package com.bigpuffs.minipuff;

import java.io.Serializable;

public class Candidate implements Serializable {
    private static final long serialVersionUID = -1100409068878822422L;

    public String imageUrl;

    public Candidate(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
