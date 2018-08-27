import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:idata_plugin/idata_plugin.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _barcodeScanResult = 'Unknown';
  String _rfidResult = 'Unknown';

  @override
  void initState() {
    super.initState();

    IdataPlugin idataPlugin = new IdataPlugin();
    idataPlugin.initializeBarcodeScan((String result) {
      setState(() {
        _barcodeScanResult = result;
      });
    });

    idataPlugin.initializeRfid((String result) {
      setState(() {
        _rfidResult = result;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Plugin example app'),
        ),
        body: new Center(
          child: new Column(
            children: <Widget>[
              new Text(
                'Barcode: $_barcodeScanResult',
                style: new TextStyle(fontSize: 24.0),
              ),
              new Text(
                'RFID: $_rfidResult',
                style: new TextStyle(fontSize: 24.0),
              )
            ],
          ),
        ),
      ),
    );
  }
}
