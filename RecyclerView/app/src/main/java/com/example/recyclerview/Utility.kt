package com.example.recyclerview

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

    /// 測試用假資料 (Cell)
    fun demoModelData(count: Int) : ArrayList<CellModel> {

        var modelArray = ArrayList<CellModel>()

        for (index in 0..count) {

            var model = CellModel()
            model.name = "William - $index"
            model.region = "Earth - $index"

            modelArray.add(model)
        }

        return modelArray
    }
}