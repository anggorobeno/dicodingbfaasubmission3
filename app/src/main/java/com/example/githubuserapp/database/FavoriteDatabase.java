package com.example.githubuserapp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.githubuserapp.model.UserInfo;

@Database(entities = {UserInfo.class},exportSchema = false, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavDAO favDAO();

}
