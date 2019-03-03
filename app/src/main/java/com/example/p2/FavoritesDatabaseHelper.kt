package com.example.p2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoritesDatabaseHelper(context: Context): SQLiteOpenHelper(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createFavoritesTableQuery = "CREATE TABLE " + DbSettings.DBFavoriteEntry.TABLE + " ( " +
                DbSettings.DBFavoriteEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSettings.DBFavoriteEntry.COL_artist + " TEXT NULL, " +
                DbSettings.DBFavoriteEntry.COL_image + " TEXT NULL, " +
                DbSettings.DBFavoriteEntry.COL_mbid + " TEXT NULL, " +
                DbSettings.DBFavoriteEntry.COL_name + " TEXT NULL, " +
                DbSettings.DBFavoriteEntry.COL_playcount + " TEXT NULL, " +
                DbSettings.DBFavoriteEntry.COL_url + " TEXT NULL);"
        db?.execSQL(createFavoritesTableQuery)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DbSettings.DBFavoriteEntry.TABLE)
        onCreate(db)
    }
}