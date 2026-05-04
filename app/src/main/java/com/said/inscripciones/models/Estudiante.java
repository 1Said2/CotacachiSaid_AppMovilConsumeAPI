package com.said.inscripciones.models;

public class Estudiante {
    public String cedula;
    public String apellidos;
    public String nombres;

    @Override
    public String toString() {
        return nombres + " " + apellidos ;
    }
}
