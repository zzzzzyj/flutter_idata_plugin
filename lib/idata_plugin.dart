import 'dart:async';

import 'package:flutter/services.dart';

typedef void IdataHandler(String type);

class IdataPlugin {
  static const MethodChannel _channel =
      const MethodChannel('idata_barcode_plugin');

  static const MethodChannel _channel_rfid =
      const MethodChannel('idata_rfid_plugin');

  void initializeBarcodeScan(IdataHandler handler) {
    _channel.setMethodCallHandler((MethodCall call) async {
      assert(call.method == 'zyj.com/idata_barcode_scan');

      handler(call.arguments);
    });
  }

  void initializeRfid(IdataHandler handler) {
    _channel_rfid.setMethodCallHandler((MethodCall call) async {
      assert(call.method == 'zyj.com/idata_rfid');

      handler(call.arguments);
    });
  }
}
