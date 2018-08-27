package com.zyj.idataplugin;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** IdataPlugin */
public class IdataPlugin implements MethodCallHandler {

  private final Registrar registrar;

  public static MethodChannel channelBarcodeScan;

  public static MethodChannel channelRfid;

  private IdataPlugin(Registrar registrar) {
    this.registrar = registrar;
  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    if (channelBarcodeScan != null) {
      throw new IllegalStateException("You should not call registerWith more than once.");
    }
    channelBarcodeScan = new MethodChannel(registrar.messenger(), "idata_barcode_plugin");
    channelBarcodeScan.setMethodCallHandler(new IdataPlugin(registrar));

    if (channelRfid != null) {
      throw new IllegalStateException("You should not call registerWith more than once.");
    }
    channelRfid = new MethodChannel(registrar.messenger(), "idata_rfid_plugin");
    channelRfid.setMethodCallHandler(new IdataPlugin(registrar));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {

  }
}
