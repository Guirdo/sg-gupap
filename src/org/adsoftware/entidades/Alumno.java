package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;


public class Alumno {
    
    public final static String FEMENINO = "Femenino";
    public final static String MASCULINO = "Masculino";
    
    public int idAlumno;
    public String nombre, apellidoPatA, apellidoMatA, domicilioA, telefono, genero;
    public Date fechaNacimiento;
    public int numSemanas, idGrupoA;

    public Alumno(int idAlumno, String nombre, String apellidoPatA, String apellidoMatA, Date fechaNacimiento, String genero, String domicilioA, String telefono, int numSemanas, int idGrupoA) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidoPatA = apellidoPatA;
        this.apellidoMatA = apellidoMatA;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.domicilioA = domicilioA;
        this.telefono = telefono;
        this.numSemanas = numSemanas;
        this.idGrupoA = idGrupoA;
    }

    public Alumno(String nombre, String apellidoPatA, String apellidoMatA, Date fechaNacimiento, String genero, String domicilioA, String telefono, int idGrupoA) {
        this.nombre = nombre;
        this.apellidoPatA = apellidoPatA;
        this.apellidoMatA = apellidoMatA;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.domicilioA = domicilioA;
        this.telefono = telefono;
        this.idGrupoA = idGrupoA;
    }
    
    //BuscarPrimero
    public static Alumno buscarPrimero(String campo, String valor) throws SQLException {

        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from alumno where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Alumno(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getString(6),
                    InterfazBD.rs.getString(7),
                    InterfazBD.rs.getString(8),
                    InterfazBD.rs.getInt(9),
                    InterfazBD.rs.getInt(10)
            );
        } else {
            return null;
        }
    }
    
    //Buscar primero 
    public static ArrayList<Alumno> buscar(String campo, String valor) throws SQLException {
        ArrayList<Alumno> lista = new ArrayList<>();
        
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from alumno where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        while(InterfazBD.rs.next()){
            lista.add( new Alumno(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getString(6),
                    InterfazBD.rs.getString(7),
                    InterfazBD.rs.getString(8),
                    InterfazBD.rs.getInt(9),
                    InterfazBD.rs.getInt(10)
            ));
        }
        return lista;
    }

    public Alumno(int idAlumno, String nombre, String apellidoPatA, String apellidoMatA) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidoPatA = apellidoPatA;
        this.apellidoMatA = apellidoMatA;
    }
    
    //Buscar todos
    public static ArrayList<Alumno> todos() throws SQLException {
        ArrayList<Alumno> lista = new ArrayList<>();

        InterfazBD.st = InterfazBD.con.createStatement();
        InterfazBD.rs = InterfazBD.st.executeQuery("select * from alumno");

        while (InterfazBD.rs.next()) {
            lista.add(new Alumno(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4)
//                    InterfazBD.rs.getString(5),
//                    InterfazBD.rs.getString(6),
//                    InterfazBD.rs.getString(7),
//                    InterfazBD.rs.getDate(8),
//                    InterfazBD.rs.getInt(9),
//                    InterfazBD.rs.getInt(10)
            ));
        }
        return lista;
    }

    //Guardar
    public void guardar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into alumno "
                + " (nombreA, apellidoPatA, apellidoMatA, fechaNacimiento, genero, domicilioA, telefono, idGrupoA)"
                + "values (?,?,?,?,?,?,?,?)");
       
                    InterfazBD.pst.setString(1, this.nombre);
                    InterfazBD.pst.setString(2, this.apellidoPatA);
                    InterfazBD.pst.setString(3, this.apellidoMatA);
                    InterfazBD.pst.setDate(4, this.fechaNacimiento);
                    InterfazBD.pst.setString(5, this.genero);
                    InterfazBD.pst.setString(6, this.domicilioA);
                    InterfazBD.pst.setString(7, this.telefono);
                    InterfazBD.pst.setInt(8, this.idGrupoA);

        InterfazBD.pst.executeUpdate();
    }

    public void baja() throws SQLException{
        InterfazBD.pst= InterfazBD.con.prepareStatement("delete from alumno"
                + " where idAlumno=?");
        
        InterfazBD.pst.setInt(1, this.idAlumno);
        InterfazBD.pst.executeUpdate();
        
    }

    public void actualizar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update alumno "
                + " set nombre=?, apellidoPatA=?, apellidoMatA=?, fechaNacimiento=?, genero=?, domicilioA=?, telefono=? "
                + "where idAlumno = ?");
        
                    InterfazBD.pst.setString(1, this.nombre);
                    InterfazBD.pst.setString(2, this.apellidoPatA);
                    InterfazBD.pst.setString(3, this.apellidoMatA);
                    InterfazBD.pst.setDate(4, this.fechaNacimiento);
                    InterfazBD.pst.setString(5, this.genero);
                    InterfazBD.pst.setString(6, this.domicilioA);
                    InterfazBD.pst.setString(7, this.telefono);
                    InterfazBD.pst.setInt(8, this.idAlumno);

        InterfazBD.pst.executeUpdate();
    }
    
    
    
    
}
