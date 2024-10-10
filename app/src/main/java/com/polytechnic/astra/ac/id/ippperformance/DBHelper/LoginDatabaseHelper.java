package com.polytechnic.astra.ac.id.ippperformance.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.polytechnic.astra.ac.id.ippperformance.Model.Login;

import java.util.ArrayList;
import java.util.List;

public class LoginDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "IPPDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_LOGIN = "login";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public LoginDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_LOGIN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }

    public List<Login> getAllLogin() {
        List<Login> logins = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_LOGIN, new String[]{COLUMN_USERNAME, COLUMN_PASSWORD}, null, null, null, null, null);
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);

            if (usernameIndex == -1 || passwordIndex == -1) {
                // Handle the case where the column does not exist
                throw new RuntimeException("Columns " + COLUMN_USERNAME + " or " + COLUMN_PASSWORD + " do not exist in the database.");
            }

            while (cursor.moveToNext()) {
                String username = cursor.getString(usernameIndex);
                String password = cursor.getString(passwordIndex);
                logins.add(new Login(username, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., log the error, notify the user)
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return logins;
    }

    public boolean addLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_LOGIN, null, values);
        return result != -1;
    }

    public void deleteAllLogins() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, null, null);
    }
}
