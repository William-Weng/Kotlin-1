package com.example.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// MARK: - RecyclerView.ViewHolder (UITableViewHeader)
class HeaderHolder : RecyclerView.ViewHolder {
    constructor(itemView: View) : super(itemView)
}

// MARK: - RecyclerView.ViewHolder (UITableViewSection)
class SectionHolder : RecyclerView.ViewHolder {

    var titleTextView: TextView

    constructor(itemView: View) : super(itemView) {
        titleTextView = itemView.findViewById(R.id.titleTextView)
    }
}

// MARK: - RecyclerView.ViewHolder (UITableViewCell)
class CellHolder : RecyclerView.ViewHolder {

    var nameTextView: TextView
    var regionTextView: TextView

    constructor(itemView: View) : super(itemView) {
        nameTextView = itemView.findViewById(R.id.nameTextView)
        regionTextView = itemView.findViewById(R.id.regionTextView)
    }
}