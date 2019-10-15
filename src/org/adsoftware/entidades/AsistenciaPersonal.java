package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AsistenciaPersonal {

    public int idAsistenciaP;
    public Date fecha;
    public Time hora;
    public String tipo;
    public int idPersonlaA;

    public AsistenciaPersonal(int idAsistenciaP, Date fecha, Time hora, String tipo, int idPersonlaA) {
        this.idAsistenciaP = idAsistenciaP;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.idPersonlaA = idPersonlaA;
    }

    public AsistenciaPersonal(Date fecha, Time hora, String tipo, int idPersonlaA) {
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.idPersonlaA = idPersonlaA;
    }
    
    public static ArrayList<AsistenciaPersonal> todos(String campo, String valor) throws SQLException {
        ArrayList<AsistenciaPersonal> lista = new ArrayList<>();

        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from asistenciaPersonal where " + campo + " = ?");
        InterfazBD.pst.setString(1, valor);
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        while (InterfazBD.rs.next()) {
            lista.add(new AsistenciaPersonal(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getDate(2),
                    InterfazBD.rs.getTime(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getInt(5)
            ));
        }

        return lista;
    }
    
}
