package com.example.p2

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.util.Log
import com.example.p2.model.Artist
import com.example.p2.model.Track
import com.example.p2.model.TrackDetail

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private var _favouriteDBHelper: FavoritesDatabaseHelper = FavoritesDatabaseHelper(application)
    private var _trackList: MutableLiveData<ArrayList<Track>> = MutableLiveData()

    fun getNewProducts(): MutableLiveData<ArrayList<Track>> {
        loadProducts("?method=chart.gettoptracks&api_key=e835969e810d2b14391b9c6b8aa36444&format=json")
        return _trackList
    }

    fun getProductsByQueryText(query: String): MutableLiveData<ArrayList<Track>>{
        loadProducts("?method=artist.gettoptracks&artist="+query+"&api_key=e835969e810d2b14391b9c6b8aa36444&format=json")
        return _trackList
    }

    fun getTrackDetail(name: String, artist: String):MutableLiveData<ArrayList<Track>>{
        loadProducts("?method=track.getInfo&api_key=e835969e810d2b14391b9c6b8aa36444&artist=" + artist + "&track=" + name + "&format=json")
        return _trackList
    }



    private fun loadProducts(query: String) {
        ProductAsyncTask().execute(query)
    }

    @SuppressLint("StaticFieldLeak")
    inner class ProductAsyncTask : AsyncTask<String, Unit, ArrayList<Track>>() {
        override fun doInBackground(vararg params: String?): ArrayList<Track>? {
            return QueryUtils.fetchProductData(params[0]!!)
        }

        override fun onPostExecute(result: ArrayList<Track>?) {
            if (result == null) {
                Log.e("RESULTS", "No Results Found")
            } else {
                val newFavorite = ArrayList<Track>()
                var al = loadFavorites()
                for (i in result) {
                    for (j in al) {
                        if (i.getUrl() == j.getUrl()) {
                            i.isFavorite = true
                        }
                        break
                    }
                    newFavorite.add(i)
                }
                _trackList.value = newFavorite
            }
        }
    }


    fun getFavorites(): MutableLiveData<ArrayList<Track>> {
        val returnList = this.loadFavorites()
        this._trackList.value = returnList
        return this._trackList
    }

    private fun loadFavorites(): ArrayList<Track> {
        val favorites: ArrayList<Track> = ArrayList()
        val database = this._favouriteDBHelper.readableDatabase

        val cursor = database.query(
            DbSettings.DBFavoriteEntry.TABLE,
            null,
            null, null, null, null, DbSettings.DBFavoriteEntry.COL_mbid //                   why this?????????????
        )

        while (cursor.moveToNext()) {
            val cursorMBID = cursor.getColumnIndex(DbSettings.DBFavoriteEntry.COL_mbid)
            val cursorArtist = cursor.getColumnIndex(DbSettings.DBFavoriteEntry.COL_artist)
            val cursorIMAGE = cursor.getColumnIndex(DbSettings.DBFavoriteEntry.COL_image)
            val cursorNAME = cursor.getColumnIndex(DbSettings.DBFavoriteEntry.COL_name)
            val cursorPLAYCOUNT = cursor.getColumnIndex(DbSettings.DBFavoriteEntry.COL_playcount)
            val cursorURL = cursor.getColumnIndex(DbSettings.DBFavoriteEntry.COL_url)




            val track = Track(
                cursor.getString(cursorMBID),
                cursor.getString(cursorNAME),
                cursor.getString(cursorIMAGE),
                cursor.getString(cursorPLAYCOUNT),
                cursor.getString(cursorArtist),
                cursor.getString(cursorURL)
            )
            track.isFavorite = true
            favorites.add(track)
        }

        cursor.close()
        database.close()

        return favorites
    }

    fun addFavorite(track: Track) {
        val database: SQLiteDatabase = this._favouriteDBHelper.writableDatabase

        val favValues = ContentValues()
        favValues.put(DbSettings.DBFavoriteEntry.COL_artist, track.getArtist())
        favValues.put(DbSettings.DBFavoriteEntry.COL_image, track.getImages())
        favValues.put(DbSettings.DBFavoriteEntry.COL_mbid, track.getMbid())
        favValues.put(DbSettings.DBFavoriteEntry.COL_name,track.getName())
        favValues.put(DbSettings.DBFavoriteEntry.COL_playcount, track.getPlayCount())
        favValues.put(DbSettings.DBFavoriteEntry.COL_url, track.getUrl())
        database.insertWithOnConflict(
            DbSettings.DBFavoriteEntry.TABLE,
            null,
            favValues,
            SQLiteDatabase.CONFLICT_REPLACE
        )

        database.close()
    }

    fun removeFavorite(name: String, isFromResultList: Boolean = false) {
        // TODO: Implement this function according to the pseudocode given
        val database: SQLiteDatabase = this._favouriteDBHelper.writableDatabase
        //val s = arrayOf(DbSettings.DBFavoriteEntry.ID,id)
        database.delete(
            DbSettings.DBFavoriteEntry.TABLE,
            "${DbSettings.DBFavoriteEntry.COL_name}=?",
            arrayOf(name)
        )                               // last two arguments lzy
        database.close()
        val copy = _trackList.value
        var i = 0
        if (copy != null) {
            for ((index, value) in copy.withIndex()) {
                if (value.getName() == name) {
                    i = index
                }
                if (isFromResultList) {
                    value.isFavorite = false
                } else {
                    copy.removeAt(i)
                }
            }
            _trackList.value = copy

        }

    }
}
