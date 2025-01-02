import 'dart:io' show Platform;

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

/// 描述:嵌入AndroidView
class InsertAndroidViewPage extends StatefulWidget {
  @override
  _InsertAndroidViewPageState createState() => _InsertAndroidViewPageState();
}

class _InsertAndroidViewPageState extends State<InsertAndroidViewPage> {
  GlobalKey key = GlobalKey();
  var platform =  new MethodChannel('com.roy.flutter.flutter_platform_view.AndroidTextView');

  var data = "";
  @override
  void initState() {
    super.initState();
    platform.setMethodCallHandler((call) => Future(() {
      if (call.method == "methodInsertAndroidViewPageState") {
        print("接收到了Android的主动调用,参数为:${call.arguments}");
        setState(() {
          data = call.arguments;
        });
      }
    }));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("我来演示如何嵌入AndroidView"),bottom: PreferredSize(
            preferredSize: Size.fromHeight(50),
            child: TextButton(
              onPressed: () async {
                var result = await  platform.invokeMethod("setText", {"msg": "ssd"});
                print("接收到了调用Android方法的返回值:$result");
              },
              child: Text("我是Flutter控件,点击发送参数到Android ${data}"),
            ),
          ),
        ),
        body: Platform.isAndroid ? SizedBox(
          width: 500,
          height: 200,
          child: AndroidView(key: key, viewType: 'InsertAndroidView', creationParamsCodec: const StandardMessageCodec()),) : Text("ios和Android实现原理一样"));
  }
}
