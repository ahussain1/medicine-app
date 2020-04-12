package com.example.banglapharmacy.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.banglapharmacy.Model.Drug
import com.example.banglapharmacy.R
import org.w3c.dom.Text

class DrugRecyclerAdapter(val context: Context, val drugsList: List<Drug>, val itemClick: (Drug) -> Unit) : RecyclerView.Adapter<DrugRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.drug_list_item, parent, false)
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return drugsList.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindCategory(drugsList[position], context)
    }

    inner class Holder(itemView: View, val itemClick: (Drug) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val drugNameField = itemView.findViewById<TextView>(R.id.drugName)
        val drugShortDescField = itemView.findViewById<TextView>(R.id.drugShortDesc)

        fun bindCategory(drug: Drug, context: Context) {
            drugNameField.text = drug.name
            drugShortDescField.text = drug.description. substring(0, 90)
            itemView.setOnClickListener {
                itemClick(drug)
            }
        }
    }

}