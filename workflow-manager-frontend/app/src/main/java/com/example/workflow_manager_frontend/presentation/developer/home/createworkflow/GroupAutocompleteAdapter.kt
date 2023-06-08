package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Group
import kotlin.math.min

class GroupAutocompleteAdapter(
    context: Context,
    private val list: List<Group>
): ArrayAdapter<Group>(context, R.layout.item_add_document,list), Filterable {
    private var filteredList: List<Group> = list

    override fun getCount(): Int {
        return min(filteredList.size, 5)
    }

    override fun getItem(position: Int): Group? {
        return filteredList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_autocomplete_group,
            parent,
            false
        )

        val groupTextView: TextView = view.findViewById(R.id.groupTextView)
        groupTextView.text = filteredList[position].name

        return view
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val newItems: MutableList<Group> = mutableListOf()

                for(item in list) {
                    if(constraint != null) {
                        if (item.name.startsWith(constraint, ignoreCase = true)) {
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
                filteredList = results?.values as List<Group>
                notifyDataSetChanged()
            }

        }
    }
}