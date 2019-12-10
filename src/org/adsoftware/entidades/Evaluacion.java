package org.adsoftware.entidades;

import java.sql.SQLException;

public class Evaluacion {

    public int idEvaluacion, parcial, idAlumnoE;
    public double calificacion;

    public Evaluacion(int idEvaluacion, int parcial, double calificacion, int idAlumnoE) {
        this.idEvaluacion = idEvaluacion;
        this.parcial = parcial;
        this.calificacion = calificacion;
        this.idAlumnoE = idAlumnoE;
    }

    public Evaluacion(int parcial, double calificacion, int idAlumnoE) {
        this.idEvaluacion = -1;
        this.parcial = parcial;
        this.calificacion = calificacion;
        this.idAlumnoE = idAlumnoE;
    }

    public static Evaluacion buscarPrimero(String campo, String valor, String campo1, String valor1) throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from evaluacion where "
                + campo + " = ? and " + campo1 + " = ? ");

        InterfazBD.pst.setString(1, valor);
        InterfazBD.pst.setString(2, valor1);
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Evaluacion(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getInt(2),
                    InterfazBD.rs.getDouble(3),
                    InterfazBD.rs.getInt(4)
            );
        } else {
            return null;
        }
    }

    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into evaluacion "
                + " (parcial,calificacion,idAlumnoE) "
                + "values (?,?,?)");

        InterfazBD.pst.setInt(1, this.parcial);
        InterfazBD.pst.setDouble(2, this.calificacion);
        InterfazBD.pst.setInt(3, this.idAlumnoE);

        InterfazBD.pst.executeUpdate();
    }

    public void guardar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update evaluacion "
                + " set calificacion = ? "
                + "where idEvaluacion = ?");

        InterfazBD.pst.setDouble(1, this.calificacion);
        InterfazBD.pst.setInt(2, this.idEvaluacion);

        InterfazBD.pst.executeUpdate();
    }

}
