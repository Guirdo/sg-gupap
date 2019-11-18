package org.adsoftware.modulopersonal.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.utilidades.Galeria;

public class VRegistroES extends JPanel{
    
    public WebTable tabla;
    public WebLabel lblIcono,lblNombre,lblCargo;
    public WebTextField tfClave;
    public WebButton btnEntrada,btnSalida;
    
    public VRegistroES(){
        this.setLayout(new MigLayout("wrap 2","30[]25[]30","30[]25[]15[]15[]20[]15[]30::"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Registro Entrada/Salida del personal");
        
        tabla = new WebTable(StyleId.table,new DefaultTableModel(0, 3));
        lblIcono = new WebLabel(StyleId.label,new ImageIcon(getClass().getResource(Galeria.FEMALE_ICON)));
        lblNombre = new WebLabel(StyleId.label,"Paloma Ramirez");
        lblCargo = new WebLabel(StyleId.label,"Maestro Ingles");
        tfClave = new WebTextField(StyleId.textfield,15);
        btnEntrada = new WebButton(StyleId.button,"Entrada");
        btnSalida = new WebButton(StyleId.button,"Salida");
        
        lblTitulo.setFont(new Font("Arial",1,20));
        tfClave.setInputPrompt("Clave empleado");
        lblNombre.setFont(new Font("Arial",1,14));
        lblCargo.setFont(new Font("Arial",1,14));
        lblNombre.setHorizontalAlignment(WebLabel.CENTER);
        lblCargo.setHorizontalAlignment(WebLabel.CENTER);
        btnEntrada.setBackground(Color.decode("#008f39"));
        btnSalida.setBackground(Color.decode("#cb3234"));
        
        this.add(lblTitulo,"north, gapleft 30");
        this.add(new JScrollPane(tabla),"h 300, w :350:,growx,span 1 6");
        this.add(lblIcono,"w 64, h 64,center");
        this.add(lblNombre);
        this.add(lblCargo);
        this.add(tfClave);
        this.add(btnEntrada,"split 2");
        this.add(btnSalida,"gapleft 15");
    }

}
