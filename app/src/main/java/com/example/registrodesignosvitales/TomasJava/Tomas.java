package com.example.registrodesignosvitales.TomasJava;

public class Tomas {

    Nodo lista;
    int noToma;
    String signoTomado, fechaDeToma, horaDeToma;
    float nivelDeLaToma;


    //constructor
    public Tomas(){

    }


    //gets
    public int getNumeroToma() {
        return noToma;
    }

    public String getSignoDeToma() {
        return signoTomado;
    }

    public String getFechaDeToma() {
        return fechaDeToma;
    }

    public String getHoraDeToma() {
        return horaDeToma;
    }

    public float getNivel() {
        return nivelDeLaToma;
    }


    //sets
    public void setToma(Nodo lista) {
        this.lista = lista;
    }



    public Nodo insertarTomaFinal(Nodo li, int dato){
        if (li == null){
            Nodo nuevo = new Nodo(dato);
            li = nuevo;
            return li;
        }
        else {
            li.setSiguiente(insertarTomaFinal(li.getSiguiente(), dato));
        }
        return li;
    }

}
