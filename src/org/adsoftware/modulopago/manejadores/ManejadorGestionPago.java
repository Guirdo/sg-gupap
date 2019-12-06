package org.adsoftware.modulopago.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.adsoftware.modulopago.interfaces.DMRegistrarPago;
import org.adsoftware.modulopago.interfaces.VGestionPagos;
import org.adsoftware.superclases.Manejador;

public class ManejadorGestionPago extends Manejador implements ActionListener{
    
    private VGestionPagos vista;
    
    public ManejadorGestionPago(JPanel pnl){
        super(pnl);
        vista = new VGestionPagos();
        
        vista.btnRegistrarPago.addActionListener(this);
        
        repintarPanelPrincipal(vista);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnRegistrarPago){
            new DMRegistrarPago().setVisible(true);
        }
    }

}
