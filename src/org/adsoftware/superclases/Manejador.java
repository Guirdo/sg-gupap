package org.adsoftware.superclases;

import javax.swing.JPanel;

public class Manejador {

    JPanel panelPrincipal;

    public Manejador(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }
    
    public Manejador(){
        
    }

    protected void repintarPanelPrincipal(JPanel pnl) {
        panelPrincipal.removeAll();
        panelPrincipal.add(pnl, "growx,growy,pushx,pushy");
        panelPrincipal.repaint();
    }

}
