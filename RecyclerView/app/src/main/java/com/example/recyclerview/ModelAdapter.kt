package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// MARK: - 類似UITableView的Delegate / DataSource
class ModelAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var context: Context
    private var modelArray: ArrayList<BaseModel>

    /// 初始化資料 (UITableViewDelegate)
    constructor(context: Context, modelArray: ArrayList<BaseModel>) : super() {
        this.context = context
        this.modelArray = modelArray
    }

    /// cell的數量 (tableView(_:numberOfRowsInSection:))
    override fun getItemCount(): Int {
        return modelArray.count()
    }

    /// cell的長相 (tableView(_:cellForRowAt:))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {

        val viewTypeEnum = CellType.values()[viewType]

        return when(viewTypeEnum) {
            CellType.Header -> {  headerViewMaker(parent) }
            CellType.Section -> { sectionViewMaker(parent) }
            CellType.Cell -> { cellViewMaker(parent) }
        }
    }

    /// cell的設定 (tableView(_:cellForRowAt:) + tableView(_:didSelectRowAt:))
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is HeaderHolder -> { headerHolderSetting(holder, position) }
            is SectionHolder -> { sectionHolderSetting(holder, position) }
            is CellHolder -> { nameHolderSetting(holder, position) }
        }
    }

    /// item的類型 (cell or header)
    override fun getItemViewType(position: Int): Int {
        val cellType = modelArray[position].cellType
        return cellType.value
    }

    /// XML -> HeaderView -> Holder
    private fun headerViewMaker(parent: ViewGroup): HeaderHolder {
        val headerView = LayoutInflater.from(context).inflate(R.layout.recyclerview_header, parent, false)
        return HeaderHolder(headerView)
    }

    /// XML -> SectionView -> Holder
    private fun sectionViewMaker(parent: ViewGroup): SectionHolder {
        val cellView = LayoutInflater.from(context).inflate(R.layout.recyclerview_section, parent, false)
        return SectionHolder(cellView)
    }

    /// XML -> CellView -> Holder
    private fun cellViewMaker(parent: ViewGroup): CellHolder {
        val cellView = LayoutInflater.from(context).inflate(R.layout.recyclerview_cell, parent, false)
        return CellHolder(cellView)
    }

    /// Header的內容細部設定
    private fun headerHolderSetting(holder: HeaderHolder, position: Int) {
        holder.itemView.setBackgroundColor(0x00ff00)
    }

    /// Section的內容細部設定
    private fun sectionHolderSetting(holder: SectionHolder, position: Int) {
        val modelData = modelArray[position] as? SectionModel ?: return
        holder.titleTextView.text = modelData.title
    }

    /// Cell的內容細部設定 [假資料]
    private fun cellHolderSetting(holder: CellHolder, position: Int) {

        val modelData = modelArray[position] as? CellModel ?: return

        holder.nameTextView.text = modelData.name
        holder.regionTextView.text = modelData.region
    }

    /// Cell的內容細部設定 [Web]
    private fun nameHolderSetting(holder: CellHolder, position: Int) {

        val modelData = modelArray[position] as? UINameModel ?: return

        holder.nameTextView.text = modelData.name
        holder.regionTextView.text = modelData.region
    }
}

