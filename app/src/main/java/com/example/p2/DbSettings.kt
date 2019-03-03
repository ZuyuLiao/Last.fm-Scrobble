package com.example.p2

import android.provider.BaseColumns

class DbSettings {
    companion object {
        const val DB_NAME = "favourites.db"
        const val DB_VERSION = 1
    }

    class DBFavoriteEntry: BaseColumns {
        companion object {
            const val TABLE = "favorites"
            const val ID = BaseColumns._ID
            const val COL_mbid = "mbid"
            const val COL_name = "name"
            const val COL_image = "image"
            const val COL_playcount = "playcount"
            const val COL_artist = "artist"
            const val COL_url = "url"
        }
    }

}