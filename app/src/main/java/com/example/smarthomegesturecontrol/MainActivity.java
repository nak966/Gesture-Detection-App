package com.example.smarthomegesturecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner gestDdownSpin;

    private String selectedGesture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestDdownSpin = (Spinner) findViewById(R.id.gestureDropdownSpinner);
        gestDdownSpin.setOnItemSelectedListener(this);

        String[] spnAry = new String[] { "Select Gesture", "LightOn", "LightOff", "FanOn","FanOff","FanUp","FanDown","SetThermo","Num0","Num1","Num2","Num3","Num4","Num5","Num6","Num7","Num8","Num9"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spnAry);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gestDdownSpin.setAdapter(adapter);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        selectedGesture = parent.getSelectedItem().toString();
        Log.d("onItemSelected", selectedGesture);

        // Condition to see if an Item is actually Selected yet..
        if (!selectedGesture.equals("Select Gesture")) {
            Intent intent = new Intent(MainActivity.this, ExampleVidActivity.class);
            Bundle b = new Bundle();
            b.putString("Gesture", selectedGesture);
            intent.putExtras(b);
            startActivity(intent);
        }
        // check to see if New ITem is Selected? or maybe not? maybe allow mutliple submissions.
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Nothing Selected..
    }
}