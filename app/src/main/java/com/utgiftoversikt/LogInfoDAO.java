package com.utgiftoversikt;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface LogInfoDAO {
    @Query("SELECT * FROM loginfo")
    List<LogInfo> getAll();

    @Insert
    void insertAll(LogInfo... logInfos);

    @Query("DELETE FROM loginfo")
    void deleteAll();
}
