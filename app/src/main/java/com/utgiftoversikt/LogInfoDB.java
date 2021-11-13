package com.utgiftoversikt;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities =  {LogInfo.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class LogInfoDB extends RoomDatabase {
    public abstract LogInfoDAO logInfoDAO();
}
