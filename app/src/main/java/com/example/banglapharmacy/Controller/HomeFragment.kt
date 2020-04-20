package com.example.banglapharmacy.Controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.banglapharmacy.Adapters.DrugRecyclerAdapter
import com.example.banglapharmacy.Model.Drug
import android.util.Log
import com.example.banglapharmacy.R
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.view.*
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.Source
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.log

class HomeFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    lateinit var adapter: DrugRecyclerAdapter

    companion object {
        val drugList = mutableListOf<Drug>()
        val filteredList = mutableListOf<Drug>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setRetainInstance(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewProperties()
        setupRecyclerView()
        MobileAds.initialize(activity) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun setupRecyclerView() {
        if(drugList.isEmpty()) {
            db.collection("Drugs").get().addOnSuccessListener { result ->
                if (drugList.isEmpty()) {
                    for (document in result) {
                        val name = document.id
                        val description = document["description"].toString()
                        val howToUse = document["how_to_use"].toString()
                        val sideEffects = document["side_effects"].toString()
                        val precaution = document["precaution"].toString()
                        val notes = document["notes"].toString()
                        val storage = document["storage"].toString()

                        val usage = document["usage"].toString()

                        Log.d("Test", "4321" + sideEffects)
                        val drug =
                            Drug(name, description, howToUse, sideEffects, precaution, notes, storage)
                        drugList.add(drug)
                    }
                    filteredList.addAll(drugList)
                }

                adapter = DrugRecyclerAdapter(context, filteredList) { drug ->
                    val drugIntent = Intent(context, DrugSummaryActivity::class.java)
                    drugIntent.putExtra("drug", drug)
                    startActivity(drugIntent)
                }

                drugListView.adapter = adapter
            }
                .addOnFailureListener { exception ->
                    Log.d("MyMessage", "Error getting documents: ", exception)
                }
        } else {
            adapter = DrugRecyclerAdapter(context, filteredList) { drug ->
                val drugIntent = Intent(context, DrugSummaryActivity::class.java)
                drugIntent.putExtra("drug", drug)
                startActivity(drugIntent)
            }
            drugListView.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            searchView.setOnQueryTextFocusChangeListener { _ , hasFocus ->
                if (hasFocus) {
                    Log.d("MyMessage", "testlab123")
                } else {
                    searchView.setQuery("", false)
                    filteredList.clear()
                    filteredList.addAll(drugList)
                    drugListView.adapter!!.notifyDataSetChanged()
                }
            }

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase()
                        filteredList.clear()

                        drugList.forEach{
                            if(it.name.toLowerCase().contains(search)) {
                                filteredList.add(it)
                            }
                        }

                        drugListView.adapter!!.notifyDataSetChanged()
                    } else {
                        filteredList.clear()
                        filteredList.addAll(drugList)
                        drugListView.adapter!!.notifyDataSetChanged()
                    }
                    return false
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        filteredList.clear()
        filteredList.addAll(drugList)
        drugListView.adapter!!.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        filteredList.clear()
        filteredList.addAll(drugList)
        drugListView.adapter!!.notifyDataSetChanged()
    }

    fun setViewProperties() {
        val layoutManager = LinearLayoutManager(context)
        drugListView.layoutManager = layoutManager
        drugListView.setHasFixedSize(true)
        drugListView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}
