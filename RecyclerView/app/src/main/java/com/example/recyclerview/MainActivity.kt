// [使用Kotlin實作RecyclerView及RecyclerView的優點](https://tpu.thinkpower.com.tw/tpu/articleDetails/1274)
// [Android 探究onCreateViewHolder和onBindViewHolder兩者關係和調用次數](https://blog.csdn.net/csdn_aiyang/article/details/80094302)
// [RecyclerView動畫源碼淺析](https://juejin.im/post/5c19f8f66fb9a049b347edc5)
// [RecyclerView的基本設計結構](https://juejin.im/post/5c10737de51d457926430f99)
// [Android 探究onCreateViewHolder和onBindViewHolder兩者關係和調用次數](https://blog.csdn.net/csdn_aiyang/article/details/80094302)
// [RecyclerView --onItemClick設置彙總](https://www.jianshu.com/p/040bfbbca49b)
// [用Kotlin實現Android點擊事件](https://www.jianshu.com/p/d057ac23eed0)
// [2.2.1 LinearLayout(線性佈局)](https://www.runoob.com/w3cnote/android-tutorial-linearlayout.html)
// [Kotlin 開發第 6 天 ImageList (RecyclerView)](https://android.devdon.com/archives/113)
// [RecyclerView系列之（1）：為RecyclerView添加Header和Footer](https://www.jianshu.com/p/991062d964cf)
// [如何在Toolbar使用Menu(kotlin)](https://givemepass.blogspot.com/2015/12/toolbarmenu.html)
// [Kotlin Tutorial（2）10分鐘認識Kotlin](https://github.com/macdidi5/Kotlin-Tutorial)
// [話說用 RecyclerView 做一個無限畫廊](https://medium.com/jastzeonic/話說用-recyclerview-做一個無限畫廊-4f847ea98672)
// [ListView的滾動監聽--AbsListView.OnScrollListener的學習](https://www.jianshu.com/p/0b731f38120e)
// [Android - Toolbar事件穿透和背景漸變](https://www.jianshu.com/p/65591a718cdc)
// [Android Toolbar遮擋按鈕（view）點擊事件，怎麼穿透呢](https://blog.csdn.net/Jiang_Rong_Tao/article/details/81563605)
// [Android Kotlin 實作 Day 12：GithubStars（下）（OkHttp）](https://ithelp.ithome.com.tw/articles/10207954)
// [英國研究顯示，連續30天用Kotlin開發Android將有益於身心健康](https://ithelp.ithome.com.tw/users/20111529/ironman/1985)
// [[Android]隱藏及顯示ImageView](https://blog.johnsonlu.org/android隱藏及顯示imageview/)
// [Android中visibility屬性VISIBLE、INVISIBLE、GONE的區別 - 謙信君 - 博客園](https://www.cnblogs.com/kenshinobiy/p/9196639.html)
// [那些 Kotlin 中的靜態事](https://medium.com/@tomazwang/那些-kotlin-中的靜態事-407139a17e5b)
// [Kotlin 開發第 11 天 Alarm ( DatePickerDialog + AlertDialog)](https://android.devdon.com/archives/239)
// [Kotlin 開發第 12 天 GithubStars ( OkHttp + RecyclerView)](https://android.devdon.com/archives/258)
// [Dialog with transparent background in Android](https://stackoverflow.com/questions/10795078/dialog-with-transparent-background-in-android)
// [空安全 - Kotlin 語言中文站](https://www.kotlincn.net/docs/reference/null-safety.html)
// [Android可收缩伸展的Expandable分组RecyclerView](https://blog.csdn.net/zhangphil/article/details/79814332)
// [MakeAppIcon - Generate iOS and Android app icons of all sizes with a click!](https://makeappicon.com/)

package com.example.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.Utility.headerModelData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

// MARK: - 第一頁
class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initIdentity()

        toolbarSetting("我是Toolbar")
        toolbarIconOnClickSetting()

        recyclerViewSetting()
        progressAlertDialogSetting()

        updateWebInformation()
    }

    /// 初始化AlertDialog
    private fun progressAlertDialogSetting() {

        val progressView = layoutInflater.inflate(R.layout.progressbar_dialog, null)
        val builder = AlertDialog.Builder(this, R.style.TransparentAlertDialog)

        builder.setView(progressView)
        builder.setCancelable(false)

        alertDialog = builder.create()
    }

    /// 取得WEB上的資訊 -> 更新
    private fun updateWebInformation() {

        alertDialog.show()

        getUINamesBody(20) { responseBody ->

            responseBody?.let {

                val uiNameModelArray = nameModelArrayMaker(responseBody)

                runOnUiThread {
                    alertDialog.dismiss()
                    recyclerView.adapter = webModelAdapterMaker(uiNameModelArray)
                }
            }
        }
    }

    /// 初始化UI的ID
    private fun initIdentity() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recyclerView)
    }

    /// 設定Toolbar (加在畫面上)
    private fun toolbarSetting(title: String) {

        val backgroundColor = resources.getColor(R.color.colorPrimaryDark, null)
        val titleTextColor = resources.getColor(R.color.colorPrimary, null)

        toolbar.title = title

        toolbar.setBackgroundColor(backgroundColor)
        toolbar.setTitleTextColor(titleTextColor)
        toolbar.setNavigationIcon(R.drawable.ic_add_box_black_24dp)

        setSupportActionBar(toolbar)
    }

    /// 設定ToolbarIcon按下的Listener
    private fun toolbarIconOnClickSetting() {

        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "我是Toolbar Icon", Toast.LENGTH_SHORT).show()
        }
    }

    /// 設定RecyclerView
    private fun recyclerViewSetting() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    /// [Web + 假資料] RecyclerView上的資料 (Header + Sections +Cells)
    private fun webModelAdapterMaker(models: ArrayList<UINameModel>) : ModelAdapter {

        val demoSectionData = Utility.demoSectionData(5)

        var modelArrayList = ArrayList<BaseModel>()

        modelArrayList.add(headerModelData())

        for (sectionData in demoSectionData) {
            modelArrayList.add(sectionData)
            modelArrayList.addAll(models)
        }

        return ModelAdapter(this, modelArrayList)
    }

    /// [GSON] 將 ResponseBody => JSON String => ArrayList<UINameModel>
    private fun nameModelArrayMaker(responseBody: ResponseBody) : ArrayList<UINameModel> {
        val jsonString = responseBody.string()
        val arrayType = object : TypeToken<ArrayList<UINameModel>>(){}.type
        return Gson().fromJson<ArrayList<UINameModel>>(jsonString, arrayType)
    }

    /// 取得API的資料 (非同步)
    private fun getUINamesBody(count: Int, callback: (ResponseBody?) -> Unit) {

        val urlString = "https://uinames.com/api/?amount=$count"
        val request = Request.Builder().get().url(urlString).build()
        val newCall = OkHttpClient().newCall(request)

        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { callback(null) }
            override fun onResponse(call: Call, response: Response) { callback(response.body) }
        })
    }
}
