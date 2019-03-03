package com.example.p2.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.p2.ProductDetailActivity
import com.example.p2.R
import com.example.p2.ProductViewModel
import com.example.p2.model.Track
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_result_list.*
import kotlinx.android.synthetic.main.result_list_item.view.*
import java.text.NumberFormat


@SuppressLint("ValidFragment")
class ResultListFragment(context: Context, query: String): Fragment() {
    private var adapter = ResultAdapter()
    private var parentContext: Context = context
    private lateinit var viewModel: ProductViewModel
    private var listInitialized = false

    private var queryString: String = query
    private var trackList: ArrayList<Track> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        val displayText = "Search for: $queryString"
        (activity as AppCompatActivity).supportActionBar?.title = displayText

        result_items_list.layoutManager = LinearLayoutManager(parentContext)
        result_items_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        val observer = Observer<ArrayList<Track>> {
            result_items_list.adapter = adapter
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
                    return trackList[p0].getUrl() == trackList[p1].getUrl()
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
                    return (trackList[p0] == trackList[p1])
                }
            })
            result.dispatchUpdatesTo(adapter)
            trackList = it ?: ArrayList()
        }

        viewModel.getProductsByQueryText(queryString).observe(this, observer)

        this.listInitialized = true
    }

    inner class ResultAdapter: RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ResultViewHolder {
            val itemView = LayoutInflater.from(p0.context).inflate(R.layout.result_list_item, p0, false)
            return ResultViewHolder(itemView)
        }

        override fun onBindViewHolder(p0: ResultViewHolder, p1: Int) {
            val product = trackList[p1]
            val productImages = product.getImages()

            Picasso.with(this@ResultListFragment.context).load(productImages).into(p0.productImg)

            p0.productTitle.text = product.getName()

            val price = product.getArtist()

            p0.productPrice.text = price

            p0.row.setOnClickListener {
                val intent = Intent(this@ResultListFragment.parentContext, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT", product)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return trackList.size
        }

        inner class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val row = itemView

            var productImg: ImageView = itemView.product_img
            var productTitle: TextView = itemView.product_title
            var productPrice: TextView = itemView.product_price
        }
    }
}