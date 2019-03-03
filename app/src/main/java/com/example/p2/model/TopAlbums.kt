package com.example.p2.model

import java.io.Serializable

class TopAlbums():Serializable{
    private var albums:ArrayList<Album> = ArrayList()

    constructor(
        albums:ArrayList<Album>
    ):this(){
        this.albums = albums
    }

    fun getAlbums():ArrayList<Album>{
        return this.albums
    }

    fun setAlbums(albums:ArrayList<Album>){
        this.albums = albums
    }
}