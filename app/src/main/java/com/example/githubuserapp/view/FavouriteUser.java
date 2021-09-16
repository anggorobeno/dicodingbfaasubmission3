package com.example.githubuserapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubuserapp.Adapter;
import com.example.githubuserapp.FavouriteAdapter;
import com.example.githubuserapp.R;
import com.example.githubuserapp.database.FavDAO;
import com.example.githubuserapp.database.FavoriteDatabase;
import com.example.githubuserapp.model.UserInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.mateware.snacky.Snacky;

public class FavouriteUser extends AppCompatActivity {
    private RecyclerView recyclerViewFavourite;
    private FavouriteAdapter adapter;
    private LinearLayout linearNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_user);
        recyclerViewFavourite = findViewById(R.id.rv_favourite);
        linearNotFound = findViewById(R.id.userNotFound);

        showRecyclerView();

        readDatabase();

        setTitle(getString(R.string.fav_title));



    }

    private void deleteDB() {
        final android.app.AlertDialog.Builder deleteFavAlert = new AlertDialog.Builder(this);
        deleteFavAlert.setMessage("Are u sure?");
        deleteFavAlert.setTitle("Delete all users");
        deleteFavAlert.setCancelable(false);
        deleteFavAlert.setPositiveButton("yes",(dialogInterface,i) -> {
        FavDAO favDAO = Room.databaseBuilder(this, FavoriteDatabase.class, "userinfo")
                .allowMainThreadQueries()
                .build()
                .favDAO();
        List<UserInfo> listUserFavourite = favDAO.getFavouriteData();
        ArrayList<UserInfo> arrayUser = new ArrayList<>(listUserFavourite);
        favDAO.deleteAll();
        adapter.clearRV(arrayUser);
        adapter.notifyDataSetChanged();
            Snacky.builder()
                    .setActivity(FavouriteUser.this)
                    .setText("Favourite Users Has Been Deleted")
                    .setDuration(Snacky.LENGTH_SHORT)
                    .setActionText("OK")
                    .success()
                    .show();
            linearNotFound.setVisibility(View.VISIBLE);

        });
        deleteFavAlert.setNegativeButton("no",((dialogInterface, i) -> deleteFavAlert.setCancelable(true)));
        deleteFavAlert.show();

    }

    private void showRecyclerView() {
        recyclerViewFavourite.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavouriteAdapter(getBaseContext());
        adapter.notifyDataSetChanged();
        recyclerViewFavourite.setAdapter(adapter);


    }

    private void readDatabase() {
        FavDAO favDAO = Room.databaseBuilder(this, FavoriteDatabase.class, "userinfo")
                .allowMainThreadQueries()
                .build()
                .favDAO();
        List<UserInfo> listUserFavourite = favDAO.getFavouriteData();
        ArrayList<UserInfo> arrayUser = new ArrayList<>(listUserFavourite);
        adapter.setData(arrayUser);
        if (listUserFavourite.isEmpty()){
            linearNotFound.setVisibility(View.VISIBLE);
            adapter.clearRV(arrayUser);
        }


    }


    Menu mMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        mMenu = menu;
        if (linearNotFound.getVisibility() == View.VISIBLE){
            mMenu.findItem(R.id.deleteAllMenu).setVisible(false);

        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deleteAllMenu) {
            deleteDB();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}