package org.adsoftware.entidades;

import java.sql.SQLException;
import java.util.ArrayList;
import static org.adsoftware.entidades.InterfazBD.con;
import static org.adsoftware.entidades.InterfazBD.rs;
import static org.adsoftware.entidades.InterfazBD.st;

public class Usuario {

    public final static int DIRECTOR = 1;
    public final static int ADMINISTRADOR = 2;
    public final static int COORDINADOR = 3;
    public final static int RECEPCIONISTA = 4;

    public int idUsuario;
    public String nombreUsuario;
    public String contrasena;

    public Usuario(int idUsuario, String nombreUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public static Usuario buscarPrimero(String campo, String valor) throws SQLException {
        //A partir del objeto Connection creamos un nuevo Statement
        InterfazBD.pst = InterfazBD.con.prepareStatement("select * from usuario where " + campo + " = ?");

        InterfazBD.pst.setString(1, valor);
        //Inicializamos el ResultSer ejecutando un query con el Statement
        InterfazBD.rs = InterfazBD.pst.executeQuery();

        if (InterfazBD.rs.first()) {
            return new Usuario(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3)
            );
        } else {
            return new Usuario(5, "hola", "soy un error");
        }
    }

    /**
     * Metodo que realiza una consulta a toda la tabla
     *
     * @return {@code ArrayList<>} con todos los objetos de la tabla.
     * @throws SQLException
     */
    public static ArrayList<Usuario> todos() throws SQLException {
        ArrayList<Usuario> lista = new ArrayList<>();

        InterfazBD.st = InterfazBD.con.createStatement();
        InterfazBD.rs = InterfazBD.st.executeQuery("select * from usuario");

        while (InterfazBD.rs.next()) {
            lista.add(new Usuario(
                    InterfazBD.rs.getInt(1),
                    InterfazBD.rs.getString(2),
                    InterfazBD.rs.getString(3)
            ));
        }

        return lista;
    }

    public void guardar() throws SQLException {
        InterfazBD.pst = InterfazBD.con.prepareStatement("update usuario "
                + "set nombreUsuario = ? ,"
                + "contrasena = ? "
                + "where idUsuario = ?");
        
        InterfazBD.pst.setString(1, this.nombreUsuario);
        InterfazBD.pst.setString(2, this.contrasena);
        InterfazBD.pst.setInt(3, this.idUsuario);

        InterfazBD.pst.executeUpdate();
    }

}
