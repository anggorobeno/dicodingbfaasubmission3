package com.example.githubuserapp.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.githubuserapp.Adapter;
import com.example.githubuserapp.R;
import com.example.githubuserapp.ViewModel.FragmentFollowerViewModel;
import com.example.githubuserapp.utils.Constants;

import java.util.Objects;


public class FragamentFollower extends Fragment {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private FragmentFollowerViewModel followerViewModel;
    private ProgressBar progressBar;
    private TextView messageFollower;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragament_follower, container, false);
        recyclerView = view.findViewById(R.id.rv_followers);
        messageFollower = view.findViewById(R.id.test);
        progressBar = view.findViewById(R.id.progressBarFollower);
        showRecyclerView();

        @SuppressLint("UseRequireInsteadOfGet")
        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.EXTRA_PERSON);
        Log.e("Error Fragment", String.valueOf(username));
        followerViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FragmentFollowerViewModel.class);
        followerViewModel.SetFollowerData(username);
        getFollowerData();
        return view;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private void getFollowerData(){
        followerViewModel.getFollowersData().observe((Objects.requireNonNull(getActivity())), user -> {
            if (user.isEmpty()){

                messageFollower.setText(R.string.no_follower);
                messageFollower.setVisibility(View.VISIBLE);
                adapter.clearRV(user);
                showLoading(false);

            }
            else{
                adapter.setData(user);
                recyclerView.setAdapter(adapter);
                showLoading(false);

            }
        });
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void showRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new Adapter(getContext());
        adapter.notifyDataSetChanged();
    }
}