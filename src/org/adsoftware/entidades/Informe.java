package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;

public class Informe {
    
    public int idInforme;
    public String rutaTexto;
    public Date fecha;
    public boolean enviado;
    public boolean leido;
    public int idPersonalI;

    public Informe(int idInforme, String rutaTexto, Date fecha, boolean enviado, boolean leido, int idPersonalI) {
        this.idInforme = idInforme;
        this.rutaTexto = rutaTexto;
        this.fecha = fecha;
        this.enviado = enviado;
        this.leido = leido;
        this.idPersonalI = idPersonalI;
    }

    public Informe(String rutaTexto, Date fecha, boolean enviado, boolean leido, int idPersonalI) {
        this.rutaTexto = rutaTexto;
        this.fecha = fecha;
        this.enviado = enviado;
        this.leido = leido;
        this.idPersonalI = idPersonalI;
    }
    
    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into informe "
                + "(rutaTexto,fecha,enviado,leido,idPersonalI) values"
                + "(?,?,?,?,?)");

        InterfazBD.pst.setString(1, this.rutaTexto);
        InterfazBD.pst.setDate(2, this.fecha);
        InterfazBD.pst.setBoolean(3, this.enviado);
        InterfazBD.pst.setBoolean(4, this.leido);
        InterfazBD.pst.setInt(5, this.idPersonalI);

        InterfazBD.pst.executeQuery();
    }
}
