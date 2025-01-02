package com.roy.myapplication

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*


/**
 * 描述:首页
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //addContentView方式添加FlutterView
        page1.setOnClickListener {
            startActivity(Intent(this@MainActivity,Page1Activity::class.java))
        }

        //普通Fragment方式添加FlutterView
        page2.setOnClickListener {

            startActivity(Intent(this@MainActivity,Page2Activity::class.java))
        }

        //使用FlutterFragmentActivity
        page3.setOnClickListener {
            startActivity(Intent(this@MainActivity,PageFlutterFragmentActivity::class.java))
        }

        //使用FlutterActivity
        page4.setOnClickListener {
            startActivity(Intent(this@MainActivity,PageFlutterActivity::class.java))
        }

        //addContentView方式添加FlutterView并传递参数
        page1Param.setOnClickListener {
            startActivity(Intent(this@MainActivity,Page1ParamActivity::class.java))
        }

        //解决debug模式下黑屏的另一种方式
        noBlack.setOnClickListener {
            startActivity(Intent(this@MainActivity,DebugNoBlackActivity::class.java))

        }

        //进入Flutter页面演示通过Channel跳转到Activity
        jumpByChannel.setOnClickListener {

            startActivity(Intent(this@MainActivity,JumpActivityFlutterWidget::class.java))
        }

        //进入嵌入了Android平台的View的Flutter页面
        insertAndroidView.setOnClickListener {
            startActivity(Intent(this@MainActivity,InsertAndroidViewFlutterWidget::class.java))
        }
    }

}
