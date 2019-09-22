package org.adsoftware.principal;

import com.alee.laf.WebLookAndFeel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.adsoftware.entidades.InterfazBD;

public class Principal {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                try {
                    WebLookAndFeel.install ();//Inicializa WebLaF
                    InterfazBD.crearConexion();//Inicia la conexion con la base de datos
                    new ManejadorPrincipal();//Inicia el programa
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } );
    }

}
