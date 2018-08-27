package com.zyj.idatapluginexample;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.idatalibrary.ScanCallbackInterface;
import com.example.idatalibrary.iDataBarcodeScan;
import com.example.idatalibrary.iDataRFID;
import com.zyj.idataplugin.IdataPlugin;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    iDataRFID idataRfid = null;
    iDataBarcodeScan idataBarcodeScan = null;

    private static final String TAG = "MainActivity_zyj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);

        idataRfid = new iDataRFID(MainActivity.this);
        idataRfid.openRfid();

        idataBarcodeScan = new iDataBarcodeScan(MainActivity.this, new ScanCallbackInterface() {
            @Override
            public void success(String scanResult) {
                Log.d(TAG, "success: " + scanResult);

                IdataPlugin.channelBarcodeScan.invokeMethod("zyj.com/idata_barcode_scan", scanResult);
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: " + keyCode);
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
