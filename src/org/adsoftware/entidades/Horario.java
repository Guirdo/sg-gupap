package org.adsoftware.entidades;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class Horario {

    public int idHorario;
    public int idPersonalH;
    public Time horaInicial;
    public Time horaFinal;
    public boolean lunes, martes, miercoles, jueves, viernes, sabado, domingo;

    public Horario(int idHorario, int idPersonalH, Time horaInicial, Time horaFinal, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado, boolean domingo) {
        this.idHorario = idHorario;
        this.idPersonalH = idPersonalH;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    public Horario(int idPersonalH, Time horaInicial, Time horaFinal, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado, boolean domingo) {
        this.idPersonalH = idPersonalH;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    public static Horario buscarUltimo() throws SQLException {
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.st = InterfazBD.con.createStatement();
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.st.executeQuery("select * from horario order by idHorario desc");

        if (InterfazBD.rs.first()) {
            return new Horario(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getInt(2),
                    InterfazBD.rs.getTime(3),
                    InterfazBD.rs.getTime(4),
                    InterfazBD.rs.getBoolean(5),
                    InterfazBD.rs.getBoolean(6),
                    InterfazBD.rs.getBoolean(7),
                    InterfazBD.rs.getBoolean(8),
                    InterfazBD.rs.getBoolean(9),
                    InterfazBD.rs.getBoolean(10),
                    InterfazBD.rs.getBoolean(11)
            );
        } else {
            return null;
        }
    }
    
    public static Horario buscarPrimero(String campo, int id) throws SQLException {
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from Horario where "+campo+" = ?");
        InterfazBD.pst.setInt(1, id);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Horario(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getInt(2),
                    InterfazBD.rs.getTime(3),
                    InterfazBD.rs.getTime(4),
                    InterfazBD.rs.getBoolean(5),
                    InterfazBD.rs.getBoolean(6),
                    InterfazBD.rs.getBoolean(7),
                    InterfazBD.rs.getBoolean(8),
                    InterfazBD.rs.getBoolean(9),
                    InterfazBD.rs.getBoolean(10),
                    InterfazBD.rs.getBoolean(11)
            );
        } else {
            return null;
        }
    }

    public static ArrayList<Horario> buscar(String campo, int valor) throws SQLException {
        ArrayList<Horario> lista = new ArrayList<>();

        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from horario where " + campo + " = ?");
        InterfazBD.pst.setInt(1, valor);

        InterfazBD.rs = InterfazBD.pst.executeQuery();

        while (InterfazBD.rs.next()) {
            lista.add(new Horario(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getInt(2),
                    InterfazBD.rs.getTime(3),
                    InterfazBD.rs.getTime(4),
                    InterfazBD.rs.getBoolean(5),
                    InterfazBD.rs.getBoolean(6),
                    InterfazBD.rs.getBoolean(7),
                    InterfazBD.rs.getBoolean(8),
                    InterfazBD.rs.getBoolean(9),
                    InterfazBD.rs.getBoolean(10),
                    InterfazBD.rs.getBoolean(11)
            ));
        }

        return lista;
    }

    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into horario "
                + "(idPersonalH,horaInicial,horaFinal,lunes,martes,miercoles,jueves,viernes,sabado,domingo) values"
                + "(?,?,?,?,?,?,?,?,?,?)");

        InterfazBD.pst.setInt(1, this.idPersonalH);
        InterfazBD.pst.setTime(2, this.horaInicial);
        InterfazBD.pst.setTime(3, this.horaFinal);
        InterfazBD.pst.setBoolean(4, this.lunes);
        InterfazBD.pst.setBoolean(5, this.martes);
        InterfazBD.pst.setBoolean(6, this.miercoles);
        InterfazBD.pst.setBoolean(7, this.jueves);
        InterfazBD.pst.setBoolean(8, this.viernes);
        InterfazBD.pst.setBoolean(9, this.sabado);
        InterfazBD.pst.setBoolean(10, this.domingo);

        InterfazBD.pst.executeUpdate();
    }
    
    public void actualizar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update horario "
                + " set horaInicial = ?, horaFinal = ?, lunes=?, martes=? , miercoles=?, jueves=?, viernes=?, sabado = ?, domingo = ?, idPersonalH = ? "
                + "where idHorario = ?");
        
        InterfazBD.pst.setTime(1, this.horaInicial);
        InterfazBD.pst.setTime(2, this.horaFinal);
        InterfazBD.pst.setBoolean(3, this.lunes);
        InterfazBD.pst.setBoolean(4, this.martes);
        InterfazBD.pst.setBoolean(5, this.miercoles);
        InterfazBD.pst.setBoolean(6, this.jueves);
        InterfazBD.pst.setBoolean(7, this.viernes);
        InterfazBD.pst.setBoolean(8, this.sabado);
        InterfazBD.pst.setBoolean(9, this.domingo);
        InterfazBD.pst.setInt(10, this.idPersonalH);
        InterfazBD.pst.setInt(11, this.idHorario);

        InterfazBD.pst.executeUpdate();
    }

    public void eliminar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("delete from horario "
                + "where idHorario = ?");

        InterfazBD.pst.setInt(1, this.idHorario);
        
        InterfazBD.pst.executeUpdate();
    }

}
