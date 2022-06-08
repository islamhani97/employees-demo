package com.islam.android.apps.employeesdemo;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Employee.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "EmployeesDatabase";

    private static Database instance;

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, Database.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
//                    .addCallback(callback)
                    .build();
        }
        return instance;
    }
    public abstract Dao dao();

}
