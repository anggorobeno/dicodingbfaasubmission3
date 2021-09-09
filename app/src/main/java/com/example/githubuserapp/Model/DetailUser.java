package com.example.githubuserapp.Model;

import com.google.gson.annotations.SerializedName;

public class DetailUser {
    @SerializedName("login")
    String username;
    @SerializedName("name")
    private String name;
    @SerializedName("followers")
    int follower;
    @SerializedName("following")
    int following;
    @SerializedName("avatar_url")
    String avatarUrl;
    @SerializedName("location")
    String location;
    @SerializedName("public_repos")
    int repository;

    public String getCompany() {
        return company;
    }

    @SerializedName("company")
    String company;

    public int getFollower() {
        return follower;
    }

    public int getFollowing() {
        return following;
    }

    public int getRepository() {
        return repository;
    }

    public String getLocation() {
        return location;
    }



    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }



    public String getAvatarUrl(){
        return avatarUrl;
    }
}
