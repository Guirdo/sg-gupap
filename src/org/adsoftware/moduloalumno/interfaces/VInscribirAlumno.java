package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.text.WebTextField;
import com.alee.extended.date.WebDateField;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class VInscribirAlumno extends JPanel {
    
     public WebTextField tfNombreA, tfApellidoPatA, tfApellidoMatA, tfDomicilioA, tfTelefono;
     public JRadioButton generoM, generoF;
     public WebDateField fechaN;
     public WebTable tabla;
     public JLabel genero, h;
     public JButton registrar;

    public VInscribirAlumno() {
        
        this.setLayout(new MigLayout("wrap 3", "30[]10[]10[]30", "30[]15[]15[]15[]15[]15[]15[]15[]50[]10"));
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Inscribir alumno");
        lblTitulo.setFont(new Font("Arial", 0, 20));
        
        tfNombreA = new WebTextField(StyleId.textfield,20);
        tfNombreA.setInputPrompt("Nombre (s)");
        tfNombreA.setFont(new Font("Arial",0,16));
        tfApellidoPatA = new WebTextField(StyleId.textfield,20);
        tfApellidoPatA.setInputPrompt("Apellido paterno");
        tfApellidoPatA.setFont(new Font("Arial",0,16));
        tfApellidoMatA = new WebTextField(StyleId.textfield,20);
        tfApellidoMatA.setInputPrompt("Apellido materno");
        tfApellidoMatA.setFont(new Font("Arial",0,16));
        fechaN = new WebDateField();
        genero = new JLabel("Género: ");
        genero.setFont(new Font("Arial", 0,16));
        generoM = new JRadioButton("Masculino");
        generoF = new JRadioButton("Femenino");
        generoM.setSelected(true);
        tfDomicilioA = new WebTextField(StyleId.textfield,40);
        tfDomicilioA.setInputPrompt("Domicilio");
        tfDomicilioA.setFont(new Font("Arial",0,16));
        tfTelefono = new WebTextField(StyleId.textfield,25);
        tfTelefono.setInputPrompt("Número de teléfono");
        tfTelefono.setFont(new Font("Arial",0,16));
        h = new JLabel("Grupo: ");
        tabla = new WebTable();
        
        registrar = new JButton("Registrar");
        
        ButtonGroup group = new ButtonGroup();
        group.add(generoM);
        group.add(generoF);
        
        this.add(lblTitulo,"north,gapleft 30");
        this.add(tfNombreA,"span 2, wrap");
        this.add(tfApellidoPatA, "span 2, wrap");
        this.add(tfApellidoMatA, "span 2, wrap");
        this.add(new WebLabel(StyleId.label,"Fecha de nacimiento:"));
        this.add(fechaN, "wrap");
        this.add(genero,"");
        this.add(generoM, "split 2");
        this.add(generoF,"wrap");
        this.add(tfDomicilioA, "span 3");
        this.add(tfTelefono, "span 2, wrap");
        this.add(h,"");
        this.add(new JScrollPane(tabla),"h 250,w 450");
        this.add(registrar);
        
        
        
    }
     
     
     
     
    
}
