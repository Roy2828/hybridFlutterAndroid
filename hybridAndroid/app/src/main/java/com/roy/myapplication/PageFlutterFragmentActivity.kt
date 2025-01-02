package com.roy.myapplication

import android.os.Bundle
import android.util.Log
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin

import io.flutter.plugin.common.MethodChannel

import org.json.JSONObject


class PageFlutterFragmentActivity : FlutterFragmentActivity() {

    private lateinit var methodChannel: MethodChannel
    val channelName = "channelName_PageFlutterFragmentActivity"
    val androidMethod = "methodName_PageFlutterFragmentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        //intent的参数设置必须在super.onCreate之前,因为super.onCreate里会取这些参数
        intent.action = "android.intent.action.RUN"

        val jsonObject = JSONObject()
        jsonObject.put("path", "InvokeMethodPage")
        jsonObject.put("title", "PageFlutterFragmentActivity")
        jsonObject.put("channelName", channelName)
        jsonObject.put("androidMethod", androidMethod)
        intent.putExtra("route", jsonObject.toString())
        super.onCreate(savedInstanceState)
        //调用super.onCreate(savedInstanceState)之后flutterView才有值,
        //所以如果需要注册插件,则应该放到super.onCreate(savedInstanceState)代码之后才可以
    //    flutterView.enableTransparentBackground()
        //如果不需要平台交互的话,只需要上面的代码并在最后加上super.onCreate就可以了
        //这里this.registrarFor方法实际调用的是FlutterFragmentActivity里的delegate的registrarFor方法,
        //而delegate的registrarFor方法实际调用的是FlutterActivityDelegate里的flutterView.getPluginRegistry().registrarFor方法,
        //而FlutterActivityDelegate里的flutterView在调用了这里的super.onCreate(savedInstanceState)才有值,
        //所以如果需要注册插件,则应该放到super.onCreate(savedInstanceState)代码之后才可以
        val key = "PageFlutterFragmentActivity"


    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        flutterEngine.getPlugins().add(object :FlutterPlugin{
            override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
                methodChannel = MethodChannel(
                    binding.binaryMessenger,
                    channelName
                )
                methodChannel.setMethodCallHandler { methodCall, result ->
                    if (methodCall.method == androidMethod) {
                        Log.e("Android", "接收到了Flutter传递的参数:${methodCall.arguments}")
                        result.success("$androidMethod ok")
                        Log.e("Android", "主动调用Flutter的methodInvokeMethodPageState方法")
                        methodChannel.invokeMethod("methodInvokeMethodPageState", "Android发送给Flutter的参数")
                    }
                }
            }

            override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {

            }

        })

    }

}