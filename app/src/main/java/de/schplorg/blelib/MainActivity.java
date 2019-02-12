package de.schplorg.blelib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView outputText = (TextView) findViewById(R.id.outputText);
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
                    D.print(inputField.getText().toString());
                    inputField.setText("");
                    handled = true;
                }
                return handled;
            }
        });
    }
}
