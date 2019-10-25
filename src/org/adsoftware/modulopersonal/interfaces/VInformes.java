package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class VInformes extends JPanel{
    
    public JPanel pnlRecibidos;
    public JPanel pnlLeidos;

    public VInformes() {
        this.setLayout(new MigLayout("wrap 2", "30[]20[]30", "30[]10[]25[]10[]30"));

        WebLabel lblTitulo = new WebLabel(StyleId.label, "Bandeja de informes");
        pnlRecibidos = new JPanel(new MigLayout("wrap", "0[]0", "2[]2"));
        pnlLeidos = new JPanel(new MigLayout("wrap", "0[]0", "2[]2"));

        lblTitulo.setFont(new Font("Arial", 1, 20));

        this.add(lblTitulo, "north, gapleft 30");
        this.add(new WebLabel(StyleId.label, "Borradores"), "wrap");
        this.add(new JScrollPane(pnlRecibidos), "span 2,growx,h 200,center");
        this.add(new WebLabel(StyleId.label, "Enviados"), "wrap");
        this.add(new JScrollPane(pnlLeidos), "span 2,h :200:,growx,growy,pushy");
    }

    
    
}
