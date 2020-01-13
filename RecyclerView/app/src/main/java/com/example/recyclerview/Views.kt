package com.example.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// MARK: - RecyclerView.ViewHolder (UITableViewHeader)
open class BaseViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: View) : super(itemView)
}

class HeaderHolder : BaseViewHolder {

    constructor(itemView: View) : super(itemView)

    /// 設定畫面
    fun config(color: Int) {
        itemView.setBackgroundColor(color)
    }
}

// MARK: - RecyclerView.ViewHolder (UITableViewSection)
class SectionHolder : BaseViewHolder {

    lateinit var titleTextView: TextView

    constructor(itemView: View) : super(itemView) { initViews() }

    /// 設定畫面
    fun config(model: SectionModel?) {
        titleTextView.text = model?.title
    }

    /// findViewById()
    private fun initViews() {
        titleTextView = itemView.findViewById(R.id.titleTextView)
    }
}

// MARK: - RecyclerView.ViewHolder (UITableViewCell)
class CellHolder : BaseViewHolder {

    lateinit var nameTextView: TextView
    lateinit var regionTextView: TextView

    constructor(itemView: View) : super(itemView) { initViews() }

    /// 設定畫面
    fun config(model: UINameModel?) {
        nameTextView.text = model?.name
        regionTextView.text = model?.region
    }

    /// findViewById()
    private fun initViews() {
        nameTextView = itemView.findViewById(R.id.nameTextView)
        regionTextView = itemView.findViewById(R.id.regionTextView)
    }
}