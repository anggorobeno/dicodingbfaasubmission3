package com.example.githubuserapp.ViewModel;
import android.util.Log;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubuserapp.Api.GithubApi;
import com.example.githubuserapp.Model.UserResponse;
import com.example.githubuserapp.Api.RetrofitInstance;
import com.example.githubuserapp.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();

    public void SetSearchData(String username) {
        requestApi(username);

    }


    private void requestApi(String username) {
        GithubApi githubApi = RetrofitInstance.getRetrofitInstance().create(GithubApi.class);
        Call<UserResponse> call = githubApi.SearchUser(username, Constants.ApiKey);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.body() != null) {
                    userResponse.setValue(response.body());
                    Log.i("Success", String.valueOf(response.body()));
                }
                else {
                    Log.e("Can't be null", String.valueOf(response.body()));
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Cant get user Data", Objects.requireNonNull(t.getMessage()));

            }
        });
    }

    public LiveData<UserResponse> GetSearchData() {
        return userResponse;
    }
}