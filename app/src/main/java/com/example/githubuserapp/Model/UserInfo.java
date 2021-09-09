package com.example.githubuserapp.Model;

import com.google.gson.annotations.SerializedName;

public class UserInfo{
    @SerializedName("login")
    String username;
    @SerializedName("avatar_url")
    String url;
    @SerializedName("type")
    String type;

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public UserInfo(String username, String url, String type) {
        this.username = username;
        this.url = url;
        this.type = type;
    }
}
