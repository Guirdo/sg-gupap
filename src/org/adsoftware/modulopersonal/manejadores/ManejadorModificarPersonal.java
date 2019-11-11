package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulopersonal.interfaces.DMModificarPersonal;
import org.adsoftware.superclases.Manejador;

public class ManejadorModificarPersonal extends Manejador implements ActionListener{
    
    private DMModificarPersonal vista;
    private Personal personal;

    public ManejadorModificarPersonal(Personal p) {
        vista = new DMModificarPersonal();
        personal = p;
        
        vista.btnModificar.addActionListener(this);
        
        llenarCampos();
        
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnModificar){
            try {
                manejaEventoModificar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorModificarPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void llenarCampos() {
        vista.tfNombreP.setText(personal.nombreP);
        vista.tfApellidoPatP.setText(personal.apellidoPatP);
        vista.tfApellidoMatP.setText(personal.apellidoMatP);
        vista.tfDomicilioP.setText(personal.domicilioP);
        vista.tfCorreoP.setText(personal.correo);
        vista.fechaN.setDate(personal.fechaNacimiento);
        vista.cmbCargo.setSelectedItem(personal.cargo);
        
        if(personal.genero.equals(Personal.FEMENINO))
            vista.generoF.setSelected(true);
        else
            vista.generoM.setSelected(true);
    }

    private void manejaEventoModificar() throws SQLException {
        personal.nombreP = vista.tfNombreP.getText();
        personal.apellidoPatP = vista.tfApellidoPatP.getText();
        personal.apellidoMatP = vista.tfApellidoMatP.getText();
        personal.domicilioP = vista.tfDomicilioP.getText();
        //TODO Continuar con la actualización del objetos
        
        
        personal.guardar();
    }
    
    

}
