package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VRegistroES extends JPanel{
    
    private WebTable tabla;
    private WebLabel lblIcono,lblNombre,lblCargo;
    private WebTextField tfClave;
    private WebButton btnEntrada,btnSalida;
    
    public static final String MALE_ICON = "/org/adsoftware/iconos/male64.png";
    public static final String FEMALE_ICON = "/org/adsoftware/iconos/female64.png";
    
    public VRegistroES(){
        this.setLayout(new MigLayout("wrap 2","30[]20[]30","30[]25[]15[]15[]20[]15[]30::"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label);
        
        lblIcono = new WebLabel(StyleId.label);
        
        lblTitulo.setFont(new Font("Arial",1,20));
        
        this.add(lblTitulo,"span 2");
    }

}
