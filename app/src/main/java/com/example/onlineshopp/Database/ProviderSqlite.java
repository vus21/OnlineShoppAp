package com.example.onlineshopp.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.Provider;

public class ProviderSqlite extends ContentProvider {
    private static final String AUTHORITY = "com.example.myapplication.Database.ProviderSqlite";
    private static  String PATH = ConnectSQLite.TABLE_1;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
    private ConnectSQLite database;
    UriMatcher urimatcher;
    final int myurl=1;
    @Override
    public boolean onCreate() {
        urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
        urimatcher.addURI(AUTHORITY,PATH,myurl);
        database = new ConnectSQLite(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query(ConnectSQLite.TABLE_1, strings, s,
                strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = database.getWritableDatabase();
        switch (urimatcher.match(uri)){
            case myurl:
                break;

        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
