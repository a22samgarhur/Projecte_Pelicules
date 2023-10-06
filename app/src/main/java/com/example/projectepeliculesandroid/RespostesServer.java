package com.example.projectepeliculesandroid;

import java.util.List;

public class RespostesServer {
    public List<RespostaSelecionada> getRespostes() {
        return respostes;
    }

    public RespostesServer(List<RespostaSelecionada> respostes) {
        this.respostes = respostes;
    }

    public void setRespostes(List<RespostaSelecionada> respostes) {
        this.respostes = respostes;
    }

    private List<RespostaSelecionada> respostes;
}
class RespostaSelecionada {
    private int numPregunta;
    private String resposta;

    public RespostaSelecionada(int numPregunta, String resposta, boolean correcta) {
        this.numPregunta = numPregunta;
        this.resposta = resposta;
        this.correcta = correcta;
    }

    private boolean correcta;

    public int getNumPregunta() {
        return numPregunta;
    }

    public void setNumPregunta(int numPregunta) {
        this.numPregunta = numPregunta;
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
