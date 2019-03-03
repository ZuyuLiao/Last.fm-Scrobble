package com.example.p2.model

import java.io.Serializable

class TrackDetail():Serializable{
    private var albumName:String =""
    private var wiki:String=""

    constructor(
        albumname:String,
        wiki:String
    ):this(){
        this.albumName = albumname
        this.wiki = wiki
    }

    fun getAlbumName():String{
        return this.albumName
    }

    fun getWiki():String{
        return this.wiki
    }
}