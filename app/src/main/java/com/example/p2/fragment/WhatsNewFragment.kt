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
import android.support.v7.widget.GridLayoutManager
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
import kotlinx.android.synthetic.main.fragment_whats_new.*
import kotlinx.android.synthetic.main.whats_new_list_item.view.*
import java.util.*

@SuppressLint("ValidFragment")
class WhatsNewFragment(context: Context): Fragment() {
    private var adapter = NewProductAdapter()
    private var parentContext: Context = context
    private lateinit var viewModel: ProductViewModel

    private var productList: ArrayList<Track> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_whats_new, container, false)
    }

    override fun onStart() {
        super.onStart()
        whats_new_list.layoutManager = GridLayoutManager(parentContext, 2)
        whats_new_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        val observer = Observer<ArrayList<Track>> {
            whats_new_list.adapter = adapter
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
                    return productList[p0].getName() == productList[p1].getName()
                }

                override fun getOldListSize(): Int {
                    return productList.size
                }

                override fun getNewListSize(): Int {
                    if (it == null) {
                        return 0
                    }
                    return it.size
                }

                override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
                    return productList[p0] == productList[p1]
                }
            })
            result.dispatchUpdatesTo(adapter)
            productList = it ?: ArrayList()
        }

        viewModel.getNewProducts().observe(this, observer)
    }

    inner class NewProductAdapter: RecyclerView.Adapter<NewProductAdapter.NewProductViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewProductViewHolder {
            val itemView = LayoutInflater.from(p0.context).inflate(R.layout.whats_new_list_item, p0, false)
            return NewProductViewHolder(itemView)
        }

        override fun onBindViewHolder(p0: NewProductViewHolder, p1: Int) {
            val product = productList[p1]
            val productImages = product.getImages()
                Picasso.with(this@WhatsNewFragment.parentContext).load(productImages).into(p0.productImg)
            p0.productTitle.text = product.getName()

            p0.row.setOnClickListener {
                val intent = Intent(this@WhatsNewFragment.parentContext, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT", product)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return productList.size
        }

        inner class NewProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val row = itemView

            var productImg: ImageView = itemView.product_img
            var productTitle: TextView = itemView.product_title
        }
    }
}