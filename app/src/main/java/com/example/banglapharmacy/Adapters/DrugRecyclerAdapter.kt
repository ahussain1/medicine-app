package com.example.banglapharmacy.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banglapharmacy.Model.Drug
import com.example.banglapharmacy.R


class DrugRecyclerAdapter(val context: Context?, val drugsList: List<Drug>, val itemClick: (Drug) -> Unit) : RecyclerView.Adapter<DrugRecyclerAdapter.Holder>() {

    private var PRIVATE_MODE = 0
    private val FAVORITES_LIST = "favorites_list"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.drug_list_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return drugsList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (context != null) {
            holder.bindCategory(drugsList[position], context)
        }

    }

    inner class Holder(itemView: View, val itemClick: (Drug) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val drugNameField = itemView.findViewById<TextView>(R.id.drugName)
        val drugShortDescField = itemView.findViewById<TextView>(R.id.drugShortDesc)
        val favoritesIcon = itemView.findViewById<Button>(R.id.favoritesIcon)
        var preferences = context?.getSharedPreferences(FAVORITES_LIST, Context.MODE_PRIVATE)

        fun bindCategory(drug: Drug, context: Context) {
            var value = preferences?.getString(drug.name, "")

            if (value.isNullOrBlank() || value.equals("false")) {
                favoritesIcon.setBackgroundResource(R.drawable.empty_heart)
                preferences?.edit()?.putString(drug.name, "false")?.apply()
            } else if (value == "true") {
                favoritesIcon.setBackgroundResource(R.drawable.purple_heart)
            }

            drugNameField.text = drug.name
            drugShortDescField.text = drug.description. substring(0, 90)
            itemView.setOnClickListener {
                itemClick(drug)
            }

            favoritesIcon.setOnClickListener{
                Log.d("tag", "othoytrue")
                if (preferences?.getString(drug.name, "").equals("false")) {
                    Log.d("tag", "othoyfalse")
                    preferences?.edit()?.putString(drug.name, "true")?.apply()
                    favoritesIcon.setBackgroundResource(R.drawable.purple_heart)
                } else {
                    Log.d("tag", "othoyelse")
                    preferences?.edit()?.putString(drug.name, "false")?.apply()
                    favoritesIcon.setBackgroundResource(R.drawable.empty_heart)
                }
            }
        }
    }

}