package org.adsoftware.modulousuario.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import com.alee.managers.tooltip.TooltipWay;
import java.awt.Font;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.superclases.Pantalla;
import org.adsoftware.utilidades.Galeria;

public class VInicioSesion extends Pantalla{
    
    public WebTextField tfNombreUsuario;
    public WebPasswordField tfContrasena;
    public WebButton btnIngresar;
    
    public VInicioSesion(){
        super(StyleId.frameDecorated,"SG-GUPAP Inicio de sesión");
        this.setLayout(new MigLayout("wrap 1","120[center]120","40[]70[]30[]15[]15[]20[]55"));
        
        WebLabel lblMarca = new WebLabel(
                new ImageIcon(getClass().getResource(Galeria.MARCA_INSTITUCIONAL)));
        WebLabel lblUsuario = new WebLabel(
                new ImageIcon(getClass().getResource(Galeria.USUARIO128_ICON)));
        WebLabel lblOlvide = new WebLabel(StyleId.label,"Olvidé la contraseña",WebLabel.CENTER);
        
        tfNombreUsuario = new WebTextField(StyleId.textfield,15);
        tfContrasena = new WebPasswordField(StyleId.passwordfield,15);
        btnIngresar = new WebButton(StyleId.button, "Ingresar");
        
        tfNombreUsuario.setInputPrompt("Nombre de usuario");
        tfNombreUsuario.setFont(new Font("Arial",0,18));
        tfContrasena.setInputPrompt("Contraseña");
        tfContrasena.setFont(new Font("Arial",0,18));
        lblOlvide.setToolTip("Pongase en contacto con el Administrador", TooltipWay.down, 0);
        btnIngresar.setFont(new Font("Arial",0,16));
        
        this.add(lblMarca,"growx,pushx");
        this.add(lblUsuario);
        this.add(tfNombreUsuario,"sg a");
        this.add(tfContrasena,"sg a");
        this.add(lblOlvide,"sg a");
        this.add(btnIngresar,"sg a");
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
