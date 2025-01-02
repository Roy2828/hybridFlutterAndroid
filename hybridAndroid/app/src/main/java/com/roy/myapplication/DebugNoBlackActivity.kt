package com.roy.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterFragment


/**
 * 描述:在debug模式下,直接跳转到使用FlutterView的Activity的时候会有黑屏,
 * 如果想处理这种情况可以直接调用flutterView.enableTransparentBackground(),参考Page1Activity
 * 这里提供另一种解决方式
 *
 * 但是没什么必要,因为release模式下是没有这种问题的
 *
 * 这里要注意隐藏显示window.decorView是不可以的,必须隐藏显示R.layout.xxx里将要包含FlutterView的ViewGroup
 *
 */
class DebugNoBlackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //先调用setContentView设置一个layout
        setContentView(R.layout.activity_debug_no_black)
        //Activity嵌入FlutterView的方式1

     /*   val flutterView = Flutter.createView(
            this,
            lifecycle,
            "page1"
        )*/



        //1.取出setContentView里将要添加FlutterView的ViewGroup

        val root = findViewById<ViewGroup>(R.id.root)
        val tipTextView = findViewById<View>(R.id.tipTextView)
        //2.添加FlutterView


        //指定路由并且传值
        val flutterFragment = FlutterFragment.withNewEngine()
            .initialRoute("page1")
            .build<FlutterFragment>()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flutterViewViewGroup, flutterFragment)
            .commit()


        //3.在xml或者调用代码隐藏包含FlutterView的ViewGroup,因为我在xml隐藏了所以下面的方法就不需要调用了
        //flutterViewViewGroup.visibility = View.INVISIBLE
        //4.当FlutterView绘制了第一帧以后再显示,就解决了debug模式下的黑屏问题
      /*  flutterView.addFirstFrameListener {
            //删除过渡TextView
            root.removeView(tipTextView)
            flutterViewViewGroup.visibility = View.VISIBLE
        }*/

    }
}
