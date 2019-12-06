
package org.adsoftware.moduloalumno.interfaces;

import com.alee.extended.date.WebDateField;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;

public class DMModificarAlumno extends JDialog {
    
    public WebTextField tfNombreA, tfApellidoPatA, tfApellidoMatA, tfDomicilioA, tfTelefono;
    public JRadioButton generoM, generoF;
    public WebDateField fechaN;
    public JLabel genero;
    public JButton confirmar;

    public DMModificarAlumno() {
        this.setModal(true);
        this.setLayout(new MigLayout("wrap 3", "30[]20[]30", "30[]15[]15[]15[]15[]15[]15[]15[]25[]30"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label, "Modificar alumno");
        lblTitulo.setFont(new Font("Arial", 0, 20));
        
        tfNombreA = new WebTextField(StyleId.textfield,20);
        tfNombreA.setInputPrompt("Nombre (s)");
        tfNombreA.setFont(new Font("Arial",0,16));
        tfNombreA.setEditable(true);
        tfApellidoPatA = new WebTextField(StyleId.textfield,20);
        tfApellidoPatA.setInputPrompt("Apellido paterno");
        tfApellidoPatA.setFont(new Font("Arial",0,16));
        tfApellidoPatA.setEditable(true);
        tfApellidoMatA = new WebTextField(StyleId.textfield,20);
        tfApellidoMatA.setInputPrompt("Apellido materno");
        tfApellidoMatA.setFont(new Font("Arial",0,16));
        tfApellidoMatA.setEditable(true);
        fechaN = new WebDateField();
        genero = new JLabel("Género: ");
        genero.setFont(new Font("Arial", 0,16));
        generoM = new JRadioButton("Masculino");
        generoF = new JRadioButton("Femenino");
        generoM.setSelected(true);
        tfDomicilioA = new WebTextField(StyleId.textfield,40);
        tfDomicilioA.setInputPrompt("Domicilio");
        tfDomicilioA.setFont(new Font("Arial",0,16));
        tfDomicilioA.setEditable(true);
        tfTelefono = new WebTextField(StyleId.textfield,25);
        tfTelefono.setInputPrompt("Número de teléfono");
        tfTelefono.setFont(new Font("Arial",0,16));
        tfTelefono.setEditable(true);
        confirmar = new JButton("Confirmar");        
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
        this.add(confirmar);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    
}
