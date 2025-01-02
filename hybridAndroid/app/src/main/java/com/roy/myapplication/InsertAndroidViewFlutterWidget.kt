package com.roy.myapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.Registrar
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory


/**
 * 描述:这个页面给Flutter提供AndroidView
 */
class InsertAndroidViewFlutterWidget : FlutterActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        intent.action = "android.intent.action.RUN"
        intent.putExtra("route", "page5")   //配置跳转到哪个页面
        super.onCreate(savedInstanceState)
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        flutterEngine.plugins.add(MyPluginRegistrant())
    }


    class ProvideAndroidView(binaryMessenger: BinaryMessenger) :
        PlatformViewFactory(StandardMessageCodec.INSTANCE), MethodChannel.MethodCallHandler {
        var methodChannel: MethodChannel?=null
        var tv_content:TextView?=null
        init {
            methodChannel = MethodChannel(binaryMessenger,"com.roy.flutter.flutter_platform_view.AndroidTextView")
            methodChannel?.setMethodCallHandler(this)
        }

        override fun create(context: Context, i: Int, data: Any?): PlatformView {
            return object : PlatformView {
                override fun getView(): View {
                    val view= View.inflate(context, R.layout.view_insert_to_flutter, null)
                    tv_content =   view.findViewById<TextView>(R.id.tv_content)
                    return view
                }

                override fun dispose() {
                    methodChannel?.setMethodCallHandler(null)
                }

            }
        }

        override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
            if(call.method.equals("setText")){
                var msg = call.argument<String>("msg")
                tv_content?.setText(msg)
                tv_content?.invalidate()
                Toast.makeText(tv_content?.context,"收到了flutter的消息：$msg",Toast.LENGTH_LONG).show()
                result.success("哈哈哈哈")
                methodChannel?.invokeMethod("methodInsertAndroidViewPageState","123453332$msg")
            }
        }

    }


    class MyPluginRegistrant : FlutterPlugin {
        override fun onAttachedToEngine(binding: FlutterPluginBinding) {
            val messenger = binding.binaryMessenger
            binding
                .platformViewRegistry
                .registerViewFactory(
                    "InsertAndroidView", ProvideAndroidView(messenger)
                )
        }

        override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        }

        companion object {
            fun registerWith(registrar: Registrar) {
                registrar
                    .platformViewRegistry()
                    .registerViewFactory(
                        "InsertAndroidView",
                        ProvideAndroidView(registrar.messenger())
                    )
            }
        }
    }


}