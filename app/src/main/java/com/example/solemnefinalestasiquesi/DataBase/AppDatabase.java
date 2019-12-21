package com.example.solemnefinalestasiquesi.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.solemnefinalestasiquesi.DAO.TextDao;
import com.example.solemnefinalestasiquesi.Models.Text;

@Database(entities = {Text.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TextDao textDao();
}
