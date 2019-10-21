package org.adsoftware.entidades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Informe(String rutaTexto, boolean enviado, boolean leido, int idPersonalI) {
        this.rutaTexto = rutaTexto;
        this.fecha = fecha;
        this.enviado = enviado;
        this.leido = leido;
        this.idPersonalI = idPersonalI;
    }

    //Buscar todos
    public static ArrayList<Informe> todos(String campo, boolean value,int idU) throws SQLException {
        ArrayList<Informe> lista = new ArrayList<>();

        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from informe where " + campo + " = ? "
                + "and idPersonalI = ?");
        InterfazBD.pst.setBoolean(1, value);
        InterfazBD.pst.setInt(2, idU);
        InterfazBD.rs = InterfazBD.pst.executeQuery();
        
        while (InterfazBD.rs.next()) {
            lista.add(new Informe(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getDate(3),
                    InterfazBD.rs.getBoolean(4),
                    InterfazBD.rs.getBoolean(5),
                    InterfazBD.rs.getInt(6)
            ));
        }

        return lista;
    }
    
    //Buscar todos
    public static ArrayList<Informe> todos(String campo, boolean value) throws SQLException {
        ArrayList<Informe> lista = new ArrayList<>();

        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from informe where " + campo + " = ?");
        InterfazBD.pst.setBoolean(1, value);
        InterfazBD.rs = InterfazBD.pst.executeQuery();
        
        while (InterfazBD.rs.next()) {
            lista.add(new Informe(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getDate(3),
                    InterfazBD.rs.getBoolean(4),
                    InterfazBD.rs.getBoolean(5),
                    InterfazBD.rs.getInt(6)
            ));
        }

        return lista;
    }

    public void insertar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("insert into informe "
                + "(rutaTexto,fecha,enviado,leido,idPersonalI) values"
                + "(?,now(),?,?,?)");

        InterfazBD.pst.setString(1, this.rutaTexto);
        InterfazBD.pst.setBoolean(2, this.enviado);
        InterfazBD.pst.setBoolean(3, this.leido);
        InterfazBD.pst.setInt(4, this.idPersonalI);

        InterfazBD.pst.executeUpdate();
    }

    public void guardar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update informe "
                + "set fecha = now() ,"
                + "enviado = ? ,"
                + "leido = ? "
                + "where idInforme = ?");
        
        InterfazBD.pst.setBoolean(1, this.enviado);
        InterfazBD.pst.setBoolean(2, this.leido);
        InterfazBD.pst.setInt(3, this.idInforme);

        InterfazBD.pst.executeUpdate();
    }
}
