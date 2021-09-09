package com.example.githubuserapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("total_count")
    int total_count;
    @SerializedName("items")
    List<UserInfo> items;

    public int getTotal_count() {
        return total_count;
    }

    public List<UserInfo> getItems() {
        return items;
    }
}
