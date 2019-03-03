package com.example.p2

import android.text.TextUtils
import android.util.Log
import com.example.p2.model.Artist
import com.example.p2.model.Track
import com.example.p2.model.TrackDetail
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import kotlin.collections.ArrayList

class QueryUtils {
    companion object {
        private val LogTag = this::class.java.simpleName
        private const val BaseURL = "http://ws.audioscrobbler.com/2.0/" // localhost URL



        fun fetchProductData(jsonQueryString: String): ArrayList<Track>? {
            if(jsonQueryString == "?method=chart.gettoptracks&api_key=e835969e810d2b14391b9c6b8aa36444&format=json")
            {
                val url: URL? = createUrl(BaseURL + jsonQueryString)

                var jsonResponse: String? = null
                try {
                    jsonResponse = makeHttpRequest(url)
                } catch (e: IOException) {
                    Log.e(this.LogTag, "Problem making the HTTP request.", e)
                }

                return extractDataFromJson(jsonResponse)
            }
            else if(jsonQueryString.contains("track.getInfo"))
            {
                val url: URL? = createUrl(BaseURL + jsonQueryString)

                var jsonResponse: String? = null
                try {
                    jsonResponse = makeHttpRequest(url)
                } catch (e: IOException) {
                    Log.e(this.LogTag, "Problem making the HTTP request.", e)
                }
                return extractDataFromJson3(jsonResponse)
            }
            else
            {
                val url: URL? = createUrl(BaseURL + jsonQueryString)

                var jsonResponse: String? = null
                try {
                    jsonResponse = makeHttpRequest(url)
                } catch (e: IOException) {
                    Log.e(this.LogTag, "Problem making the HTTP request.", e)
                }
                return extractDataFromJson2(jsonResponse)
            }

        }

        private fun createUrl(stringUrl: String): URL? {
            var url: URL? = null
            try {
                url = URL(stringUrl)
            }
            catch (e: MalformedURLException) {
                Log.e(this.LogTag, "Problem building the URL.", e)
            }

            return url
        }

        private fun makeHttpRequest(url: URL?): String {
            var jsonResponse = ""

            if (url == null) {
                return jsonResponse
            }

            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.readTimeout = 10000 // 10 seconds
                urlConnection.connectTimeout = 15000 // 15 seconds
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                if (urlConnection.responseCode == 200) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                }
                else {
                    Log.e(this.LogTag, "Error response code: ${urlConnection.responseCode}")
                }
            }
            catch (e: IOException) {
                Log.e(this.LogTag, "Problem retrieving the product data results: $url", e)
            }
            finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }

            return jsonResponse
        }

        private fun readFromStream(inputStream: InputStream?): String {
            val output = StringBuilder()
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
                val reader = BufferedReader(inputStreamReader)
                var line = reader.readLine()
                while (line != null) {
                    output.append(line)
                    line = reader.readLine()
                }
            }

            return output.toString()
        }

        private fun extractDataFromJson3(productJson: String?): ArrayList<Track>? {
            if (TextUtils.isEmpty(productJson)) {
                return null
            }
            var track =ArrayList<Track>()
            try {
                val baseJsonResponse = JSONObject(productJson).getJSONObject("track")

                    //artist
                    val album = returnValueOrDefault<JSONObject>(baseJsonResponse, "album") as JSONObject?
                    var albumname = ""
                    if(album != null)
                    {

                        albumname = returnValueOrDefault<String>(album, "artist") as String

                    }

                    //image
                    val images = returnValueOrDefault<JSONArray>(baseJsonResponse, "image") as JSONArray?
                    var image = ""
                    if (images != null) {
                        val ilist = images.getJSONObject(2)
                        image = returnValueOrDefault<String>(ilist, "#text") as String
                        print("11111111111111111111")
                    }

                    //wiki
                    val wiki = returnValueOrDefault<JSONObject>(baseJsonResponse, "wiki") as JSONObject?
                    var content = ""
                    if (wiki != null) {

                        content = returnValueOrDefault<String>(wiki, "content") as String
                        print("11111111111111111111")
                    }

                    //artist
                    val artist = returnValueOrDefault<JSONObject>(baseJsonResponse, "artist") as JSONObject?
                    var a = ""
                    if(artist != null)
                    {

                        a = returnValueOrDefault<String>(artist, "name") as String

                    }

                    track.add(
                        Track(
                            returnValueOrDefault<String>(baseJsonResponse, "mbid") as String,
                            returnValueOrDefault<String>(baseJsonResponse, "name") as String,
                            image,
                            returnValueOrDefault<String>(baseJsonResponse, "playcount") as String,
                            a,
                            returnValueOrDefault<String>(baseJsonResponse, "url") as String,
                            albumname,
                            content
                    )
                    )


            }
            catch (e: JSONException) {
                Log.e(this.LogTag, "Problem parsing the product JSON results", e)
            }

            return track
        }

        private fun extractDataFromJson2(productJson: String?): ArrayList<Track>? {
            if (TextUtils.isEmpty(productJson)) {
                return null
            }

            val productList = ArrayList<Track>()
            try {
                val baseJsonResponse = JSONObject(productJson)
                val tracksObj = baseJsonResponse.getJSONObject("toptracks")
                val trackArray = tracksObj.getJSONArray("track")
                for (i in 0 until trackArray.length()) {
                    val productObject = trackArray.getJSONObject(i)

                    //artist
                    val artist = returnValueOrDefault<JSONObject>(productObject, "artist") as JSONObject?
                    var a = ""
                    if(artist != null)
                    {

                        a = returnValueOrDefault<String>(artist, "name") as String

                    }

                    //image
                    val images = returnValueOrDefault<JSONArray>(productObject, "image") as JSONArray?
                    var image = ""
                    if (images != null) {
                        val ilist = images.getJSONObject(2)
                        image = returnValueOrDefault<String>(ilist, "#text") as String
                        print("11111111111111111111")
                    }


                    productList.add(Track(
                        returnValueOrDefault<String>(productObject, "mbid") as String,
                        returnValueOrDefault<String>(productObject, "name") as String,
                        image, ///////////////////////////////////////
                        returnValueOrDefault<String>(productObject, "playcount") as String,
                        a,
                        returnValueOrDefault<String>(productObject, "url") as String
                    ))
                }
            }
            catch (e: JSONException) {
                Log.e(this.LogTag, "Problem parsing the product JSON results", e)
            }

            return productList
        }

        private fun extractDataFromJson(productJson: String?): ArrayList<Track>? {
            if (TextUtils.isEmpty(productJson)) {
                return null
            }

            val trackList = ArrayList<Track>()
            try {
                val baseJsonResponse = JSONObject(productJson)
                val tracksObj = baseJsonResponse.getJSONObject("tracks")
                val trackArray = tracksObj.getJSONArray("track")
                for (i in 0 until trackArray.length()) {
                    val productObject = trackArray.getJSONObject(i)

                    //artist
                    val artist = returnValueOrDefault<JSONObject>(productObject, "artist") as JSONObject?
                    var a = ""
                    if(artist != null)
                    {


                            a = returnValueOrDefault<String>(artist, "name") as String

                    }

                    //image
                    val images = returnValueOrDefault<JSONArray>(productObject, "image") as JSONArray?
                    var image = ""
                    if (images != null) {
                        val ilist = images.getJSONObject(2)
                        image = returnValueOrDefault<String>(ilist, "#text") as String
                    }


                    trackList.add(Track(
                        returnValueOrDefault<String>(productObject, "mbid") as String,
                        returnValueOrDefault<String>(productObject, "name") as String,
                        image, ///////////////////////////////////////
                        returnValueOrDefault<String>(productObject, "playcount") as String,
                        a,
                        returnValueOrDefault<String>(productObject, "url") as String
                    ))
                }
            }
            catch (e: JSONException) {
                Log.e(this.LogTag, "Problem parsing the product JSON results", e)
            }

            return trackList
        }

        private inline fun <reified T> returnValueOrDefault(json: JSONObject, key: String): Any? {
            when (T::class) {
                String::class -> {
                    return if (json.has(key)) {
                        json.getString(key)
                    } else {
                        ""
                    }
                }
                Int::class -> {
                    return if (json.has(key)) {
                        json.getInt(key)
                    }
                    else {
                        return -1
                    }
                }
                Double::class -> {
                    return if (json.has(key)) {
                        json.getDouble(key)
                    }
                    else {
                        return -1.0
                    }
                }
                Long::class -> {
                    return if (json.has(key)) {
                        json.getLong(key)
                    }
                    else {
                        return (-1).toLong()
                    }
                }
                JSONObject::class -> {
                    return if (json.has(key)) {
                        json.getJSONObject(key)
                    }
                    else {
                        return null
                    }
                }
                JSONArray::class -> {
                    return if (json.has(key)) {
                        json.getJSONArray(key)
                    }
                    else {
                        return null
                    }
                }
                else -> {
                    return null
                }
            }
        }
    }
}