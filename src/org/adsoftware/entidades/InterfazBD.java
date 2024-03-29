package org.adsoftware.entidades;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;

public class InterfazBD {

    private static String url = "jdbc:mysql://localhost:3306/bdgupap?serverTimezone=" + TimeZone.getDefault().getID();
    private static String usuario;
    private static String contrasena;

    public static Connection con;
    public static Statement st;
    public static PreparedStatement pst;
    public static ResultSet rs;
    public static ResultSetMetaData rsmd;
    public static CallableStatement cst;
    
    public static void crearConexion() throws ClassNotFoundException, SQLException{
        InterfazBD.usuario = "adsoft";
        InterfazBD.contrasena = "12345678";
        
        InterfazBD.con = null;

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, InterfazBD.usuario, InterfazBD.contrasena);
    }

    public static Object[][] consultar(String consulta) throws SQLException {
        //Matriz para obtener los registros de la tabla
        Object[][] datos;
        //Array dinamico para obtener los n registros de la tabla
        ArrayList<Object[]> lista = new ArrayList<>();

        //Inicializamos el ResultSet a nulo
        rs = null;
        //A partir del objeto Connection creamos un nuevo Statement
        st = con.createStatement();
        //Inicializamos el ResultSer ejecutando un query con el Statement
        rs = st.executeQuery(consulta);
        //Del ResultSet obtenemos los meta datos de la tabla que estamos
        //consultado
        rsmd = rs.getMetaData();

        while (rs.next()) {//Mientras el ResultSet tenga registros por leer, entonces...
            //Guardaremos la fila en un array
            Object[] array = new Object[rsmd.getColumnCount()];

            //Según cuantas columnas devuelva nuestra consulta,
            //comenzamos un ciclo
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                //Guarda columna por columna en el array
                array[i] = rs.getObject(i + 1);
            }

            //Añadimos la fila a nuestro array dinamico
            lista.add(array);
        }

        //Iniciaizamos nuestra matriz con el numero de filas
        //y el numero de columnas obtenidas por la consulta
        datos = new Object[lista.size()][rsmd.getColumnCount()];

        //Segun cuantas filas obtuvimos, entocnes
        for (int i = 0; i < lista.size(); i++) {
            Object[] array = lista.get(i);//Variable auxiliar

            //Vamos agregando dato por dato a la matriz
            for (int j = 0; j < rsmd.getColumnCount(); j++) {
                datos[i][j] = array[j];
            }
        }

        return datos;
    }

    /**
     * Metodo que realizar un update o un insert en la base de datos
     *
     * @param modificacion cadena de caracteres con el query
     * @throws SQLException
     */
    public void actualizar(String modificacion) throws SQLException {
        int filasAfectada;
        rs = null;
        st = con.createStatement();

        filasAfectada = st.executeUpdate(modificacion);
    }

    public void eliminar(String eliminacion) throws SQLException {
        int filasAfectada;
        rs = null;
        st = con.createStatement();

        filasAfectada = st.executeUpdate(eliminacion);
    }

    /**
     * Metodo que llama un procedimiento almacenado con los respectivo parametros de este
     * @param procedimiento Llamada al procedimiento
     * @param parametros Los argumentos que pide el procedimiento almacenado
     * @throws SQLException 
     */
    public void procedimientoInsertar(String procedimiento,Object... parametros) throws SQLException {
        //Se prepara la llamada
        cst = con.prepareCall(procedimiento);
        
        int i=1;
        //El ciclo que va agregando los parametros que recibe el procedimiento
        for(Object dato : parametros){
            cst.setObject(i, dato);
            i++;
        }
        
        //Ejecuta la llamada
        cst.execute();
    }
}
