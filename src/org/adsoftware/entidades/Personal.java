
package org.adsoftware.entidades;

import java.sql.SQLException;
import java.util.ArrayList;

public class Personal {
    
    public final static int cDOCENTE = 1;
    public final static int cCONTADOR = 2;
    public final static int cRECEPCIONISTA = 3;
    public final static int cADMINISTRADOR = 4;
    public final static int cCOORDINADOR = 5;
    
    int idPersonal;
    String nombreP, apellidoPatP, apellidoMatP, domicilioP, cargo;
//    Agregar esto a variables
//	fechaNacimiento date,

    public Personal(int idPersonal, String nombreP, String apellidoPatP, String apellidoMatP, String domicilioP, String cargo) {
        this.idPersonal = idPersonal;
        this.nombreP = nombreP;
        this.apellidoPatP = apellidoPatP;
        this.apellidoMatP = apellidoMatP;
        this.domicilioP = domicilioP;
        this.cargo = cargo;
    }

    public Personal(String nombreP, String apellidoPatP, String apellidoMatP, String domicilioP, String cargo) {
        this.nombreP = nombreP;
        this.apellidoPatP = apellidoPatP;
        this.apellidoMatP = apellidoMatP;
        this.domicilioP = domicilioP;
        this.cargo = cargo;
    }
    
    //BuscarPrimero
     public static Personal buscarPrimero(String campo, String valor) throws SQLException {
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from usuario where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Personal(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getString(5),
                    InterfazBD.rs.getString(6)
            );
        } else {
            return new Personal(1, "2", "3", "4", "5", "6");
        }
    }
    
     
     //Buscar todos
     public static ArrayList<Personal> todos() throws SQLException {
        ArrayList<Personal> lista = new ArrayList<>();

        InterfazBD.st = InterfazBD.con.createStatement();
        InterfazBD.rs = InterfazBD.st.executeQuery("select * from usuario");

        while (InterfazBD.rs.next()) {
            lista.add(new Personal(
                    InterfazBD.rs.getString(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getString(4),
                    InterfazBD.rs.getString(5)
            ));
        }

        return lista;
    }
     
    public void guardar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into personal "
                + "set nombreP = ? ,"
                + "apellidoPatP = ? ,"
                + "apellidoMatP = ? ,"
                + "domicilioP = ? ,"
                + "cargo = ? " 
                + "where idPersonal = ?");
        
        InterfazBD.pst.setString(1, this.nombreP);
        InterfazBD.pst.setString(2, this.apellidoPatP);
        InterfazBD.pst.setString(3, this.apellidoMatP);
        InterfazBD.pst.setString(4, this.domicilioP);
        InterfazBD.pst.setString(5, this.cargo);
        InterfazBD.pst.setInt(6, this.idPersonal);

        InterfazBD.pst.executeQuery(); 
    }
     
}
