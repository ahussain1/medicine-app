package com.example.banglapharmacy.Controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banglapharmacy.Adapters.DrugRecyclerAdapter
import com.example.banglapharmacy.Model.Drug
import com.example.banglapharmacy.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_home.*

class FavoritesFragment : Fragment() {

    lateinit var adapter: DrugRecyclerAdapter
    private val FAVORITES_LIST = "favorites_list"
    private val favoriteList = mutableListOf<Drug>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var preferences = context?.getSharedPreferences(FAVORITES_LIST, Context.MODE_PRIVATE)

        HomeFragment.drugList.forEach {
            val value = preferences?.getString(it.name, "")
            if (value.equals("true")) {
                favoriteList.add(it)
            }
        }

        if (favoriteList.isNotEmpty()) {
            adapter = DrugRecyclerAdapter(context, favoriteList) { drug ->
                val drugIntent = Intent(context, DrugSummaryActivity::class.java)
                drugIntent.putExtra("drug", drug)
                startActivity(drugIntent)
            }

            favoriteListView.adapter = adapter
            val layoutManager = LinearLayoutManager(context)
            favoriteListView.layoutManager = layoutManager
            favoriteListView.setHasFixedSize(true)
            favoriteListView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            emptyListText.visibility = View.INVISIBLE
        } else {
            favoriteListView.visibility = View.INVISIBLE
        }

        showBannerAd();
    }

    fun showBannerAd() {
        MobileAds.initialize(activity) {}
        val adRequest = AdRequest.Builder().build()
        adView2.loadAd(adRequest)
    }
}
