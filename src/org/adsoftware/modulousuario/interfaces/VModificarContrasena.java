package org.adsoftware.modulousuario.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebPasswordField;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VModificarContrasena extends JPanel{
    
    public WebPasswordField tfNueva,tfConfirmar;
    public WebButton btnModificar;
    
    public VModificarContrasena(String usuario){
        this.setLayout(new MigLayout("wrap 1","150::[center]150::","50::[]25[]35[]50::"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Modificar contraseña de "+usuario);
        tfNueva = new WebPasswordField(StyleId.passwordfield,20);
        tfConfirmar = new WebPasswordField(StyleId.passwordfield,20);
        btnModificar = new WebButton("Confirmar");
        
        lblTitulo.setFont(new Font("Arial",1,20));
        tfNueva.setFont(new Font("Arial",0,18));
        tfNueva.setInputPrompt("Nueva contraseña");
        tfConfirmar.setFont(new Font("Arial",0,18));
        tfConfirmar.setInputPrompt("Confirmar");
        btnModificar.setFont(new Font("Arial",0,16));
        
        this.add(lblTitulo,"north,gapleft 30");
        this.add(tfNueva,"");
        this.add(tfConfirmar,"");
        this.add(btnModificar);
    }

}
