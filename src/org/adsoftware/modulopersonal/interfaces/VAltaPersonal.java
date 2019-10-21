
package org.adsoftware.modulopersonal.interfaces;
import com.alee.laf.text.WebTextField;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.entidades.Personal;

public class VAltaPersonal extends JPanel {
    
     public WebTextField tfNombreP, tfApellidoPatP, tfApellidoMatP, tfDomicilioP, tfCorreoP;
     public WebComboBox cmbCargo;
     public JRadioButton generoM, generoF;
     public JLabel genero, cargo;
     public Personal cargos;
     
    public VAltaPersonal() {
        this.setLayout(new MigLayout("wrap 1", "10[]10", "10[]15[15]15[]15[]15[]15[]15[]15[]10"));
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
        
        cmbCargo = new WebComboBox();
        
        
        ButtonGroup group = new ButtonGroup();
        group.add(generoM);
        group.add(generoF);
         
        this.add(lblTitulo,"north,gapleft 30");
        this.add(tfNombreP,"");
        this.add(tfApellidoPatP, "");
        this.add(tfApellidoMatP, "");
        this.add(tfDomicilioP, "");
        this.add(tfCorreoP, "");
        this.add(genero,"");
        this.add(generoM, "cell 0 5");
        this.add(generoF,"cell 0 5");
        this.add(cargo, "");
        this.add(cmbCargo, "cell 0 6");
    }
     
     
    
}

