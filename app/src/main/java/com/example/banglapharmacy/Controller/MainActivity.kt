package com.example.banglapharmacy.Controller

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.banglapharmacy.R
import com.google.android.material.navigation.NavigationView.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.net.URI

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var homeFragment: HomeFragment
    lateinit var favoritesFragment: FavoritesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        actionBar?.title = "Pharmacy Dictionary"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar, R.string.open, R.string.close
        ) {

        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment, "Home")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                if ( supportFragmentManager.findFragmentByTag("Home")!!.isVisible) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment, "Home")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            R.id.favorites -> {
                val current = supportFragmentManager.findFragmentByTag("Favorites")
                if (current != null && current.isVisible) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return true
                }
                    favoritesFragment = FavoritesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, favoritesFragment, "Favorites")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit()
                }
            R.id.review -> {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.arabyprof.app"))
                startActivity(i)
            }
            R.id.share -> {
                val shareBody = "You are the body"
                val shareSub = "You are the subject"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                intent.putExtra(Intent.EXTRA_TEXT, shareSub)
                startActivity(Intent.createChooser(intent, "Share  your app"))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
