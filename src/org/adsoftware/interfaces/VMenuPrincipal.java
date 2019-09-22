package org.adsoftware.interfaces;

import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;

public class VMenuPrincipal extends Pantalla{
    
    public JPanel pnlPrincipal;

    public VMenuPrincipal() {
        super(StyleId.frameDecorated,"SG-GUPAP Menú principal");
        this.setLayout(new MigLayout("wrap 1","","[]20[]"));
        
        pnlPrincipal = new JPanel(new MigLayout("","0[]0","0[]0"));
        
        this.add(panelBotones(),"west");
        this.add(pnlPrincipal,"h 400, w 400");
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private JPanel panelBotones(){
        JPanel pnl = new JPanel(new MigLayout("wrap 1","15[]15",""));
        
        WebCollapsiblePane btnGrupo = new WebCollapsiblePane(StyleId.collapsiblepane,"Grupos",new WebButton("Crear grupo"));
        WebCollapsiblePane btnPersonal = new WebCollapsiblePane(StyleId.collapsiblepane, "Personal");
        WebCollapsiblePane btnAlumnos = new WebCollapsiblePane(StyleId.collapsiblepane, "Alumnos");
        WebCollapsiblePane btnPagos = new WebCollapsiblePane(StyleId.collapsiblepane, "Pagos");        
        
        pnl.add(btnGrupo,"");
        pnl.add(btnPersonal,"");
        pnl.add(btnAlumnos,"");
        pnl.add(btnPagos,"");
        
        return pnl;
    }

}
