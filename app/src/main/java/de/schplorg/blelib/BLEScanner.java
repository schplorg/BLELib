package de.schplorg.blelib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam Bäumer on 2/18/18.
 * sam.baeumer@schplorg.de
 */

public class BLEScanner {
    BluetoothAdapter adapter;
    BluetoothLeScanner leScanner;
    HashMap<String,BluetoothDevice> deviceList;

    public BLEScanner(){
        adapter = BluetoothAdapter.getDefaultAdapter();
        leScanner = adapter.getBluetoothLeScanner();
        deviceList = new HashMap<String,BluetoothDevice>();
    }

    public void start(){
        leScanner.startScan(leScanCallback);
    }

    public void stop(){
        leScanner.stopScan(leScanCallback);
    }

    private android.bluetooth.le.ScanCallback leScanCallback =
            new android.bluetooth.le.ScanCallback() {
                @Override
                public  void onScanResult(int i, ScanResult result){
                    BluetoothDevice device = result.getDevice();
                    String address = device.getAddress();
                    if(!deviceList.containsKey(address)){
                        deviceList.put(address,device);
                    }
                }
            };
}


