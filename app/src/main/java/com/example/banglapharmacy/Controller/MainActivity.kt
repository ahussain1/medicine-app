package com.example.banglapharmacy.Controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banglapharmacy.Adapters.DrugRecyclerAdapter
import com.example.banglapharmacy.Model.Drug
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import com.example.banglapharmacy.R


class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var adapter: DrugRecyclerAdapter

    companion object {
        val drugList = mutableListOf<Drug>()
    }

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
                    val brandNames = document["brand_names"].toString()
                    val sideEffects = document["side_effects"].toString()
                    val usage = document["usage"].toString()

                    Log.d("Test", "4321" + sideEffects)
                    val drug = Drug(
                        name,
                        description,
                        brandNames,
                        sideEffects,
                        usage
                    )
                    drugList.add(drug)
                }

                adapter = DrugRecyclerAdapter(this, drugList) { drug ->
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

    fun setViewProperties() {
        val layoutManager = LinearLayoutManager(this)
        drugListView.layoutManager = layoutManager
        drugListView.setHasFixedSize(true)
    }
}
