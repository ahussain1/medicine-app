package com.example.banglapharmacy.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banglapharmacy.R
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        db.collection("Animals")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("Test123"+document.data)
                }
            }
            .addOnFailureListener { exception ->
                println("ERROR")
            }

    }
}
