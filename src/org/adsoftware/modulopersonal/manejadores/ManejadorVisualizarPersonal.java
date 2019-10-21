
package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import com.alee.managers.style.StyleId;
import com.alee.laf.button.WebButton;
import org.adsoftware.entidades.Personal;
import org.adsoftware.goodies.Panelito;
import org.adsoftware.modulopersonal.interfaces.VVisualizarPersonal;

public class ManejadorVisualizarPersonal implements ActionListener {
private JPanel panelPrincipal;
private VVisualizarPersonal pnlV; 
private Personal p;

private ArrayList<Personal> listaP;

private ArrayList<WebButton> listaBotones;
 public ManejadorVisualizarPersonal(JPanel pnlPrin) throws SQLException {
        this.panelPrincipal = pnlPrin;

        pnlV = new VVisualizarPersonal();
        
        consultarPersonal();

        repintarPanelPrincipal(pnlV);
    }
     private void repintarPanelPrincipal(JPanel panel) {
        panelPrincipal.removeAll();
        panelPrincipal.add(panel, "growx,growy,pushx,pushy");
        panelPrincipal.repaint();
    }
     
     
    private void consultarPersonal() throws SQLException {
        listaP = Personal.todos();
        listaBotones = new ArrayList<>();

        for (int i = 0; i < listaP.size(); i++) {
            listaBotones.add(new WebButton(StyleId.buttonHover, "Datos completos"));
            listaBotones.get(i).addActionListener(this);
            pnlV.add(new Panelito("/org/adsoftware/iconos/usuario32.png",
                    listaP.get(i).nombreP, listaBotones.get(i)), "growx");
        }
        
        
    } 
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < listaP.size(); i++) {
            if (e.getSource() == listaBotones.get(i)) {
                p = listaP.get(i);
            }
        }
    }
    
    public void mostrar(){
        
    }
    
}
