package org.adsoftware.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.interfaces.VMenuPrincipal;
import org.adsoftware.interfaces.VMenuPrincipalAdministrador;
import org.adsoftware.interfaces.VMenuPrincipalDirector;
import org.adsoftware.modulopersonal.manejadores.ManejadorAltaPersonal;
import org.adsoftware.modulopersonal.manejadores.ManejadorVisualizarPersonal;
import org.adsoftware.modulousuario.manejadores.ManejadorModificarContrasena;
import org.adsoftware.modulousuario.manejadores.ManejadorValidarUsuario;

public class ManejadorPrincipal implements ActionListener {

    private VMenuPrincipal venMenu = null;
    private VMenuPrincipalAdministrador venAdmin = null;
    private VMenuPrincipalDirector venDirector = null;

    public ManejadorPrincipal() throws ClassNotFoundException, SQLException {
        new ManejadorValidarUsuario();
    }

    public ManejadorPrincipal(int idUsuario) {
        switch (idUsuario) {
            case Usuario.DIRECTOR:
                venDirector = new VMenuPrincipalDirector();
                venDirector.btnPersonalA.addActionListener(this);
                venDirector.btnPersonalV.addActionListener(this);
                venDirector.setVisible(true);
                break;
                
            case Usuario.COORDINADOR:
            case Usuario.RECEPCIONISTA:
                venMenu = new VMenuPrincipal();
                venMenu.setVisible(true);
                break;
            case Usuario.ADMINISTRADOR:
                venAdmin = new VMenuPrincipalAdministrador();

                venAdmin.btnGestion.addActionListener(this);

                venAdmin.setVisible(true);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (venAdmin != null) {
                if (e.getSource() == venAdmin.btnGestion) {
                    manejaEventoGestionUsuario();
                }
            }
            if(venDirector != null){
                if(e.getSource()== venDirector.btnPersonalA){
                    manejaEventoAltaPersonal();
                }else if(e.getSource()==venDirector.btnPersonalV)
                        manejaEventoVisualizarPersonal();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoGestionUsuario() throws SQLException {
        new ManejadorModificarContrasena(venAdmin.pnlPrincipal);
    }
    
    private void manejaEventoAltaPersonal() throws SQLException{
        new ManejadorAltaPersonal(venDirector.pnlPrincipal);
    }
    
    private void manejaEventoVisualizarPersonal() throws SQLException{
        new ManejadorVisualizarPersonal(venDirector.pnlPrincipal);
    }
}
