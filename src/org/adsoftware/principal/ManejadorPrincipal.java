package org.adsoftware.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.interfaces.VMenuPrincipal;
import org.adsoftware.interfaces.VMenuPrincipalAdministrador;
import org.adsoftware.interfaces.VMenuPrincipalRecepcionista;
import org.adsoftware.modulopersonal.manejadores.ManejadorGenerarInforme;
import org.adsoftware.modulopersonal.manejadores.ManejadorRegistroES;
import org.adsoftware.modulousuario.manejadores.ManejadorModificarContrasena;
import org.adsoftware.modulousuario.manejadores.ManejadorValidarUsuario;

public class ManejadorPrincipal implements ActionListener {

    private VMenuPrincipal venMenu = null;
    private VMenuPrincipalAdministrador venAdmin = null;
    private VMenuPrincipalRecepcionista venRecep = null;
    
    private int idUsuario;

    public ManejadorPrincipal() throws ClassNotFoundException, SQLException {
        new ManejadorValidarUsuario();
    }

    public ManejadorPrincipal(int idUsuario) {
        this.idUsuario=idUsuario;
        switch (idUsuario) {
            case Usuario.DIRECTOR:
            case Usuario.COORDINADOR:
                venMenu = new VMenuPrincipal();
                venMenu.setVisible(true);
                break;
            case Usuario.ADMINISTRADOR:
                venAdmin = new VMenuPrincipalAdministrador();

                venAdmin.btnGestion.addActionListener(this);
                venAdmin.btnGenerarInforme.addActionListener(this);

                venAdmin.setVisible(true);
                break;

            case Usuario.RECEPCIONISTA:
                venRecep = new VMenuPrincipalRecepcionista();

                venRecep.btnRegistroES.addActionListener(this);

                venRecep.setVisible(true);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (venAdmin != null) {
                if (e.getSource() == venAdmin.btnGestion) {
                    manejaEventoGestionUsuario();
                }else if(e.getSource() == venAdmin.btnGenerarInforme){
                    manejaEventoGenerarInforme();
                }
            }else if(venRecep != null){
                if(e.getSource() == venRecep.btnRegistroES){
                    manejarEventoRegistroES();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoGestionUsuario() throws SQLException {
        new ManejadorModificarContrasena(venAdmin.pnlPrincipal);
    }

    private void manejarEventoRegistroES() throws SQLException {
        new ManejadorRegistroES(venRecep.pnlPrincipal);
    }

    private void manejaEventoGenerarInforme() throws SQLException {
        new ManejadorGenerarInforme(idUsuario,venAdmin.pnlPrincipal);
    }

}
