package com.example.p2.model

import java.io.Serializable

class TopAlbumsResponse():Serializable{
    private  var topAlbums:TopAlbums = TopAlbums()

    constructor(
        topAlbums: TopAlbums
    ):this(){
        this.topAlbums = topAlbums
    }

    fun getTopAlbums():TopAlbums{
        return this.topAlbums
    }

    fun setTopAlbums(topAlbums: TopAlbums){
        this.topAlbums =topAlbums
    }
}