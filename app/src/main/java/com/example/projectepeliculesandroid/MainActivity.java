package com.example.projectepeliculesandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public int acertades = 0;
    public ArrayList respostes = new ArrayList();
    List<RadioGroup> listaRadioGroup = new ArrayList<>();
    List<Pregunta> listaObjetoPregunta = new ArrayList<>();
    int duracion = Toast.LENGTH_SHORT;
    int contadorCorrectas=0;
    int contadorIncorrectas=0;
    ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Llamamos a la vista de linearleyaout para incluirle ahi los botones
        LinearLayout linear = findViewById(R.id.linear);
        try {
            JSONObject obj = new JSONObject(cargarJSONDeAsset());
            JSONArray objecte = obj.getJSONArray("preguntes");

            for (int i = 0; i < objecte.length(); i++) {
                JSONObject preguntes = objecte.getJSONObject(i);
                //Log.d("Data:",preguntes.toString()); //Para imprimir todo el contenido del JSON

                String id = preguntes.getString("id");
                String pregunta = preguntes.getString("pregunta");
                String url = preguntes.getString("imatge");
                //Log.d("Numero de pregunta", "Numero"+id);//Para ver el id de las preguntas
                //Log.d("Pregunta", pregunta);//Para ver el nombre de las preguntas


                JSONArray respuestas = preguntes.getJSONArray("respostes");
                List<String> listarespuestas = new ArrayList<String>();
                HashMap<String,Boolean>conjuntorespuestas=new HashMap<String, Boolean>();

                for (int j = 0; j < respuestas.length(); j++) {
                    JSONObject respuesta = respuestas.getJSONObject(j);
                    conjuntorespuestas.put(respuesta.getString("resposta"),respuesta.getBoolean("correcta"));
                    listarespuestas.add(respuesta.getString("resposta"));
                }
                //Log.d("Respostes", listarespuestas.toString());//Para ver las respuestas
                Pregunta objetoPregunta = new Pregunta(id,pregunta,conjuntorespuestas,listarespuestas,url);
                generarPreguntas(objetoPregunta);
                listaObjetoPregunta.add(objetoPregunta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button enviar = new Button(this);
        enviar.setText("Enviar respostes");
        linear.addView(enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mensaje="";
                Boolean todasContestadas=false;
                int contadorPreguntes=0;
                for (int i = 0; i < listaRadioGroup.size(); i++) {

                    String valor="0";
                    int id =listaRadioGroup.get(i).getCheckedRadioButtonId();

                    if (id != -1) {
                        todasContestadas=true;
                        contadorPreguntes++;
                        RadioButton button = findViewById(id);
                        valor= button.getText().toString();
                    }
                    else
                        todasContestadas=false;


                    if(listaObjetoPregunta.get(i).respostas.get(valor)==true){
                        Log.d("Valor boton",listaObjetoPregunta.get(i).respostas.get(valor).toString());
                        contadorCorrectas++;
                    }
                    else
                        contadorIncorrectas++;


                }

                if(contadorPreguntes==listaRadioGroup.size()){
                    mensaje="Todas las preguntas han sido contestadas";


                }
                else
                    mensaje="Faltan preguntas por contestar";

                //Creamos Toast para que cree un mensaje emergente
                Toast toast = Toast.makeText(getApplicationContext(), mensaje+"Respostas correctas: "+contadorCorrectas+"\n Respostas incorrectas: "+contadorIncorrectas, duracion);
                toast.show();//Usamos show para que muestre el mensaje
            }
        });
    }

    //Procedimiento para crear las preguntas segun los datos del JSON
    private void generarPreguntas(Pregunta pregunta){

        //Llamamos a la vista de linearleyaout para incluirle ahi los botones
        LinearLayout linear = findViewById(R.id.linear);

        //Creamos el text view para el titulo de las preguntas
        TextView titolPregunta = new TextView(this);
        titolPregunta.setTextSize(20);
        titolPregunta.setTextColor(Color.BLACK);
        titolPregunta.setText(pregunta.nom);//Le pasamos el titulo de la pregunta
        linear.addView(titolPregunta);//La añadimos a la vista para que la muestre

        //Creamos un Radiogrup para meter ahi los Radiobuttons
        RadioGroup grupo = new RadioGroup(this);
        linear.addView(grupo);//Añadimos el grupo de botones a la vista para que la muestre

        //Creamos una array de Radiobutton
        RadioButton[] botons = new RadioButton[4];

        //Hacemos un bucle para ir iterando en la lista de respuestas
        for (int i = 0; i < pregunta.respostas.size(); i++) {
            botons[i]= new RadioButton(this);//Creamos un Radiobuton con la posicion de la i
            botons[i].setText(pregunta.llistaRespostas.get(i));//Añadimos el contenido de respuestas al Radiobuton
            grupo.addView(botons[i]);//Añadimos el Radiobuton al Radiogrup
        }
        listaRadioGroup.add(grupo);
    }


    //Funcion para leer un JSON de la carpeta de assets
    private String cargarJSONDeAsset() {
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




        /*respostes = new ArrayList();
        TextView respostafinal = findViewById(R.id.respostafinal);
        RadioGroup grup = findViewById(R.id.grupo);
        RadioGroup grup2 = findViewById(R.id.grupo2);

        int id = grup.getCheckedRadioButtonId();
        RadioButton radio = findViewById(id);

        int id2 = grup2.getCheckedRadioButtonId();
        RadioButton radio2 = findViewById(id2);

        if (respostafinal.getVisibility() == v.INVISIBLE) {
            respostafinal.setVisibility(v.VISIBLE);
            respostes.add(radio.getText());
            respostes.add(radio2.getText());
            respostafinal.setText("Respostes" + respostes);

        } else {
            respostafinal.setVisibility(v.INVISIBLE);
            ;
        }*/
    }

    public void onRadioButtonClicked(View view) {
        /*TextView resp = findViewById(R.id.respuesta);
        TextView contador = findViewById(R.id.contador);


        contador.setText("Total acertades: " + acertades);

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

        }*/
    }

}

