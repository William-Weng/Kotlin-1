package com.example.recyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// MARK: - 類似UITableView的Delegate / DataSource
class ModelAdapter: RecyclerView.Adapter<BaseViewHolder> {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BaseViewHolder {

        val viewTypeEnum = CellType.values()[viewType]

        return when(viewTypeEnum) {
            CellType.Header -> { HeaderHolder(Utility.cellViewMaker(CellType.Header, context, parent)) }
            CellType.Section -> { SectionHolder(Utility.cellViewMaker(CellType.Section, context, parent)) }
            CellType.Cell -> { CellHolder(Utility.cellViewMaker(CellType.Cell, context, parent)) }
        }
    }

    /// cell的設定 (tableView(_:cellForRowAt:) + tableView(_:didSelectRowAt:))
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        when(holder) {
            is HeaderHolder -> { holder.config(0x00ff00) }
            is SectionHolder -> { val modelData = modelArray[position] as? SectionModel; holder.config(modelData) }
            is CellHolder -> { val modelData = modelArray[position] as? UINameModel; holder.config(modelData) }
        }
    }

    /// item的類型 (cell or header)
    override fun getItemViewType(position: Int): Int {
        return modelArray[position].cellType.value
    }
}

