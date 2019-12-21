package com.example.solemnefinalestasiquesi.API;

import com.example.solemnefinalestasiquesi.DAO.TextDao;
import com.example.solemnefinalestasiquesi.DataBase.AppDatabase;
import com.example.solemnefinalestasiquesi.DataBase.ChatApp;
import com.example.solemnefinalestasiquesi.Models.Text;

import java.util.ArrayList;
import java.util.List;

public class ChatApi {

    public void deleteAllMessages() {
        AppDatabase db = ChatApp.getDatabase();
        TextDao productDao = db.textDao();
        productDao.nudeTable();
    }

    public void recreateAllMessages(ArrayList<String> messages) {
        AppDatabase db = ChatApp.getDatabase();
        for(int i = 0; i < messages.size(); i++){
            TextDao textDao = db.textDao();
            String message = messages.get(i);
            textDao.insert(message);
        }
    }

    public List<Text> getAllMessages() {
        AppDatabase db = ChatApp.getDatabase();
        TextDao textDao  = db.textDao();
        return textDao.getAll();
    }

    public void createNewMessage(String message) {
        AppDatabase db = ChatApp.getDatabase();
        TextDao textDao = db.textDao();
        textDao.insert(message);
    }
}
