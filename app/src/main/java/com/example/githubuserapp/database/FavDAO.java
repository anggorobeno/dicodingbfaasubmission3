package com.example.githubuserapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.githubuserapp.model.UserInfo;

import java.util.List;

@Dao
public interface FavDAO {
    @Query("SELECT * FROM userinfo ORDER BY id DESC ")
    List<UserInfo> getFavouriteData();
    @Query("DELETE FROM userinfo WHERE id=:id")
    void deleteUsername(int id);

    @Query("SELECT * FROM userinfo WHERE username = :username")
    UserInfo getUserByUsername(String username);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(UserInfo... userInfos);
    @Query("DELETE FROM userinfo")
    void deleteAll();


}
