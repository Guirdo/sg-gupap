package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VGenerarInforme extends JPanel{
    
    public WebButton btnGuardar;
    public WebButton btnEnviar;
    public WebLabel lblNombre;
    
    public VGenerarInforme(){
        this.setLayout(new MigLayout("","",""));
    }

}
