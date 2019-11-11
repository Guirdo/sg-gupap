
package org.adsoftware.modulopersonal.interfaces;
import com.alee.laf.text.WebTextField;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;
import com.alee.extended.date.WebDateField;
import org.adsoftware.entidades.Personal;

public class VAltaPersonal extends JPanel {
    
     public WebTextField tfNombreP, tfApellidoPatP, tfApellidoMatP, tfDomicilioP, tfCorreoP;
     public WebComboBox cmbCargo;
     public JRadioButton generoM, generoF;
     public JLabel genero, cargo;
     public Personal cargos;
     public JButton registrar;
     public WebDateField fechaN;
     
    public VAltaPersonal() {
        this.setLayout(new MigLayout("wrap 3", "30[]10[]10[]30", "30[]15[]15[]15[]15[]15[]15[]15[]50[]10"));
        WebLabel lblTitulo = new WebLabel(StyleId.label,"Dar de alta personal");
        lblTitulo.setFont(new Font("Arial", 0, 20));
        
        tfNombreP = new WebTextField(StyleId.textfield,20);
        tfNombreP.setInputPrompt("Nombre (s)");
        tfNombreP.setFont(new Font("Arial",0,16));
        tfApellidoPatP = new WebTextField(StyleId.textfield,20);
        tfApellidoPatP.setInputPrompt("Apellido paterno");
        tfApellidoPatP.setFont(new Font("Arial",0,16));
        tfApellidoMatP = new WebTextField(StyleId.textfield,20);
        tfApellidoMatP.setInputPrompt("Apellido materno");
        tfApellidoMatP.setFont(new Font("Arial",0,16));
        tfDomicilioP = new WebTextField(StyleId.textfield,40);
        tfDomicilioP.setInputPrompt("Domicilio");
        tfDomicilioP.setFont(new Font("Arial",0,16));
        tfCorreoP = new WebTextField(StyleId.textfield,25);
        tfCorreoP.setInputPrompt("Correo electrónico");
        tfCorreoP.setFont(new Font("Arial",0,16));
        genero = new JLabel("Género: ");
        genero.setFont(new Font("Arial", 0,16));
        generoM = new JRadioButton("Masculino");
        generoF = new JRadioButton("Femenino");
        generoM.setSelected(true);
        cargo = new JLabel("Cargo: ");
        cargo.setFont(new Font("Arial", 0,16));
        fechaN = new WebDateField();
        
        cmbCargo = new WebComboBox();
        cargos();
        registrar = new JButton("Registrar");
        
        ButtonGroup group = new ButtonGroup();
        group.add(generoM);
        group.add(generoF);
                 
        this.add(lblTitulo,"north,gapleft 30");
        this.add(tfNombreP,"span 2, wrap");
        this.add(tfApellidoPatP, "span 2, wrap");
        this.add(tfApellidoMatP, "span 2, wrap");
        this.add(new WebLabel(StyleId.label,"Fecha de nacimiento:"));
        this.add(fechaN, "wrap");
        this.add(tfDomicilioP, "span 3");
        this.add(tfCorreoP, "span 2, wrap");
        this.add(genero,"");
        this.add(generoM, "split 2");
        this.add(generoF,"wrap");
        this.add(cargo, "");
        this.add(cmbCargo, "wrap");
        this.add(registrar,"span 2, growx");
    }
     
    private void cargos(){
        cmbCargo.addItem(Personal.DOCENTE);
        cmbCargo.addItem(Personal.CONTADOR);
        cmbCargo.addItem(Personal.COORDINADOR);
        cmbCargo.addItem(Personal.ADMINISTRADOR);
    } 
    
}

