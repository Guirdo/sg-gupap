package org.adsoftware.modulousuario.interfaces;

import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VGestionPersonal extends JPanel{
    
    public VGestionPersonal(){
        this.setLayout(new MigLayout("wrap 1","30[]30","30[]20[]20[]20[]30::"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Gestión de usuarios");
        lblTitulo.setFont(new Font("Arial",0,20));  
        
        this.add(lblTitulo,"north, gapleft 30");
    }

}
