package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personal {

    public final static String DOCENTE = "Docente";
    public final static String CONTADOR = "Contador";
    public final static String RECEPCIONISTA = "Recepcionista";
    public final static String ADMINISTRADOR = "Administrador";
    public final static String COORDINADOR = "Coordinador";
    public final static String FEMENINO = "Femenino";
    public final static String MASCULINO = "Masculino";
    public int idPersonal;
    public String nombreP, apellidoPatP, apellidoMatP, domicilioP, cargo, correo, genero;
    public Date fechaNacimiento;

    public Personal(int idPersonal, String nombreP, String apellidoPatP, String apellidoMatP, Date fechaNacimiento, String domicilioP, String cargo, String correo, String genero) {
        this.idPersonal = idPersonal;
        this.nombreP = nombreP;
        this.apellidoPatP = apellidoPatP;
        this.apellidoMatP = apellidoMatP;
        this.domicilioP = domicilioP;
        this.cargo = cargo;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.genero = genero;
    }
    
    public Personal(String nombreP, String apellidoPatP, String apellidoMatP, Date fechaNacimiento, String domicilioP, String cargo, String correo, String genero) {
        this.nombreP = nombreP;
        this.apellidoPatP = apellidoPatP;
        this.apellidoMatP = apellidoMatP;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilioP = domicilioP;
        this.cargo = cargo;
        this.correo = correo;
        this.genero = genero;
    }

    //BuscarPrimero
    public static Personal buscarPrimero(String campo, String valor) throws SQLException {
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from personal where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Personal(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getString(6),
                    InterfazBD.rs.getString(7),
                    InterfazBD.rs.getString(8),
                    InterfazBD.rs.getString(9)
            );
        } else {
            return null;
        }
    }
    
    public static ArrayList<Personal> buscar(String campo, String valor) throws SQLException {
        ArrayList<Personal> lista = new ArrayList<>();
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from personal where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        while(InterfazBD.rs.next()){
            lista.add( new Personal(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getString(6),
                    InterfazBD.rs.getString(7),
                    InterfazBD.rs.getString(8),
                    InterfazBD.rs.getString(9)
            ));
        }
        
        return lista;
    }

    //Buscar todos
    public static ArrayList<Personal> todos() throws SQLException {
        ArrayList<Personal> lista = new ArrayList<>();

        InterfazBD.st = InterfazBD.con.createStatement();
        InterfazBD.rs = InterfazBD.st.executeQuery("select * from personal");

        while (InterfazBD.rs.next()) {
            lista.add(new Personal(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getDate(5),
                    InterfazBD.rs.getString(6),
                    InterfazBD.rs.getString(7),
                    InterfazBD.rs.getString(8),
                    InterfazBD.rs.getString(9)
            ));
        }

        return lista;
    }

    public void guardar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into personal "
                + " (nombreP, apellidoPatP, apellidoMatP, fechaNacimiento, domicilioP, cargo, correo, genero)"
                + "values (?,?,?,?,?,?,?,?)");
        
        InterfazBD.pst.setString(1, this.nombreP);
        InterfazBD.pst.setString(2, this.apellidoPatP);
        InterfazBD.pst.setString(3, this.apellidoMatP);
        InterfazBD.pst.setDate(4, this.fechaNacimiento);
        InterfazBD.pst.setString(5, this.domicilioP);
        InterfazBD.pst.setString(6, this.cargo);
        InterfazBD.pst.setString(7, this.correo);
        InterfazBD.pst.setString(8, this.genero);

        InterfazBD.pst.executeUpdate();
    }
    
    public void actualizar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update personal "
                + " set nombreP = ?, apellidoPatP=?, apellidoMatP=?, fechaNacimiento=? , domicilioP=?, cargo=?, correo=?, genero=? "
                + "where idPersonal = ?");
        
        InterfazBD.pst.setString(1, this.nombreP);
        InterfazBD.pst.setString(2, this.apellidoPatP);
        InterfazBD.pst.setString(3, this.apellidoMatP);
        InterfazBD.pst.setDate(4, this.fechaNacimiento);
        InterfazBD.pst.setString(5, this.domicilioP);
        InterfazBD.pst.setString(6, this.cargo);
        InterfazBD.pst.setString(7, this.correo);
        InterfazBD.pst.setString(8, this.genero);
        InterfazBD.pst.setInt(9, this.idPersonal);

        InterfazBD.pst.executeUpdate();
    }
    
    public void baja() throws SQLException{
        InterfazBD.pst= InterfazBD.con.prepareStatement("delete from personal"
                + " where idPersonal=?");
        
        InterfazBD.pst.setInt(1, this.idPersonal);
        InterfazBD.pst.executeUpdate();
        
    }

}
