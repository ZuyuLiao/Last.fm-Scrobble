package com.example.p2.model

import java.io.Serializable
import java.time.Duration

class Track():Serializable{
    private  var mbid:String =""
    private  var name:String=""
    private  var images:String=""
    private  var playCount:String=""
    private  var artist:String = ""
    private  var url:String = ""
    private var album:String = ""
    private var content:String = ""
    var isFavorite: Boolean = false

    constructor(
        mbid:String,
        name:String,
        images:String,
        playCount:String,
        artist: String,
        url:String
    ):this(){
        this.mbid = mbid
        this.name = name
        this.images = images
        this.playCount = playCount
        this.artist = artist
        this.url = url
    }

    constructor(
        mbid:String,
        name:String,
        images:String,
        playCount:String,
        artist: String,
        url:String,
        album:String,
        content:String
    ):this(){
        this.mbid = mbid
        this.name = name
        this.images = images
        this.playCount = playCount
        this.artist = artist
        this.url = url
        this.album = album
        this.content = content
    }

    fun getAlbum():String{
        return this.album
    }

    fun getContent():String{
        return this.content
    }

    fun getMbid():String{
        return this.mbid
    }

    fun setMbid(mbid: String){
        this.mbid = mbid
    }

    fun getName():String{
        return this.name
    }

    fun setNmae(name: String){
        this.name = name
    }

    fun getImages():String{
        return this.images
    }

    fun setImages(images: String){
        this.images = images
    }

    fun getPlayCount():String{
        return this.playCount
    }

    fun setPlayCount(playCount:String){
        this.playCount = playCount
    }

    fun getArtist():String{
        return this.artist
    }

    fun setArtist(artist: String){
        this.artist = artist
    }

    fun getUrl():String{
        return this.url
    }

    fun setUrl(url: String){
        this.url = url
    }



}
