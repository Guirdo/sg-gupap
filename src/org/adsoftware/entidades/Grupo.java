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
    public String diasSemana;
    public String horario;
    public String curso;
    public Date fechaInicio;
    public int numEstudiantes;
    public int idPersonalG;

    public Grupo(int idGrupo, String diasSemana, String horario, String curso, Date fechaInicio, int numEstudiantes, int idPersonalG) {
        this.idGrupo = idGrupo;
        this.diasSemana = diasSemana;
        this.horario = horario;
        this.curso = curso;
        this.fechaInicio = fechaInicio;
        this.numEstudiantes = numEstudiantes;
        this.idPersonalG = idPersonalG;
    }

    public Grupo(String diasSemana, String horario, String curso, Date fechaInicio, int idPersonalG) {
        this.diasSemana = diasSemana;
        this.horario = horario;
        this.curso = curso;
        this.fechaInicio = fechaInicio;
        this.idPersonalG = idPersonalG;
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
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getInt(6),
                    InterfazBD.rs.getInt(7)
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
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getInt(6),
                    InterfazBD.rs.getInt(7)
            ));
        }

        return lista;
    }
    
    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into grupo "
                + "(diasSemana,horario,curso,fechaInicio,idPersonalG) values"
                + "(?,?,?,?,?)");

        InterfazBD.pst.setString(1, this.diasSemana);
        InterfazBD.pst.setString(2, this.horario);
        InterfazBD.pst.setString(3, this.curso);
        InterfazBD.pst.setDate(4, this.fechaInicio);
        InterfazBD.pst.setInt(5, this.idPersonalG);

        InterfazBD.pst.executeUpdate();
    }

}
