package com.example.githubuserapp.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.githubuserapp.Adapter;
import com.example.githubuserapp.R;
import com.example.githubuserapp.ViewModel.FragmentFollowingViewModel;
import com.example.githubuserapp.utils.Constants;

import java.util.Objects;


public class FragmentFollowing extends Fragment {
    private Adapter adapter;
    private RecyclerView recyclerView;
    private FragmentFollowingViewModel followingViewModel;
    private ProgressBar progressBarFollowing;
    private TextView messageFollowing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        recyclerView = view.findViewById(R.id.rv_followings);
        messageFollowing = view.findViewById(R.id.test);
        progressBarFollowing = view.findViewById(R.id.progressBarFollowing);
        showRecyclerView();
        @SuppressLint("UseRequireInsteadOfGet")
        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Constants.EXTRA_PERSON);
        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FragmentFollowingViewModel.class);
        followingViewModel.SetFollowingData(username);
        getFollowingData();
        return view;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private void getFollowingData() {
        followingViewModel.getFollowingsData().observe((Objects.requireNonNull(getActivity())), user -> {
            if (user.isEmpty()){
                messageFollowing.setText(R.string.no_followings);
                messageFollowing.setVisibility(View.VISIBLE);
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
            progressBarFollowing.setVisibility(View.VISIBLE);
        } else {
            progressBarFollowing.setVisibility(View.INVISIBLE);
        }
    }
    private void showRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new Adapter(getContext());
        adapter.notifyDataSetChanged();
    }
}