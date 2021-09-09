package com.example.githubuserapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.githubuserapp.Adapter;
import com.example.githubuserapp.R;
import com.example.githubuserapp.ViewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private EditText searchUser;
    private Button buttonSearch;
    private LinearLayout linearNotFound;
    private ProgressBar progressBar;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        searchUser = findViewById(R.id.editTextUsername);
        buttonSearch = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);
        linearNotFound = findViewById(R.id.userNotFound);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        mainActivityViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainActivityViewModel.class);

        showRecyclerView();
        buttonSearch.setOnClickListener(v -> {
            String username = searchUser.getText().toString();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(MainActivity.this, "Please enter a username!", Toast.LENGTH_SHORT).show();
            }
            else {
                mainActivityViewModel.SetSearchData(username);
                getUserData();
                showLoading(true);

            }
        });


        if (savedInstanceState != null){
            String keepData = savedInstanceState.getString("keepdata");

            mainActivityViewModel.SetSearchData(keepData);
            getUserData();
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("keepdata", searchUser.getText().toString());
    }

    private void getUserData() {
        mainActivityViewModel.GetSearchData().observe(this, user -> {
            if (user.getTotal_count() > 0){
                showLoading(false);
                adapter.setData(user.getItems());
                recyclerView.setAdapter(adapter);
                linearNotFound.setVisibility(View.GONE);

            }
            else{
                showLoading(false);
                adapter.clearRV(user.getItems());
                linearNotFound.setVisibility(View.VISIBLE);
            }
            searchUser.setText("");




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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new Adapter(getBaseContext());
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent (Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


