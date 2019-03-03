package com.example.p2.model

import java.io.Serializable

class Album(): Serializable{
    private  var mbid:String = ""
    private  var name:String = ""
    private  var image = ArrayList<ImageItem>()
    private  var playCount:String = ""
    private  var artist:Artist = Artist()
    private  var url:String = ""

    constructor(
        mbid:String,
        name: String,
        image:ArrayList<ImageItem>,
        playCount:String,
        artist:Artist,
        url:String
    ):this(){
        this.mbid = mbid
        this.name = name
        this.image = image
        this.playCount = playCount
        this.artist = artist
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

    fun getImage():ArrayList<ImageItem>{
        return this.image
    }

    fun setImage(image:ArrayList<ImageItem>){
        this.image = image
    }

    fun getPlayCount():String{
        return this.playCount
    }

    fun setPlayCount(playCount: String){
        this.playCount = playCount
    }

    fun getArtist():Artist{
        return this.artist
    }

    fun setArtist(artist: Artist){
        this.artist = artist
    }

    fun getUrl():String{
        return this.url
    }

    fun setUrl(url: String){
        this.url = url
    }

    fun getImageUrl():String{
        if (getImage().size > 0)
        {
            for(img in image)
            {
                if(img.getSize().contentEquals("large"))
                {
                    return img.getUrl()
                }
            }
        }
        return ""
    }
}