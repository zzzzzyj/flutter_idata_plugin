# idata_plugin

A Flutter plugin for iData 95W(A03_A3Q_V0.29) Devices

## Getting Started
主要技术点在于宿主(Android或iOS)主动发消息到Flutter部分，
官方教程给出了[quick_actions](https://pub.dartlang.org/packages/quick_actions)这个例子，
需要通过plugin来实现。
1. plugin flutter 定义MethodChannel，定义接收执行结果的方法
2. plugin android 注册该MethodChannel
3. example android 引入arr包，使用android特定代码onKeyDown等，使用invokeMethod执行MethodChannel对应方法。
4. example flutter 接收执行结果并展示。

## Plugin
#### idata_plugin/lib/idata_plugin.dart
1. 定义MethodChannel
    ```dart
    static const MethodChannel _channel = const MethodChannel('idata_barcode_plugin');
    ```
2. 设置接收方法，用于接收Android端执行invokeMethod后返回的结果
    ```dart
    typedef void IdataHandler(String type); //用于在主程序中调用initializeBarcodeScan时传入回调函数
    ```
    ```dart
    void initializeBarcodeScan(IdataHandler handler) {
        _channel.setMethodCallHandler((MethodCall call) async {
          assert(call.method == 'zyj.com/idata_barcode_scan'); //断言，如果call.method不为'zyj.com/idata_barcode_scan'则不往下执行
    
          handler(call.arguments); //将接收到的参数传入回调
        });
      }
    ```
#### idata_plugin/android
1. 注册MethodChannel
    ```java
        public static MethodChannel channelBarcodeScan;
        
        private IdataPlugin(Registrar registrar) {
           this.registrar = registrar;
        }
     
        public static void registerWith(Registrar registrar) {
            if (channelBarcodeScan != null) {
             throw new IllegalStateException("You should not call registerWith more than once.");
            }
            channelBarcodeScan = new MethodChannel(registrar.messenger(), "idata_barcode_plugin");
            channelBarcodeScan.setMethodCallHandler(new IdataPlugin(registrar));
        }
    ```

## Example
#### example/android
1. 引入idatalibrary-release.arr包
    >路径为`example/android/app/libs/idatalibrary-release.arr`
    >在`example/android/app/build.gradle`中的`dependencies`添加
    ```
        dependencies {
            ...
            implementation fileTree(include: '*.aar', dir: 'libs')
        }
    ```
2. 在onKeyDown等事件中调用对应的invokeMethod方法
    ```java
       public class MainActivity extends FlutterActivity {
           iDataRFID idataRfid = null;
           iDataBarcodeScan idataBarcodeScan = null;
       
           @Override
           protected void onCreate(Bundle savedInstanceState) {
               ...
       
               idataRfid = new iDataRFID(MainActivity.this);
               idataRfid.openRfid();
       
               idataBarcodeScan = new iDataBarcodeScan(MainActivity.this, new ScanCallbackInterface() {
                   @Override
                   public void success(String scanResult) {
                       IdataPlugin.channelBarcodeScan.invokeMethod("zyj.com/idata_barcode_scan", scanResult);
                   }
       
                   @Override
                   public void fail() {
       
                   }
               });
           }
       
           @Override
           public boolean onKeyDown(int keyCode, KeyEvent event) {
               if (keyCode == 139) {
                   idataBarcodeScan.singleScan();
               }
               if (keyCode == 140 || keyCode == 141) {
                   IdataPlugin.channelRfid.invokeMethod("zyj.com/idata_rfid", idataRfid.readUid());
               }
               return super.onKeyDown(keyCode, event);
           }
       
           @Override
           public boolean onKeyUp(int keyCode, KeyEvent event) {
               if (keyCode == 139 || keyCode == 140 || keyCode == 141) {
                   idataBarcodeScan.scanStop();
               }
               return super.onKeyUp(keyCode, event);
           }
       
           @Override
           protected void onDestroy() {
               super.onDestroy();
               idataBarcodeScan.finishScanner();
           }
       }

    ```
#### example/lib/main.dart
1. 在initState方法中调用idata_plugin的initializeBarcodeScan方法
    ```dart
        String _barcodeScanResult = 'Unknown';   
 
        @override
        void initState() {
            super.initState();
            
            IdataPlugin idataPlugin = new IdataPlugin();
            idataPlugin.initializeBarcodeScan((String result) {
              setState(() {
                _barcodeScanResult = result;
              });
            });
        }
    ```