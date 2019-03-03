package com.example.p2.model

import java.io.Serializable

class TopTracks():Serializable{
    private var tracks:ArrayList<Track> = ArrayList()

    constructor(
        tracks:ArrayList<Track>
    ):this(){
        this.tracks = tracks
    }

    fun getArtists():ArrayList<Track>{
        return this.tracks
    }

    fun setArtists(tracks:ArrayList<Track>){
        this.tracks = tracks
    }

}