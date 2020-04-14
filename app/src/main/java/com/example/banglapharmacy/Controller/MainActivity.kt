package com.example.banglapharmacy.Controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banglapharmacy.Adapters.DrugRecyclerAdapter
import com.example.banglapharmacy.Model.Drug
import com.example.banglapharmacy.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*



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
                    val sideEffects = document["side_effects"].toString()
                    val precaution = document["precaution"].toString()
                    val notes = document["notes"].toString()
                    val storage = document["storage"].toString()

                    val usage = document["usage"].toString()

                    Log.d("Test", "4321" + sideEffects)
                    val drug = Drug(name, description, howToUse, sideEffects, precaution, notes, storage)
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
        drugListView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
