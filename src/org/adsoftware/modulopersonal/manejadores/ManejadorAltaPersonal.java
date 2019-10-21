
package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.adsoftware.modulopersonal.interfaces.VAltaPersonal;

public class ManejadorAltaPersonal implements ActionListener {
    public JPanel panelPrincipal;
    public VAltaPersonal pnlAlta;
    
     public ManejadorAltaPersonal(JPanel panelPrin) throws SQLException {
        this.panelPrincipal = panelPrin;

        pnlAlta = new VAltaPersonal();
        
        repintarPanelPrincipal(pnlAlta);
    }
     private void repintarPanelPrincipal(JPanel panel) {
        panelPrincipal.removeAll();
        panelPrincipal.add(panel, "growx,growy,pushx,pushy");
        panelPrincipal.repaint();
    }
     
     
     
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
