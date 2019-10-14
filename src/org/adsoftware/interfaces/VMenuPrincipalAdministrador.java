package org.adsoftware.interfaces;

import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;

public class VMenuPrincipalAdministrador extends Pantalla {

    public JPanel pnlPrincipal;
    public WebButton btnGestion;
    private WebCollapsiblePane btnUsuario;

    public VMenuPrincipalAdministrador() {
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

        btnUsuario = new WebCollapsiblePane(StyleId.collapsiblepane, "Usuarios");
        
        botonesUsuario();
        pnl.add(btnUsuario);

        return pnl;
    }
    
    
    private void botonesUsuario(){
        btnGestion = new WebButton(StyleId.button,"Gestión");
        
        btnUsuario.add(btnGestion);
    }
}
