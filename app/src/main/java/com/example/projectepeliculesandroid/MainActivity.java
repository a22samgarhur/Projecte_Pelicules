package com.example.projectepeliculesandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public int acertades=0;
    public ArrayList respostes = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray objecte = obj.getJSONArray("preguntes");

            for (int i =0;i<objecte.length();i++){
                JSONObject preguntes = objecte.getJSONObject(i);
                //Log.d("Data:",preguntes.toString()); //Para imprimir todo el contenido del JSON

                String id = preguntes.getString("id");
                String pregunta = preguntes.getString("pregunta");
                Log.d("Numero de pregunta",id);
                Log.d("Pregunta",pregunta);

                JSONArray respuestas=preguntes.getJSONArray("respostes");
                List<String> listarespuestas=new ArrayList<String>();

                for (int j =0;j<respuestas.length();j++){
                    JSONObject respuesta = respuestas.getJSONObject(j);
                    listarespuestas.add(respuesta.getString("resposta"));
                }
                Log.d("Respostes",listarespuestas.toString());

            }
         } catch (JSONException e) {
            e.printStackTrace();
        }


        Button enviar = findViewById(R.id.butoEnviar);
        enviar.setOnClickListener(this);
         }

    //Funcion para leer un JSON de la carpeta de assets
    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = this.getAssets().open("Questionari JSON.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.e("data", json);
        return json;
    }


    public void onClick(View v) {
            respostes=new ArrayList();
            TextView respostafinal = findViewById(R.id.respostafinal);
            RadioGroup grup = findViewById(R.id.grupo);
            RadioGroup grup2 = findViewById(R.id.grupo2);

            int id=grup.getCheckedRadioButtonId();
            RadioButton radio = findViewById(id);

            int id2=grup2.getCheckedRadioButtonId();
            RadioButton radio2 = findViewById(id2);

            if (respostafinal.getVisibility() == v.INVISIBLE) {
                respostafinal.setVisibility(v.VISIBLE);
                respostes.add(radio.getText());
                respostes.add(radio2.getText());
                respostafinal.setText("Respostes"+respostes);

            } else {
                respostafinal.setVisibility(v.INVISIBLE);
                ;
            }
        }
    public void onRadioButtonClicked(View view) {
        TextView resp = findViewById(R.id.respuesta);
        TextView contador = findViewById(R.id.contador);


        contador.setText("Total acertades: "+acertades);

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    resp.setText("Respuesta correcta");
                    resp.setTextColor(Color.GREEN);
                    resp.setVisibility(View.VISIBLE);
                    acertades++;
                    //RadioButton radioButton1=findViewById(R.id.radioButton1);
                    //respostes.add(radioButton1.getText());
                break;
            case R.id.radioButton2:
            case R.id.radioButton3:
            case R.id.radioButton4:
                if (checked)
                    resp.setText("Respuesta incorrecta");
                    resp.setTextColor(Color.RED);
                    resp.setVisibility(View.VISIBLE);
                break;

        }
    }

    }

