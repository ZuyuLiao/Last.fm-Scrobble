package com.example.p2.model

import java.io.Serializable

class ImageItem():Serializable{
    private  var url:String = ""
    private var size:String= ""
    constructor(
        url:String,
        size:String
    ):this(){
        this.url = url
        this.size = size
    }

    fun getUrl():String{
        return this.url
    }

    fun setUrl(url:String){
        this.url = url
    }

    fun getSize():String{
        return this.size
    }

    fun setSize(size:String){
        this.size = size
    }
}