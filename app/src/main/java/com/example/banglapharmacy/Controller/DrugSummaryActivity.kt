package com.example.banglapharmacy.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.banglapharmacy.Model.Drug
import com.example.banglapharmacy.R
import kotlinx.android.synthetic.main.activity_drug_summary.*

class DrugSummaryActivity : AppCompatActivity() {

    val minFontSize = 16
    val maxFontSize = 26


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_summary)
        populateFields()

        smallerFont.setOnClickListener({
            description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            howToUse.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            sideEffects.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            precaution.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            notes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            storage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        })

        biggerFont.setOnClickListener({
            description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            howToUse.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            sideEffects.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            precaution.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            notes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            storage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        })
    }



    fun populateFields() {
        val drug = getIntent().getSerializableExtra("drug") as? Drug

        drugName.text = drug?.name
        description.setHtmlText(drug!!.description)
        howToUse.setHtmlText(drug!!.howToUse)
        sideEffects.setHtmlText(drug!!.sideEffects)
        precaution.setHtmlText(drug!!.precaution)
        storage.setHtmlText(drug!!.storage)

        notes.setHtmlText(drug!!.notes)

        if (drug.notes.equals("null")){
            notes.text = "Blank"
        } else {
            notes.setHtmlText(drug!!.notes)
        }
    }



    fun TextView.setHtmlText(source: String) {
        this.text = HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
