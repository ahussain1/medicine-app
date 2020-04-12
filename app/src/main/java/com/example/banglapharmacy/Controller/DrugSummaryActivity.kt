package com.example.banglapharmacy.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.HtmlCompat
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
        description.setHtmlText(drug!!.description)
        howToUse.setHtmlText(drug!!.howToUse)
        usage.text = drug?.usage
        brandName.setHtmlText(drug!!.brandNames)

    }

    fun TextView.setHtmlText(source: String) {
        this.text = HtmlCompat.fromHtml(source, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
