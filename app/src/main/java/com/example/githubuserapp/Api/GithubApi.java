package com.example.githubuserapp.Api;

import com.example.githubuserapp.Model.DetailUser;
import com.example.githubuserapp.Model.UserInfo;
import com.example.githubuserapp.Model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("/users/{username}")
    Call<DetailUser> getDetailUser(@Path("username") String username, @Header("Authorization") String token);

    @GET("search/users")
    Call<UserResponse>SearchUser(@Query("q") String username, @Header("Authorization") String token);

    @GET("/users/{username}/followers")
    Call<List<UserInfo>>getFollower(@Path("username") String username, @Header("Authorization") String token);

    @GET("/users/{username}/following")
    Call<List<UserInfo>>getFollowing(@Path("username") String username, @Header("Authorization") String token);
}
