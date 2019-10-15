package org.adsoftware.interfaces;

import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;

public class VMenuPrincipalRecepcionista extends Pantalla {

    public JPanel pnlPrincipal;
    public WebButton btnRegistroES;
    private WebCollapsiblePane btnPersonal;

    public VMenuPrincipalRecepcionista() {
        super(StyleId.frameDecorated, "SG-GUPAP Menú principal");

        this.setLayout(new MigLayout("wrap 1", "", "[]20[]"));

        pnlPrincipal = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        
        btnRegistroES = new WebButton(StyleId.button, "Registro E/S personal");

        this.add(panelBotones(), "west");
        this.add(pnlPrincipal, "h :550:, w :550:");

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel panelBotones() {
        JPanel pnl = new JPanel(new MigLayout("wrap 1", "15[]15", ""));

        btnPersonal = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal",btnRegistroES);
        
        pnl.add(btnPersonal);

        return pnl;
    }

}
