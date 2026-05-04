package com.said.inscripciones.models;

import java.util.List;

public class InscripcionResponse {
    public int id_inscripcion_cab;
    public String fecha;
    public Estudiante ins_estudiante;
    public List<Curso> ins_inscripcion_det;
}
