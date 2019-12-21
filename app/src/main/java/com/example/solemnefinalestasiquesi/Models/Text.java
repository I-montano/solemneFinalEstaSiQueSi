package com.example.solemnefinalestasiquesi.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "text")
public class Text {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String message;

    public Text() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
