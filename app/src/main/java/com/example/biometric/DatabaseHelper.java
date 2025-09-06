package com.example.biometric;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biometric_registry.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.UserEntry.TABLE_NAME + " (" +
                    DatabaseContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    DatabaseContract.UserEntry.COLUMN_COUNTRY + " TEXT NOT NULL," +
                    DatabaseContract.UserEntry.COLUMN_CITY + " TEXT NOT NULL," +
                    DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE + " TEXT NOT NULL," +
                    DatabaseContract.UserEntry.COLUMN_FINGERPRINT_HASHES + " TEXT NOT NULL," +
                    DatabaseContract.UserEntry.COLUMN_IRIS_HASH + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.UserEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserEntry.COLUMN_NAME, user.getName());
        values.put(DatabaseContract.UserEntry.COLUMN_COUNTRY, user.getCountry());
        values.put(DatabaseContract.UserEntry.COLUMN_CITY, user.getCity());
        values.put(DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE, user.getRegistrationDate());
        values.put(DatabaseContract.UserEntry.COLUMN_FINGERPRINT_HASHES, user.getFingerprintHashes());
        values.put(DatabaseContract.UserEntry.COLUMN_IRIS_HASH, user.getIrisHash());

        long newRowId = db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DatabaseContract.UserEntry._ID,
                DatabaseContract.UserEntry.COLUMN_NAME,
                DatabaseContract.UserEntry.COLUMN_COUNTRY,
                DatabaseContract.UserEntry.COLUMN_CITY,
                DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE,
                DatabaseContract.UserEntry.COLUMN_FINGERPRINT_HASHES,
                DatabaseContract.UserEntry.COLUMN_IRIS_HASH
        };

        Cursor cursor = db.query(
                DatabaseContract.UserEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE + " DESC"
        );

        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry._ID)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME)));
            user.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_COUNTRY)));
            user.setCity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_CITY)));
            user.setRegistrationDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE)));
            user.setFingerprintHashes(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_FINGERPRINT_HASHES)));
            user.setIrisHash(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_IRIS_HASH)));
            userList.add(user);
        }
        cursor.close();
        db.close();
        return userList;
    }

    public User getUserById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] projection = {
                DatabaseContract.UserEntry._ID,
                DatabaseContract.UserEntry.COLUMN_NAME,
                DatabaseContract.UserEntry.COLUMN_COUNTRY,
                DatabaseContract.UserEntry.COLUMN_CITY,
                DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE,
                DatabaseContract.UserEntry.COLUMN_FINGERPRINT_HASHES,
                DatabaseContract.UserEntry.COLUMN_IRIS_HASH
        };

        String selection = DatabaseContract.UserEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                DatabaseContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry._ID)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_NAME)));
            user.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_COUNTRY)));
            user.setCity(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_CITY)));
            user.setRegistrationDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_REGISTRATION_DATE)));
            user.setFingerprintHashes(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_FINGERPRINT_HASHES)));
            user.setIrisHash(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_IRIS_HASH)));
        }
        cursor.close();
        db.close();
        return user;
    }
}
