package com.example.solemnefinalestasiquesi.DataBase;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class ChatApp extends Application {
    private static Context context;
    private static AppDatabase database;

    public void onCreate() {
        super.onCreate();
        ChatApp.context = getApplicationContext();
        ChatApp.database = initDatabase();
    }

    public static Context getAppContext() {
        return ChatApp.context;
    }

    public static AppDatabase getDatabase() {
        return ChatApp.database;
    }

    private AppDatabase initDatabase() {
        AppDatabase database = Room.databaseBuilder(getAppContext(), AppDatabase.class, "chatapp.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        return database;
    }
}
