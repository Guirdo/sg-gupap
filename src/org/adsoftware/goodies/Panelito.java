package org.adsoftware.goodies;

import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Panelito extends JPanel{
    
    private String rutaIcono;
    private String cadena;
    private JButton btn;

    public Panelito(String rutaIcono, String cadena, JButton boton) {
        this.btn = boton;
        
        this.setLayout(new MigLayout("","25[]40[]60[]25","10[]10"));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        WebLabel lblIcono = 
                new WebLabel(new ImageIcon(getClass().getResource(rutaIcono)));
        WebLabel lblCadena = new WebLabel(StyleId.label,cadena);
        
        lblCadena.setFont(new Font("Arial",1,18));
        
        this.add(lblIcono,"");
        this.add(lblCadena,"w :150:,growx");
        this.add(btn);
    }

}
