package com.example.p2

import android.app.ActionBar
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.support.design.widget.NavigationView
import com.example.p2.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("FAVORITE", "Added favorite but came back here...")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_home)

        // Load Fragment into View


        val adapter = PagerAdapter(this, supportFragmentManager)

        viewpager.adapter = adapter
        frag_placeholder.setupWithViewPager(viewpager)

        supportActionBar?.title = "Home"
        supportActionBar?.subtitle = "Top Tracks"
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        // fetch
        else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {

                    // Load Fragment into View
                    val fm = supportFragmentManager

                    // add
                    val ft = fm.beginTransaction()
                    ft.replace(R.id.frag_placeholder, HomeFragment(this@MainActivity), "HOME_FRAG")
                    ft.commit()

                    supportActionBar?.title = "Home"
                    supportActionBar?.subtitle = "Top Track"

            }
            R.id.nav_about_us -> {
                displayDialog(R.layout.dialog_about_us)
            }
            R.id.nav_privacy_policy -> {
                displayDialog(R.layout.dialog_privacy_policy)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displayDialog(layout: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(layout)

        val window = dialog.window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)

        dialog.findViewById<Button>(R.id.close).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}

