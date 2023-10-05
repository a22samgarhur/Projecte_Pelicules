package com.example.projectepeliculesandroid;

import java.util.List;

public class preguntasResponse {
    private List<Preguntas>preguntas;

    public List<Preguntas> getPreguntes() {
        return preguntas;
    }

    public void setPreguntes(List<Preguntas> preguntes) {
        this.preguntas = preguntes;
    }
}
class Preguntas {
    private int id;
    private String pregunta;
    private List<Respostes>respostes;
    private String imatge;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<Respostes> getRespostes() {
        return respostes;
    }

    public void setRespostes(List<Respostes> respostes) {
        this.respostes = respostes;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }



}

class Respostes{

    private int id;
    private String resposta;
    private boolean correcta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }


}
