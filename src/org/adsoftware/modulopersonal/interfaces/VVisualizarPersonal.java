
package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import com.alee.extended.collapsible.WebCollapsiblePane;
import javax.swing.JButton;
import org.adsoftware.goodies.Panelito;

public class VVisualizarPersonal extends JPanel {
    public JScrollPane tabla;
    public JPanel este, pnlDatos;
    public WebLabel icono, nombre, correo, cargo, domicilio;
    public WebCollapsiblePane grupos;
    public JButton baja;
    
    

    public VVisualizarPersonal() {
        this.setLayout(new MigLayout("wrap 1", "30[]30", "30[]15[]30"));
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Dar de alta personal");
        lblTitulo.setFont(new Font("Arial", 0, 20));       
        este = new JPanel(new MigLayout("wrap 1","2[]2",""));
        tabla = new JScrollPane(este);
        pnlDatos = new JPanel(new MigLayout("wrap 3","0[]40[]50[]0","0[]15[]15[]15[]0"));
        
       icono = new WebLabel(StyleId.label,"");
       nombre = new WebLabel(StyleId.label, "nombre");
       nombre.setFont(new Font("Arial",0,16));
       correo = new WebLabel(StyleId.label, "correo");
       correo.setFont(new Font("Arial",0,16));
       cargo = new WebLabel(StyleId.label, "cargo");
       cargo.setFont(new Font("Arial",0,16));
       domicilio = new WebLabel(StyleId.label, "domicilio");
       domicilio.setFont(new Font("Arial",0,16));
        
       grupos = new WebCollapsiblePane(StyleId.collapsiblepane, "Grupos asignados");
       baja = new JButton("Dar de baja");
       
        
        pnlDatos.add(icono, "span 1 4, h 126, w 126");
        pnlDatos.add(nombre, "");
        pnlDatos.add(grupos, "span 1 3");
        pnlDatos.add(correo, "");
        pnlDatos.add(cargo, "");
        pnlDatos.add(domicilio,"");
        pnlDatos.add(baja,"");
        
        this.add(lblTitulo,"north,gapleft 30");
        this.add(new JScrollPane(este),"h 150, growx");
        this.add(pnlDatos, "growx, pushx");
    }
    
}

