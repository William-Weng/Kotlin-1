// [使用Kotlin實作RecyclerView及RecyclerView的優點](https://tpu.thinkpower.com.tw/tpu/articleDetails/1274)
// [Android 探究onCreateViewHolder和onBindViewHolder兩者關係和調用次數](https://blog.csdn.net/csdn_aiyang/article/details/80094302)
// [RecyclerView動畫源碼淺析](https://juejin.im/post/5c19f8f66fb9a049b347edc5)
// [RecyclerView的基本設計結構](https://juejin.im/post/5c10737de51d457926430f99)
// [Android 探究onCreateViewHolder和onBindViewHolder兩者關係和調用次數](https://blog.csdn.net/csdn_aiyang/article/details/80094302)
// [RecyclerView --onItemClick設置彙總](https://www.jianshu.com/p/040bfbbca49b)
// [用Kotlin實現Android點擊事件](https://www.jianshu.com/p/d057ac23eed0)
// [2.2.1 LinearLayout(線性佈局)](https://www.runoob.com/w3cnote/android-tutorial-linearlayout.html)
// [Kotlin 開發第 6 天 ImageList (RecyclerView)](https://android.devdon.com/archives/113)

package com.example.recyclerview_demo

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*

class MainActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataModelArray: ArrayList<DataModel>
    private lateinit var dataAdapter: DataAdaper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        recyclerViewSetting(dataAdapter)
    }

    /// 初始資料設定
    private fun initData() {
        dataModelArray = dataModelArrayMaker(1000)
        dataAdapter = DataAdaper(this, dataModelArray)
    }

    /// RecyclerView設定
    private fun recyclerViewSetting(dataAdapter: DataAdaper) {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dataAdapter
    }

    /// 假資料
    private fun dataModelArrayMaker(count: Int): ArrayList<DataModel> {

        var dataArray: ArrayList<DataModel> = ArrayList()

        for (index in 1..count) {
            dataArray.add(DataModel("第 $index 筆", index))
        }

        return dataArray
    }
}

// MARK: - RecyclerView.Adapter (UITableViewDataSource, UITableViewDelegate)
class DataAdaper: RecyclerView.Adapter<MyViewHolder> {

    private var context: Context
    private var dataModelArray: ArrayList<DataModel>

    /// 初始化資料 (UITableViewDelegate)
    constructor(context: Context, dataModelArray: ArrayList<DataModel>) : super() {
        this.context = context
        this.dataModelArray = dataModelArray
    }

    /// item的數量 (tableView(_:numberOfRowsInSection:))
    override fun getItemCount(): Int {
        return dataModelArray.count()
    }

    /// item的長相 (tableView(_:cellForRowAt:))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cell = LayoutInflater.from(context).inflate(R.layout.recyclerview_cell, parent, false)
        return MyViewHolder(cell)
    }

    /// item的設定 (數據 / 顏色 / 點擊) (tableView(_:cellForRowAt:) + tableView(_:didSelectRowAt:))
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val dataModel = dataModelArray[position]
        var color = if(position % 2 == 0) Color.parseColor("#ffffff") else Color.parseColor("#ddeedd")

        holder.nameTextView.text = dataModel.name
        holder.noteTextView.text = dataModel.index.toString()
        holder.nameTextView.setBackgroundColor(color)
        holder.noteTextView.setBackgroundColor(color)

        holder.itemView.setOnClickListener(cellOnClickListenerMaker(position, dataModel))
    }

    /// OnClickListener的程式功能設定
    private fun cellOnClickListenerMaker(position: Int, dataModel: DataModel): View.OnClickListener {

        var clickListener = View.OnClickListener {

            dataModelArray.removeAt(position)
            notifyDataSetChanged()

            Toast.makeText(context, "${dataModel.name} 刪除了", Toast.LENGTH_SHORT).show()
        }

        return clickListener
    }
}

// MARK: - RecyclerView.ViewHolder (UITableViewCell)
class MyViewHolder : RecyclerView.ViewHolder {

    var nameTextView: TextView
    var noteTextView: TextView

    constructor(itemView: View) : super(itemView) {
        nameTextView = itemView.findViewById(R.id.nameTextView)
        noteTextView = itemView.findViewById(R.id.noteTextView)
    }
}

// MARK: - Cell的資料Model
class DataModel(val name: String, val index: Int)



