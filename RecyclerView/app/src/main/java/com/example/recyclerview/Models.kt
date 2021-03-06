package com.example.recyclerview

import com.google.gson.annotations.SerializedName

// MARK: - Cell的類型 (Header / Cell)
enum class CellType(val value: Int) {
    Header(0),
    Cell(1),
    Section(2),
}

// MARK: - 基本的Model長相
interface BaseModel {
    val cellType: CellType
}

// MARK: - HeaderModel (內容的Model)
class HeaderModel : BaseModel {

    override val cellType: CellType
        get() = CellType.Header
}

// MARK: - SectionModel (內容的Model)
class SectionModel : BaseModel {

    override val cellType: CellType
        get() = CellType.Section

    var title: String? = null
}

// MARK: - UINameModel (內容的Model)
class UINameModel : BaseModel {

    override val cellType: CellType
        get() { return CellType.Cell }

    @SerializedName("name")
    var name: String? = null

    @SerializedName("region")
    var region: String? = null
}