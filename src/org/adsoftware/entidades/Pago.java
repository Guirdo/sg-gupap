package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pago {
    
    public static final String SEMANAL = "SEMANAL";
    public static final String INSCRIPCION = "INSCRIPCION";
    public static final String REINSCRIPCION = "REINSCRIPCION";
    
    public int idPago;
    public Date fecha;
    public String concepto;
    public double monto;
    public int idAlumnoP;

    public Pago(int idPago, Date fecha, String concepto, double monto, int idAlumnoP) {
        this.idPago = idPago;
        this.fecha = fecha;
        this.concepto = concepto;
        this.monto = monto;
        this.idAlumnoP = idAlumnoP;
    }

    public Pago(Date fecha, String concepto, double monto, int idAlumnoP) {
        this.fecha = fecha;
        this.concepto = concepto;
        this.monto = monto;
        this.idAlumnoP = idAlumnoP;
    }
    
    public static ArrayList<Pago> buscar(String campo, String valor) throws SQLException {
        ArrayList<Pago> lista = new ArrayList<>();
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from pago where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        while(InterfazBD.rs.next()){
            lista.add( new Pago(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getDate(2),
                    InterfazBD.rs.getString(3),
                    InterfazBD.rs.getDouble(4),
                    InterfazBD.rs.getInt(5)
            ));
        }
        
        return lista;
    }
    
    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into pago "
                + " (fecha,monto,idAlumnoP) "
                + "values (?,?,?)");

        InterfazBD.pst.setDate(1, this.fecha);
        InterfazBD.pst.setDouble(2, this.monto);
        InterfazBD.pst.setInt(3, this.idAlumnoP);

        InterfazBD.pst.executeUpdate();
    }

}
