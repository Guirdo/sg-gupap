package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Grupo {

    public static final String INGLES_A1 = "InglesA1";
    public static final String INGLES_A2 = "InglesA2";
    public static final String INGLES_B1 = "InglesB1";
    public static final String INGLES_B2 = "InglesB2";
    public static final String INGLES_C1 = "InglesC1";

    public int idGrupo;
    public String curso;
    public Date fechaInicio;
    public int numEstudiantes;
    public int idHorarioG;

    public Grupo(int idGrupo, String curso, Date fechaInicio, int numEstudiantes, int idHorarioG) {
        this.idGrupo = idGrupo;
        this.curso = curso;
        this.fechaInicio = fechaInicio;
        this.numEstudiantes = numEstudiantes;
        this.idHorarioG = idHorarioG;
    }

    public Grupo(String curso, Date fechaInicio, int idHorarioG) {
        this.curso = curso;
        this.fechaInicio = fechaInicio;
        this.idHorarioG = idHorarioG;
    }

    public static Grupo buscarPrimero(String campo, String valor) throws SQLException {
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from grupo where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Grupo(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getDate(3),
                    InterfazBD.rs.getInt(4),
                    InterfazBD.rs.getInt(5)
            );
        } else {
            return null;
        }
    }

    public static ArrayList<Grupo> todos() throws SQLException {
        ArrayList<Grupo> lista = new ArrayList<>();

        InterfazBD.st = InterfazBD.con.createStatement();
        InterfazBD.rs = InterfazBD.st.executeQuery("select * from grupo");

        while (InterfazBD.rs.next()) {
            lista.add(new Grupo(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getDate(3),
                    InterfazBD.rs.getInt(4),
                    InterfazBD.rs.getInt(5)
            ));
        }

        return lista;
    }

    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into grupo "
                + "(curso,fechaInicio,idHorarioG) values"
                + "(?,?,?)");

        InterfazBD.pst.setString(1, this.curso);
        InterfazBD.pst.setDate(2, this.fechaInicio);
        InterfazBD.pst.setInt(3, this.idHorarioG);

        InterfazBD.pst.executeUpdate();
    }

    public void actualizar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update grupo "
                + " set fechaInicio = ?, curso = ? "
                + "where idGrupo = ?");

        InterfazBD.pst.setDate(1, this.fechaInicio);
        InterfazBD.pst.setString(2, this.curso);
        InterfazBD.pst.setInt(3, this.idGrupo);
        
        InterfazBD.pst.executeUpdate();
    }

    public void eliminar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("delete from grupo "
                + "where idGrupo = ?");

        InterfazBD.pst.setInt(1, this.idGrupo);
        
        InterfazBD.pst.executeUpdate();
    }

}
