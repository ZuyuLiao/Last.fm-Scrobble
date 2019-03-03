package com.example.p2

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.p2.R
import com.example.p2.model.Track
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
class ProductDetailActivity: AppCompatActivity() {
    private lateinit var track: Track
    private lateinit var viewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        track = intent.extras!!.getSerializable("PRODUCT") as Track
        Log.e("FAVORITE", track.isFavorite.toString())

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)


        button.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(track.getUrl()))
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent)
            }
        }

        this.loadUI(track)
    }

    override fun onBackPressed() {
        this.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu, menu)
        if (this.track.isFavorite) {
            menu?.getItem(0)?.icon = getDrawable(R.drawable.ic_star_24dp)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            R.id.action_favorite -> {
                if(track.isFavorite)
                {
                    item.setIcon(R.drawable.ic_star_border_24dp)
                    viewModel.removeFavorite(track.getName(), false)
                    track.isFavorite = false
                }
                else
                {
                    item.setIcon(R.drawable.ic_star_24dp)
                    viewModel.addFavorite(track)
                    track.isFavorite = true
                }

                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUI(product: Track) {
        val Text = product.getArtist()

        track_title.text = "Name: " + product.getName()


        name.text = "Artist: "+ Text

        playcount.text = "PlayCount: " + product.getPlayCount()
        mbid.text = "mbid: " + product.getMbid()
        url.text = "url: " + product.getUrl()


        Picasso.with(this).load(product.getImages()).into(product_img)


    }



}