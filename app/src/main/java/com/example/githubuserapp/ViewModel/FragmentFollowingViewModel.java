package com.example.githubuserapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubuserapp.Api.GithubApi;
import com.example.githubuserapp.Api.RetrofitInstance;
import com.example.githubuserapp.Model.UserInfo;
import com.example.githubuserapp.utils.Constants;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFollowingViewModel extends ViewModel {
    private final MutableLiveData<List<UserInfo>> userFollowings = new MutableLiveData<>();
    public void SetFollowingData(String username){
        requestApi(username);
    }

    private void requestApi(String username) {
        GithubApi githubApi = RetrofitInstance.getRetrofitInstance().create(GithubApi.class);
        Call<List<UserInfo>> call = githubApi.getFollowing(username, Constants.ApiKey);
        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                if(response.body() != null) {
                    userFollowings.setValue(response.body());
                    Log.e("Success", String.valueOf(response.body()));
                }
                else{
                    Log.e("Following Can't be null", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Log.e("Cant get user Followings", Objects.requireNonNull(t.getMessage()));

            }
        });
    }
    public LiveData<List<UserInfo>> getFollowingsData(){
        return userFollowings;
    }

}
