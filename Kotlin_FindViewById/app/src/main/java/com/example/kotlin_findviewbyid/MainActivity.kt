package com.example.kotlin_findviewbyid

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.View
import android.widget.Button
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var myButtonForID: Button? = null
    private var myEditTextForID: EditText? = null

    private lateinit var myButtoForLateInit: Button
    private lateinit var myEditTextForLateInit: EditText

    private val myButtonForLazy by bindView<Button>(R.id.myButton)
    private val myEditTextForLazy by bindView<Button>(R.id.myEditText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElements()
        initElementsForLateInit()
        initElementsForExtensions()
    }

    /// ① 利用findViewById()初始化元件
    private fun initElements() {
        myButtonForID = findViewById<Button>(R.id.myButton)
        myEditTextForID = findViewById<EditText>(R.id.myEditText)
    }

    /// ② 利用lateinit延遲初始化元件
    private fun initElementsForLateInit() {
        myButtoForLateInit = findViewById<Button>(R.id.myButton)
        myEditTextForLateInit = findViewById<EditText>(R.id.myEditText)
    }

    /// ③ 利用lazy延遲初始化元件
    private fun <T : View> Activity.bindView(@IdRes res: Int): Lazy<T> {
        return lazy { findViewById<T>(res) }
    }

    /// ④ 直接利用Kotlin Android Extensions來直接以id的名字來設定
    private fun initElementsForExtensions() {
        myButton.text = "@+id/myButton"
        myEditText.setText("@+id/myEditText")
    }
}
