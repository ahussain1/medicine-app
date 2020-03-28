package com.example.banglapharmacy.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banglapharmacy.Model.Drug
import com.example.banglapharmacy.R
import kotlinx.android.synthetic.main.activity_drug_summary.*

class DrugSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_summary)
        populateFields()
    }

    fun populateFields() {
        val drug = getIntent().getSerializableExtra("drug") as? Drug

        drugName.text = drug?.name
        description.text = drug?.description
        sideEffects.text = drug?.sideEffects
        usage.text = drug?.usage
        brandName.text = drug?.brandNames
    }
}
