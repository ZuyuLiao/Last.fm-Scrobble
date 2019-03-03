package com.example.p2.model

import java.io.Serializable

class TopArtists():Serializable{
    private var artists:ArrayList<Artist> = ArrayList()
    constructor(
        artists: ArrayList<Artist>
    ):this(){
        this.artists = artists
    }

    fun getArtists():ArrayList<Artist>{
        return this.artists
    }

    fun setArtists(artists: ArrayList<Artist>){
        this.artists = artists
    }
}