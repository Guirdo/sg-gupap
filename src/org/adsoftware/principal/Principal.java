package org.adsoftware.principal;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.window.WebFrame;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.modulogrupo.interfaces.VRegistroGrupo;

public class Principal {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                try {
                    WebLookAndFeel.install ();//Inicializa WebLaF
                    //InterfazBD.crearConexion();//Inicia la conexion con la base de datos
                    //new ManejadorPrincipal();//Inicia el programa
                    WebFrame frame = new WebFrame();
                    frame.add(new VRegistroGrupo());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
//                catch (ClassNotFoundException | SQLException ex) {
//                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
//                }
                catch(Exception e){
                    
                }
            }
        } );
    }

}
