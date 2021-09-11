package com.example.githubuserapp.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.example.githubuserapp.R;
import com.example.githubuserapp.database.FavDAO;
import com.example.githubuserapp.database.FavoriteDatabase;
import com.example.githubuserapp.model.UserInfo;
import com.example.githubuserapp.utils.Constants;
import com.example.githubuserapp.viewModel.DetailUserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;


public class DetailUser extends AppCompatActivity {
    private DetailUserViewModel detailUserViewModel;
    private static Context context;
    FloatingActionButton fabFavorite;
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
        fabFavorite = findViewById(R.id.fab_favorite);
        UserInfo user = getIntent().getParcelableExtra(Constants.FAV_PERSON);
        if (user != null){
            Log.e("Error cuyyy", String.valueOf(user));

        }
        String username = getIntent().getStringExtra(Constants.EXTRA_PERSON);
        String uname = user.getUsername();
        Log.e("Error cuyyy3", String.valueOf(uname));

        setTitle(uname);
        FavDAO favDAO = Room.databaseBuilder(this, FavoriteDatabase.class, "userinfo")
                .allowMainThreadQueries()
                .build()
                .favDAO();
        UserInfo check = favDAO.getUserByUsername(uname);

        if (check != null) {
            fabFavorite.setVisibility(View.GONE);
        } else {
            fabFavorite.setOnClickListener(view -> {
                favDAO.insertAll(user);
                Log.e("Error cuyyy2", String.valueOf(check));

                Toast.makeText(this, R.string.succes_fav, Toast.LENGTH_SHORT).show();

                fabFavorite.setVisibility(View.GONE);
            });
        }
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
                    fragment = new FragmentFollower();
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
