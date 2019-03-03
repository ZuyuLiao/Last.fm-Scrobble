package com.example.p2.model

import java.io.Serializable

class Artist():Serializable{
    private var mbid:String=""
    private var name:String=""
    private  var url:String=""

    constructor(
        mbid:String,
        name:String,
        url:String
    ):this(){
        this.mbid = mbid
        this.name = name
        this.url = url
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

    fun setName(name: String){
        this.name = name
    }


    fun getUrl():String{
        return this.url
    }

    fun setUrl(url: String){
        this.url
    }
}