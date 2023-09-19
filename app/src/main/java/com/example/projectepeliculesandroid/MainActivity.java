package com.example.projectepeliculesandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup grup = findViewById(R.id.grupo);
        RadioButton correcto = findViewById(R.id.radioButton1);
        RadioButton falso = findViewById(R.id.radioButton2);

        TextView resp = findViewById(R.id.respuesta);



        /*public void onRadioButtonClicked(View view) {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();
            // Check which radio button was clicked.
            switch (view.getId()) {
                case R.id.radioButton1:
                    if (checked)
                        resp.setText("Respuesta correcta");
                        resp.setTextColor(Color.GREEN);
                        resp.setVisibility(View.VISIBLE);
                    break;
                case R.id.radioButton2,R.id.radioButton3,R.id.radioButton4:
                    if (checked)
                        resp.setText("Respuesta incorrecta");
                        resp.setTextColor(Color.RED);
                        resp.setVisibility(View.VISIBLE);
                    break;

                    // Do nothing.
                    break;
            }
        }*/


        /*correcto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correcto.isChecked()) {
                    resp.setText("Respuesta correcta");
                    resp.setTextColor(Color.GREEN);
                    resp.setVisibility(View.VISIBLE);
                }


            }

        });
        falso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (falso.isChecked()) {
                    resp.setText("Respuesta incorrecta");
                    resp.setTextColor(Color.RED);
                    resp.setVisibility(View.VISIBLE);

                }
            }


        });*/
    }

}