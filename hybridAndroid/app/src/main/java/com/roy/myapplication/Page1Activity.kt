package com.roy.myapplication

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import io.flutter.embedding.android.FlutterFragment


/**
 * 描述:addContentView方式添加FlutterView
 */
class Page1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //下面这个setContentView添加不添加的都没关系
        //如果添加了,在debug模式下FlutterView显示有点慢所以会先显示activity_page1再显示FlutterView
        //而release模式下速度会很快,看不到activity_page1,会直接显示FlutterView
        setContentView(R.layout.activity_page1)
        //Activity嵌入FlutterView的方式1


        //指定路由并且传值
        val flutterFragment = FlutterFragment.withNewEngine()
            .initialRoute("page1")
            .build<FlutterFragment>()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flutterViewViewGroup, flutterFragment)
            .commit()


        //但是如果上面直接使用setContentView就看不到过渡效果了,也就是看不到activity_page1也就看不到TextView显示的文字了
    }
}
