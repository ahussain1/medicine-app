package com.example.banglapharmacy.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banglapharmacy.Adapters.DrugRecyclerAdapter
import com.example.banglapharmacy.Model.Drug
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import com.example.banglapharmacy.R
import java.util.*


class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var adapter: DrugRecyclerAdapter

    companion object {
        val drugList = mutableListOf<Drug>()
    }

    val filteredList = mutableListOf<Drug>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllData()
        setViewProperties()
    }

    fun getAllData() {
        if(drugList.isEmpty()) {
            db.collection("Drugs").get().addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.id
                    val description = document["description"].toString()
                    val howToUse = document["how_to_use"].toString()
                    val brandNames = document["brand_names"].toString()
                    val sideEffects = document["side_effects"].toString()
                    val usage = document["usage"].toString()

                    Log.d("Test", "4321" + sideEffects)
                    val drug = Drug(
                        name,
                        howToUse,
                        description,
                        brandNames,
                        sideEffects,
                        usage
                    )
                    drugList.add(drug)
                }

                filteredList.addAll(drugList)

                adapter = DrugRecyclerAdapter(this, filteredList) { drug ->
                    val drugIntent = Intent(this, DrugSummaryActivity::class.java)
                    drugIntent.putExtra("drug", drug)
                    startActivity(drugIntent)
                }
                drugListView.adapter = adapter

            }
                .addOnFailureListener { exception ->
                    Log.d("MyMessage", "Error getting documents: ", exception)
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if (menuItem != null) {
            filteredList.clear()
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
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
                    return true
                }

            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    fun setViewProperties() {
        val layoutManager = LinearLayoutManager(this)
        drugListView.layoutManager = layoutManager
        drugListView.setHasFixedSize(true)
    }
}
