package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

object Utility {

    /// 空白的Header
    fun headerModelData() : HeaderModel {
        return HeaderModel()
    }

    /// 測試用假資料 (Section)
    fun demoSectionData(count: Int) : ArrayList<SectionModel> {

        var modelArray = ArrayList<SectionModel>()

        for (index in 0..count) {

            var model = SectionModel()
            model.title = "標題 - $index"
            modelArray.add(model)
        }

        return modelArray
    }

    /// XML => View
    fun cellViewMaker(cellType: CellType, context: Context, parent: ViewGroup) : View {

        return when(cellType) {
            CellType.Header -> { LayoutInflater.from(context).inflate(R.layout.recyclerview_header, parent, false) }
            CellType.Section -> { LayoutInflater.from(context).inflate(R.layout.recyclerview_section, parent, false) }
            CellType.Cell -> { LayoutInflater.from(context).inflate(R.layout.recyclerview_cell, parent, false) }
        }
    }
}