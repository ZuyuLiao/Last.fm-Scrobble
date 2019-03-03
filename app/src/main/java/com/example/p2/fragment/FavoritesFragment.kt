package com.example.p2.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.p2.R
import com.example.p2.ProductDetailActivity
import com.example.p2.ProductViewModel
import com.example.p2.model.Track
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_list_item.view.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import java.text.NumberFormat
import java.util.ArrayList

@SuppressLint("ValidFragment")
class FavoritesFragment(context: Context): Fragment() {
    private var adapter = FavoritesAdapter()
    private var parentContext: Context = context
    private lateinit var viewModel: ProductViewModel

    private var trackList: ArrayList<Track> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onStart() {
        super.onStart()
        favorites_list.layoutManager = LinearLayoutManager(parentContext)
        favorites_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        val observer = Observer<ArrayList<Track>> {
            favorites_list.adapter = adapter
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
                    if(p0 < trackList.size && p1 < trackList.size) {
                        return trackList[p0].getUrl() == trackList[p1].getUrl()
                    }
                    else
                        return false
                }

                override fun getOldListSize(): Int {
                    return trackList.size
                }

                override fun getNewListSize(): Int {
                    if (it == null) {
                        return 0
                    }
                    return it.size
                }

                override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
                    return trackList[p0] == trackList[p1]
                }
            })
            result.dispatchUpdatesTo(adapter)
            trackList = it ?: ArrayList()
        }

        viewModel.getFavorites().observe(this, observer)
    }


    inner class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoriteViewHolder {
            val itemView = LayoutInflater.from(p0.context).inflate(R.layout.favorite_list_item, p0, false)
            return FavoriteViewHolder(itemView)
        }

        override fun onBindViewHolder(p0: FavoriteViewHolder, p1: Int) {
            val product = trackList[p1]
            val productImages = product.getImages()

            Picasso.with(this@FavoritesFragment.parentContext).load(productImages).into(p0.productImg)

            p0.productTitle.text = product.getName()

            val price = product.getArtist()

            p0.productPrice.text = price


            p0.row.setOnClickListener {
                val intent = Intent(this@FavoritesFragment.parentContext, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT", product)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return trackList.size
        }

        inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var row = itemView

            var productImg: ImageView = itemView.product_img
            var productTitle: TextView = itemView.product_title
            var productPrice: TextView = itemView.product_price
        }
    }
}