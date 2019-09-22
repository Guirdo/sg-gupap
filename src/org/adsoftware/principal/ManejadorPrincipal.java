package org.adsoftware.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.interfaces.VMenuPrincipal;
import org.adsoftware.interfaces.VMenuPrincipalAdministrador;
import org.adsoftware.modulousuario.manejadores.ManejadorValidarUsuario;

public class ManejadorPrincipal implements ActionListener{

    private VMenuPrincipal venMenu = null;
    private VMenuPrincipalAdministrador venAdmin = null;
    

    public ManejadorPrincipal() throws ClassNotFoundException, SQLException {
        new ManejadorValidarUsuario();
    }

    public ManejadorPrincipal(int idUsuario) {        
        switch (idUsuario) {
            case Usuario.DIRECTOR:
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
        if(venAdmin!=null){
            if(e.getSource() == venAdmin.btnGestion){
                System.out.println("Hola! SI FUNCIONO!");
            }
        }
    }


}
