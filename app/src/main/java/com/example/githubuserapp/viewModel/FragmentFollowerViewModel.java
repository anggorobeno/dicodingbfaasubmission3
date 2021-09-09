package com.example.githubuserapp.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubuserapp.api.GithubApi;
import com.example.githubuserapp.api.RetrofitInstance;
import com.example.githubuserapp.model.UserInfo;
import com.example.githubuserapp.utils.Constants;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFollowerViewModel extends ViewModel {
    private final MutableLiveData<List<UserInfo>> userFollowers = new MutableLiveData<>();

    public void SetFollowerData(String username) {
        requestApi(username);
    }

    private void requestApi(String username) {
        GithubApi githubApi = RetrofitInstance.getRetrofitInstance().create(GithubApi.class);
        Call<List<UserInfo>> call = githubApi.getFollower(username, Constants.ApiKey);
        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                if (response.body() != null) {
                    userFollowers.setValue(response.body());
                    Log.i("Success get Follower Data", String.valueOf(response.body()));
                } else {
                    Log.e("Follower Can't be null", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Log.e("Cant get user Followers", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    public LiveData<List<UserInfo>> getFollowersData() {
        return userFollowers;
    }


}
