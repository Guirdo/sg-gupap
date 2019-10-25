package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextPane;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class VGenerarInforme extends JPanel{
    
    public WebButton btnGuardar;
    public WebButton btnEnviar;
    public WebButton btnCancelar;
    public WebLabel lblNombre;
    public WebTextPane tpInforme;
    
    public VGenerarInforme(){
        this.setLayout(new MigLayout("wrap 4","30[]30[]15[]15[]30","30[]20[]30"));
        
        WebLabel lblTitutlo = new WebLabel(StyleId.label,"Generar informe semanal");
        
        btnGuardar = new WebButton(StyleId.button,"Guardar");
        btnEnviar = new WebButton(StyleId.button,"Enviar");
        btnCancelar = new WebButton(StyleId.button,"Cancelar");
        lblNombre = new WebLabel(StyleId.label);
        tpInforme = new WebTextPane(StyleId.textpane);
        
        lblTitutlo.setFont(new Font("Arial",1,20));
        tpInforme.setFont(new Font("Arial",0,16));
        
        this.add(lblTitutlo,"north, growx");
        this.add(new WebLabel(StyleId.label,"Nombre: "),"split 2,growx,pushx");
        this.add(lblNombre,"growx,w 150");
        this.add(btnCancelar);
        this.add(btnGuardar);
        this.add(btnEnviar);
        this.add(tpInforme,"span 4,growx,pushx,growy,pushy,h 350");
    }

}
