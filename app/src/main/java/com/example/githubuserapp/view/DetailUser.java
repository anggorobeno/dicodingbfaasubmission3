package com.example.githubuserapp.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.githubuserapp.R;
import com.example.githubuserapp.utils.Constants;
import com.example.githubuserapp.viewModel.DetailUserViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;


public class DetailUser extends AppCompatActivity {
    private DetailUserViewModel detailUserViewModel;
    private static Context context;

    private ImageView avatar1;
    private TextView username, names, company, location, repository, follower, following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        SectionPagerAdapter mSection = new SectionPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mSection);
        context = getApplicationContext();
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        username = findViewById(R.id.detailUsername);
        avatar1 = findViewById(R.id.detailAvatar);
        company = findViewById(R.id.detailCompany);
        location = findViewById(R.id.detailLocation);
        repository = findViewById(R.id.detailRepository);
        follower = findViewById(R.id.detailFollower);
        following = findViewById(R.id.detailFollowing);
        String username = getIntent().getStringExtra(Constants.EXTRA_PERSON);
        setTitle(username);


        detailUserViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel.class);
        detailUserViewModel.SetDetailUser(username);
        getUserDetail();

    }

    private void getUserDetail() {
        detailUserViewModel.GetDetailUser().observe(this, user -> {
            Picasso.with(getBaseContext())
                    .load(user.getAvatarUrl())
                    .into(avatar1);
            username.setText(user.getUsername());
            if (user.getLocation() == null) {
                location.setVisibility(View.GONE);
            } else location.setText(getString(R.string.Location) + user.getLocation());
            if (user.getRepository() == 0) {
                repository.setVisibility(View.GONE);
            } else repository.setText(getString(R.string.repo) + user.getRepository());
            if (user.getFollower() == 0) {
                follower.setVisibility(View.GONE);
            } else follower.setText(getString(R.string.followers) + user.getFollower());
            if (user.getFollowing() == 0) {
                following.setVisibility(View.GONE);
            } else following.setText(getString(R.string.followings) + user.getFollowing());
            if (user.getCompany() == null) {
                company.setVisibility(View.GONE);
            } else company.setText(getString(R.string.company) + user.getCompany());


        });

    }

    public static class SectionPagerAdapter extends FragmentPagerAdapter {
        @StringRes
        private final int[] TAB_TITLES = new int[]{
                R.string.tab_follower,
                R.string.tab_following
        };

        public SectionPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new FragamentFollower();
                    break;
                case 1:
                    fragment = new FragmentFollowing();
                    break;
            }
            assert fragment != null;
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return context.getResources().getString(TAB_TITLES[position]);
        }

    }
}
