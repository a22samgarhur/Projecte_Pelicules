package com.example.projectepeliculesandroid;

import java.util.HashMap;
import java.util.List;

public class Pregunta {
    String id;
    String nom;
    HashMap<String,Boolean> respostas;
    List<String>llistaRespostas;
    String url;

    public Pregunta(String id, String nom, HashMap<String,Boolean> respostas,List<String>llistaRespostas, String url) {
        this.id = id;
        this.nom = nom;
        this.respostas = respostas;
        this.llistaRespostas=llistaRespostas;
        this.url = url;
    }

    public HashMap<String, Boolean> getRespostas() {
        return respostas;
    }

    public void setRespostas(HashMap<String, Boolean> respostas) {
        this.respostas = respostas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
