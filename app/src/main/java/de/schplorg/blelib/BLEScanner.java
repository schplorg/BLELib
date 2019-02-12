package de.schplorg.blelib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanResult;

import android.content.Context;
import java.util.HashMap;

/**
 * Created by Sam Bäumer on 2/18/18.
 * sam.baeumer@schplorg.de
 */

public class BLEScanner {
    Context c;
    BluetoothManager manager;
    BluetoothAdapter adapter;
    BluetoothLeScanner leScanner;
    HashMap<String,BluetoothDevice> deviceList;

    public BLEScanner(Context c){
        manager = (BluetoothManager)c.getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = manager.getAdapter();
        leScanner = adapter.getBluetoothLeScanner();
        deviceList = new HashMap<String,BluetoothDevice>();
    }
    public boolean isScanning = false;
    public void start(){
        if(!isScanning){
            if(leScanCallback != null){
                if(leScanner != null){
                    leScanner.startScan(leScanCallback);
                    isScanning = true;
                }else{
                    D.println("leScanner null!");
                }
            }else{
                D.println("leScanCallback null!");
            }
        }
    }

    public void stop(){
        if(isScanning){
            if(leScanCallback != null){
                if(leScanner != null){
                    leScanner.stopScan(leScanCallback);
                    isScanning = false;
                }else{
                    D.println("leScanner null!");
                }
            }else{
                D.println("leScanCallback null!");
            }
        }
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


