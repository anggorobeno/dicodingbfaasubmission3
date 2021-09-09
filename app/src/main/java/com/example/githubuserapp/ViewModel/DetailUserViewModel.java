package com.example.githubuserapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubuserapp.Api.GithubApi;
import com.example.githubuserapp.Api.RetrofitInstance;
import com.example.githubuserapp.Model.DetailUser;
import com.example.githubuserapp.Model.UserResponse;
import com.example.githubuserapp.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserViewModel extends ViewModel {
    private final MutableLiveData<DetailUser> userDetail = new MutableLiveData<>();
    public void SetDetailUser(String username){
        requestApi(username);

    }

    private void requestApi(String username) {
        GithubApi githubApi = RetrofitInstance.getRetrofitInstance().create(GithubApi.class);
        Call<DetailUser> call = githubApi.getDetailUser(username, Constants.ApiKey);
        call.enqueue(new Callback<DetailUser>() {
            @Override
            public void onResponse(Call<DetailUser> call, Response<DetailUser> response) {
                if (response.body() != null) {
                    userDetail.setValue(response.body());
                    Log.e("Success", String.valueOf(response.body()));
                }
                else {
                    Log.e("Detail User Can't be null", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<DetailUser> call, Throwable t) {
                Log.e("Cant get user Detail", Objects.requireNonNull(t.getMessage()));

            }
        });
    }
    public LiveData<DetailUser> GetDetailUser() {return userDetail;}

}
