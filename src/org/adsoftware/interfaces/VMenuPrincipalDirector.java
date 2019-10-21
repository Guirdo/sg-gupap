/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adsoftware.interfaces;

import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;

/**
 *
 * @author diann
 */
public class VMenuPrincipalDirector extends Pantalla {
    public JPanel pnlPrincipal;
    public WebButton btnPersonalA, btnPersonalV;

    public VMenuPrincipalDirector() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));
        
        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));

        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :550:");

        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));
        
        btnPersonalA = new WebButton(StyleId.button,"Dar de alta");
        btnPersonalV = new WebButton(StyleId.button,"Visualizar personal");

        WebCollapsiblePane btnP = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal", btnPersonalV);
        WebCollapsiblePane btnPA = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal", btnPersonalA);
        pnl.add(btnP,"");
        pnl.add(btnPA,"");
        

        return pnl;
    }
    
    
}
