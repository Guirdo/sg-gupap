package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextPane;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VLecturaInforme extends JPanel{
    
    public WebButton btnAceptar;
    public WebLabel lblNombre;
    public WebLabel lblFecha;
    public WebTextPane tpInforme;
    
    public VLecturaInforme(){
        this.setLayout(new MigLayout("wrap 4","30[]10[]15[]15[]30","30[]20[]30"));
        
        WebLabel lblTitutlo = new WebLabel(StyleId.label,"Lectura informe semanal");
        
        btnAceptar = new WebButton(StyleId.button,"Aceptar");
        lblNombre = new WebLabel(StyleId.label);
        lblFecha = new WebLabel(StyleId.label);
        tpInforme = new WebTextPane(StyleId.textpane);
        
        lblTitutlo.setFont(new Font("Arial",1,20));
        tpInforme.setFont(new Font("Arial",0,16));
        tpInforme.setEditable(false);
        
        this.add(lblTitutlo,"north, growx");
        this.add(new WebLabel(StyleId.label,"Nombre: "));
        this.add(lblNombre);
        this.add(lblFecha,"growx,pushx");
        this.add(btnAceptar,"span 2,center,growx,pushx");
        this.add(tpInforme,"span 4,growx,pushx,growy,pushy,h 350");
    }

}
