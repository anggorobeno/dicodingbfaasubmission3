package com.example.githubuserapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.githubuserapp.model.UserInfo;

import java.util.List;

@Dao
public interface FavDAO {

    @Query("SELECT * FROM userinfo WHERE username = :username")
    UserInfo getUserByUsername(String username);
    @Insert
    void insertAll(UserInfo... userInfos);

}
