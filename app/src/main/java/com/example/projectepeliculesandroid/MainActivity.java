package com.example.projectepeliculesandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class MainActivity extends AppCompatActivity  {
    private static final String BASE_URL = "http://10.0.2.2:3000";

    public int acertades = 0;
    public ArrayList respostes = new ArrayList();
    private CountDownTimer countDownTimer;
    private TextView temporizador;
    private final long tiempoTemporizador = 30000; // 30 segundos
    private final long intervaloTemporizador = 1000; // 1 segundo
    List<RadioGroup> listaRadioGroup = new ArrayList<>();
    List<Preguntas> listaObjetoPregunta = new ArrayList<>();
    List<String> contestadasCorrectas = new ArrayList<>();
    List<String> contestadasIncorrectas = new ArrayList<>();
    Button enviar;
    int duracion = Toast.LENGTH_SHORT;
    int contadorCorrectas = 0;
    int contadorIncorrectas = 0;
    Boolean todasContestadas = false;
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Llamamos a la vista de linearleyaout para incluirle ahi los botones
        LinearLayout linear = findViewById(R.id.linear);
        temporizador = findViewById(R.id.textViewtemporizador);//Inicializamos el Text view del temporizador
        temporizador.setTextColor(Color.RED);
        iniciarTemporizador();//Llamamos a la funcion para que inicie el temporizador

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia de la interfaz de la API
        preguntesAPI preguntesAPI = retrofit.create(preguntesAPI.class);


        // Realizar la solicitud a la API
        Call<preguntasResponse> call = preguntesAPI.getPreguntes();
        Log.e("Prueba1","Antes de entrar al enqueue");

        call.enqueue(new Callback<preguntasResponse>() {

            @Override
            public void onResponse(Call<preguntasResponse> call, Response<preguntasResponse> response) {
                Log.e("Prueba1","dentro del on response");
                if (response.isSuccessful()) {
                    preguntasResponse preguntasResponse = response.body();
                    if (preguntasResponse != null) {
                            generarPreguntas(preguntasResponse);
                    }

                }
            }

            @Override
            public void onFailure(Call<preguntasResponse> call, Throwable t) {
                // Manejar errores de la solicitud
            }
        });





        /*try {
            //Creamos un objeto JSON y lo cargamos
            JSONObject obj = new JSONObject(cargarJSONDeAsset());
            JSONArray objecte = obj.getJSONArray("preguntes");

            for (int i = 0; i < objecte.length(); i++) {
                JSONObject preguntes = objecte.getJSONObject(i);
                //Log.d("Preguntas del archivo:",preguntes.toString()); //Para imprimir todo el contenido del JSON

                String id = preguntes.getString("id");
                String pregunta = preguntes.getString("pregunta");
                String url = preguntes.getString("imatge");
                //Log.d("Numero de pregunta", "Numero"+id);//Para ver el id de las preguntas
                //Log.d("Pregunta", pregunta);//Para ver el nombre de las preguntas


                JSONArray respuestas = preguntes.getJSONArray("respostes");
                List<String> listarespuestas = new ArrayList<String>();
                HashMap<String, Boolean> conjuntorespuestas = new HashMap<String, Boolean>();

                for (int j = 0; j < respuestas.length(); j++) {
                    JSONObject respuesta = respuestas.getJSONObject(j);
                    conjuntorespuestas.put(respuesta.getString("resposta"), respuesta.getBoolean("correcta"));
                    listarespuestas.add(respuesta.getString("resposta"));
                }
                //Log.d("Respostes", listarespuestas.toString());//Para ver las respuestas
                Pregunta objetoPregunta = new Pregunta(id, pregunta, conjuntorespuestas, listarespuestas, url);
                generarPreguntasJSON(objetoPregunta);
                listaObjetoPregunta.add(objetoPregunta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        enviar = new Button(this);
        enviar.setText("Enviar respuestas");
        linear.addView(enviar);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mensaje = "";
                int contadorPreguntes = 0;
                String valor = "0";
                boolean respondidas = true;

                for (int i = 0; i < listaRadioGroup.size(); i++) {

                    int id = listaRadioGroup.get(i).getCheckedRadioButtonId();//Cogemos la ID del boton pulsado

                    //Hacemos el condicional de si hay un boton pulsado
                    if (id == -1) {
                        respondidas = false;
                    }
                }


                if(respondidas){

                for (int i = 0; i < listaRadioGroup.size(); i++) {
                    int id = listaRadioGroup.get(i).getCheckedRadioButtonId();//Cogemos la ID del boton pulsado
                    mensaje = "Todas las preguntas han sido contestadas";//Cambiamos el mensaje
                    todasContestadas = true;
                    enviar.setEnabled(false);//Se desactivara el boton al cliclar
                    countDownTimer.cancel();//Se parara el temporizador al cliclar
                    RadioButton button = findViewById(id);//Creamos un boton i le ponemos el valor de la id que hemos extraido del Radio group
                    valor = button.getText().toString();//Aqui cogemos el valor que contiene el boton
                    for(int j = 0; j < listaObjetoPregunta.get(i).getRespostes().size(); j++) {
                    if (listaObjetoPregunta.get(i).getRespostes().get(j).getResposta().equals(valor)) {
                        if(listaObjetoPregunta.get(i).getRespostes().get(j).isCorrecta()) {
                            contadorCorrectas++;//Si es true sumamos 1 a la lista del contador de correctas
                            //Log.e("contador correctas",""+contadorCorrectas);
                            contestadasCorrectas.add("Pregunta "+(i+1)+",Respuesta: "+valor);//Añadimos la respuesta que hemos contestado correctamente a la lista de correctas

                        }else {
                            contadorIncorrectas++;//Si es false sumamos 1 a la lista del contador de falsas
                            //Log.e("contador incorrectas",""+contadorIncorrectas);
                            contestadasIncorrectas.add("Pregunta "+(i+1)+",Respuesta: "+valor);//Añadimos la respuesta que hemos contestado correctamente a la lista de incorrectas

                        }
                    }
                }}}
                else
                    mensaje = "Faltan preguntas por contestar";

                Log.e("Respuestas correctas",""+contestadasCorrectas);
                Log.e("Respuestas incorrectas",""+contestadasIncorrectas);




                //Creamos Toast para que cree un mensaje emergente
                Toast toast = Toast.makeText(getApplicationContext(), mensaje, duracion);
                toast.show();//Usamos show para que muestre el mensaje
            }
        });
    }

    private void iniciarTemporizador() {
        countDownTimer = new CountDownTimer(tiempoTemporizador, intervaloTemporizador) {

            @Override
            public void onTick(long millisUntilFinished) {
                // Este método se llama en cada intervalo (cada segundo en este caso)
                long segundosRestantes = millisUntilFinished / 1000;
                temporizador.setText("TIEMPO RESTANTE: " + segundosRestantes + " segundos");
            }

            @Override
            public void onFinish() {
                // Acciones a realizar cuando el temporizador llega a cero
                String mensaje = "¡Tiempo agotado!";
                enviar.setEnabled(false);
                Toast toast = Toast.makeText(getApplicationContext(), mensaje, duracion);
                toast.show();
            }
        };

        // Iniciar el temporizador
        countDownTimer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Detener el temporizador para evitar fugas de memoria
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    //Procedimiento para crear las preguntas segun los datos del objeto preguntasResponse
    private void generarPreguntas(preguntasResponse preguntasResponse) {
        //Creamos un bucle para iterar en la lista de preguntas del objetos que nos viene del server
        for (int i = 0; i < preguntasResponse.getPreguntes().size(); i++) {
        //Llamamos a la vista de linearleyaout para incluirle ahi los botones
        LinearLayout linear = findViewById(R.id.linear);

        //Creamos el text view para el titulo de las preguntas
        TextView titolPregunta = new TextView(this);
        titolPregunta.setTextSize(20);
        titolPregunta.setTextColor(Color.BLACK);
        titolPregunta.setText(i+1+"."+preguntasResponse.getPreguntes().get(i).getPregunta());//Le pasamos el titulo de la pregunta
        linear.addView(titolPregunta);//La añadimos a la vista para que la muestre

        //Creamos un Radiogrup para meter ahi los Radiobuttons
        RadioGroup grupo = new RadioGroup(this);
        linear.addView(grupo);//Añadimos el grupo de botones a la vista para que la muestre

        //Creamos una array de Radiobutton
        RadioButton[] botons = new RadioButton[4];

        //Hacemos un bucle para ir iterando en la lista de respuestas
        for (int j = 0; j < preguntasResponse.getPreguntes().get(i).getRespostes().size(); j++) {
            botons[j] = new RadioButton(this);//Creamos un Radiobuton con la posicion de la j
            botons[j].setText(preguntasResponse.getPreguntes().get(i).getRespostes().get(j).getResposta());//Añadimos el contenido de respuestas al Radiobuton
            grupo.addView(botons[j]);//Añadimos el Radiobuton al Radiogrup
        }
        //Añadimos los radiogroups a una lista para poder usar los datos despues
        listaRadioGroup.add(grupo);

        //Añadimos las preguntas completas a una lista para poder usar los datos despues
        listaObjetoPregunta.add(preguntasResponse.getPreguntes().get(i));
    }


    }


    /*    //Procedimiento para crear las preguntas segun los datos del archivo JSON
    private void generarPreguntasJSON(Pregunta pregunta) {

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
            botons[i] = new RadioButton(this);//Creamos un Radiobuton con la posicion de la i
            botons[i].setText(pregunta.llistaRespostas.get(i));//Añadimos el contenido de respuestas al Radiobuton
            grupo.addView(botons[i]);//Añadimos el Radiobuton al Radiogrup
        }
        listaRadioGroup.add(grupo);
    }*/


    /*//Funcion para leer un JSON de la carpeta de assets
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
        //Log.e("data", json);
        return json;
    }*/


    }





