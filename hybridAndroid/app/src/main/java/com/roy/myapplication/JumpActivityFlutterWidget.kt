package com.roy.myapplication

import android.content.Intent
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin

import io.flutter.plugin.common.MethodChannel


/**
 * 描述:这个页面包含FlutterView,然后点击FlutterView中的按钮跳转到另一个Activity,
 * 这种方式其实只是调用Channel通信而已
 */
class JumpActivityFlutterWidget : FlutterActivity() {

    private lateinit var methodChannel: MethodChannel

    override fun onCreate(savedInstanceState: Bundle?) {

        //intent的参数设置必须在super.onCreate之前,因为super.onCreate里会取这些参数
        intent.action = "android.intent.action.RUN"
        intent.putExtra("route", "page4")
        super.onCreate(savedInstanceState)

        //调用super.onCreate(savedInstanceState)之后flutterView才有值,
        //所以如果需要注册插件,则应该放到super.onCreate(savedInstanceState)代码之后才可以
       // flutterView.enableTransparentBackground()
    }




    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        flutterEngine.plugins.add(object :FlutterPlugin{
            override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
                //Flutter跳转Activity的方式1,使用Channel
                methodChannel = MethodChannel(
                       binding.binaryMessenger,
                    "MainActivityMethodChannel"
                )
                methodChannel.setMethodCallHandler { methodCall, result ->
                    if (methodCall.method == "jumpTestActivity") {
                        startActivity(Intent(this@JumpActivityFlutterWidget, TestActivity::class.java))
                    }
                }
            }

            override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {

            }


        })
    }
}