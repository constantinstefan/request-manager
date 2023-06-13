package com.example.workflow_manager_frontend.presentation.developer.groups.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Customer
import kotlin.math.min

class MemberAutocompleteAdapter(context : Context,
                                private val list : List<Customer>)
    : ArrayAdapter<Customer>(context, R.layout.item_autocomplete_member) {
    private var filteredList: List<Customer> = list

    override fun getCount(): Int {
        return min(filteredList.size, 5)
    }

    override fun getItem(position: Int): Customer? {
        return filteredList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_autocomplete_member,
            parent,
            false
        )

        val email: TextView = view.findViewById(R.id.memberTextView)
        email.text = filteredList[position].email

        return view
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val newItems: MutableList<Customer> = mutableListOf()

                for(item in list) {
                    if(constraint != null) {
                        if (item.email.startsWith(constraint, ignoreCase = true)) {
                            newItems.add(item)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = newItems
                filterResults.count = newItems.size
                return  filterResults
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Customer>
                notifyDataSetChanged()
            }

        }
    }
}