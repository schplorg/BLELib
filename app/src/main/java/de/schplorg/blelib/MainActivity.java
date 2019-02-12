package de.schplorg.blelib;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView outputText;
    private BLEScanner scanner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputText = (TextView) findViewById(R.id.outputText);
        final EditText inputField = (EditText) findViewById(R.id.inputField);

        D.callback = new PrintCallback(){
            @Override
            public void print(String s) {
                String output = outputText.getText().toString();
                outputText.setText(output + s);
            }
        };

        inputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = inputField.getText().toString();
                    handleInput(s);
                    inputField.setText("");
                    handled = true;
                }
                return handled;
            }
        });
    }

    // INPUT
    private void handleInput(String s){
        if(s.equals("clear")){
            outputText.setText("");
        }
        if(s.equals("start")){
            D.println(s);
            if(scanner == null){
                boolean hasPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
                if(hasPermission){
                    scanner = new BLEScanner(this.getApplicationContext());
                    scanner.start();
                }else{
                    D.println("!ACCESS_COARSE_LOCATION");
                }
            }else{
                scanner.start();
            }
        }
        if(s.equals("allow")){
            D.println(s);
            handlePermissions();
        }
        if(s.equals("stop")){
            if(scanner != null){
                D.println(s);
                scanner.stop();
            }
        }
        if(s.equals("log")){
            if(scanner != null){
                D.println(s);
                D.println(scanner.isScanning?"isScanning":"!isScanning");
                D.println(scanner.adapter.isDiscovering()?"isDiscovering":"!isDiscovering");
                D.println(scanner.adapter.isEnabled()?"isEnabled":"!isEnabled");
            }
        }
        if(s.equals("ls")){
            if(scanner != null){
                D.println(s);
                D.println("found:" + scanner.deviceList.size());
                for (String address:scanner.deviceList.keySet()) {
                    D.println(address);
                }
            }
        }
    }

    // PERMISSIONS
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 4;
    private void handlePermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                } else {
                    // permission denied, boo!
                }
                return;
            }
        }
    }
}
