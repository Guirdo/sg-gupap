package org.adsoftware.entidades;

public class Evaluacion {
    
    int idEvaluacion, parcial, calificacion, idAlumnoE;

    public Evaluacion(int idEvaluacion, int parcial, int calificacion, int idAlumnoE) {
        this.idEvaluacion = idEvaluacion;
        this.parcial = parcial;
        this.calificacion = calificacion;
        this.idAlumnoE = idAlumnoE;
    }

    public Evaluacion(int parcial, int calificacion, int idAlumnoE) {
        this.parcial = parcial;
        this.calificacion = calificacion;
        this.idAlumnoE = idAlumnoE;
    }
    
    
   
}
