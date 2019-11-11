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

    public WebButton btnPersonalA, btnPersonalV;
    public JPanel pnlPrincipal;
    public WebButton btnLeerInformes;
    private WebCollapsiblePane btnPersonal;
    
    public VMenuPrincipalDirector() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));

        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        btnLeerInformes = new WebButton(StyleId.button, "Informes");
        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :600:");

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));
        
        WebCollapsiblePane btnP = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal", panelBotonesPersonal());
        
        pnl.add(btnP,"");
        

        return pnl;
    }
    private JPanel panelBotonesPersonal() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "1[]1", ""));

        btnPersonalA = new WebButton(StyleId.button,"Dar de alta");
        btnPersonalV = new WebButton(StyleId.button,"Visualizar personal");
       

        pnl.add(btnPersonalA);
        pnl.add(btnPersonalV);
        pnl.add(btnLeerInformes);
        
        return pnl;
    }
}
