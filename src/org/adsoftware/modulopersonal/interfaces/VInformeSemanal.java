package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class VInformeSemanal extends JPanel{
    
    public WebButton btnGenerar;
    public JPanel pnlBorradores;
    public JPanel pnlEnviados;

    public VInformeSemanal() {
        this.setLayout(new MigLayout("wrap 2","30[]20[]30","30[]25[]10[]25[]10[]30"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Generar informe semanal");
        btnGenerar = new WebButton(StyleId.button,"Generar informe");
        pnlBorradores = new JPanel(new MigLayout("wrap","0[]0","2[]2"));
        pnlEnviados = new JPanel(new MigLayout("wrap","0[]0","2[]2"));
        
        lblTitulo.setFont(new Font("Arial",1,20));
        
        this.add(lblTitulo,"north, gapleft 30");
        this.add(btnGenerar,"wrap");
        this.add(new WebLabel(StyleId.label,"Borradores"),"wrap");
        this.add(new JScrollPane(pnlBorradores),"span 2,growx,h 100, w 350");
        this.add(new WebLabel(StyleId.label,"Enviados"),"wrap");
        this.add(new JScrollPane(pnlEnviados),"span 2,h :150:,growx,growy,pushy");
    }
    
    

}
