package com.example.solemnefinalestasiquesi.DAO;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.solemnefinalestasiquesi.Models.Text;

import java.util.List;

@Dao
public interface TextDao {
    @Query("SELECT * FROM text")
    List<Text> getAll();

    @Query("SELECT * FROM text where id = (:id)")
    Text getById(int id);
}
