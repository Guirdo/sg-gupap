/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.goodies.Panelito;

/**
 *
 * @author diann
 */
public class VVisualizarPersonal extends JPanel {
    public JScrollPane tabla;
    public JPanel este;
    public Panelito pnlto;

    public VVisualizarPersonal() {
        this.setLayout(new MigLayout("wrap 1", "10[]10", "10[]15[]10[150]20[]15[]10[]10[]10[]10[]10[]10"));
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Dar de alta personal");
        lblTitulo.setFont(new Font("Arial", 0, 20));
       
        este = new JPanel();
        tabla = new JScrollPane(este);
        
        
        this.add(lblTitulo,"north,gapleft 30");
        this.add(este);
        this.add(tabla);
    }
    
}

