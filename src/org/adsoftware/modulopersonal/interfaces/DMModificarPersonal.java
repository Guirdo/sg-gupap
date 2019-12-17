package org.adsoftware.modulopersonal.interfaces;

import com.alee.extended.date.WebDateField;
import com.alee.laf.combobox.WebComboBox;
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
import org.adsoftware.entidades.Personal;

public class DMModificarPersonal extends JDialog {

    public WebTextField tfNombreP, tfApellidoPatP, tfApellidoMatP, tfDomicilioP, tfCorreoP;
    public WebComboBox cmbCargo;
    public JRadioButton generoM, generoF;
    public JLabel genero, cargo;
    public Personal cargos;
    public JButton btnModificar;
    public WebDateField fechaN;

    public DMModificarPersonal() {
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new MigLayout("wrap 3", "25[]10[]10[]25", "25[]15[]15[]15[]15[]15[]15[]15[]50[]25"));
        WebLabel lblTitulo = new WebLabel(StyleId.label, "Modificar personal");
        lblTitulo.setFont(new Font("Arial", 0, 20));

        tfNombreP = new WebTextField(StyleId.textfield, 20);
        tfNombreP.setInputPrompt("Nombre (s)");
        tfNombreP.setFont(new Font("Arial", 0, 16));
        tfApellidoPatP = new WebTextField(StyleId.textfield, 20);
        tfApellidoPatP.setInputPrompt("Apellido paterno");
        tfApellidoPatP.setFont(new Font("Arial", 0, 16));
        tfApellidoMatP = new WebTextField(StyleId.textfield, 20);
        tfApellidoMatP.setInputPrompt("Apellido materno");
        tfApellidoMatP.setFont(new Font("Arial", 0, 16));
        tfDomicilioP = new WebTextField(StyleId.textfield, 40);
        tfDomicilioP.setInputPrompt("Domicilio");
        tfDomicilioP.setFont(new Font("Arial", 0, 16));
        tfCorreoP = new WebTextField(StyleId.textfield, 25);
        tfCorreoP.setInputPrompt("Correo electrónico");
        tfCorreoP.setFont(new Font("Arial", 0, 16));
        genero = new JLabel("Género: ");
        genero.setFont(new Font("Arial", 0, 16));
        generoM = new JRadioButton("Masculino");
        generoF = new JRadioButton("Femenino");
        generoM.setSelected(true);
        cargo = new JLabel("Cargo: ");
        cargo.setFont(new Font("Arial", 0, 16));
        fechaN = new WebDateField();

        cmbCargo = new WebComboBox();
        cargos();
        btnModificar = new JButton("Confirmar modificación");

        ButtonGroup group = new ButtonGroup();
        group.add(generoM);
        group.add(generoF);

        this.add(lblTitulo, "north,gapleft 30");
        this.add(tfNombreP, "span 2, wrap");
        this.add(tfApellidoPatP, "span 2, wrap");
        this.add(tfApellidoMatP, "span 2, wrap");
        this.add(new WebLabel(StyleId.label, "Fecha de nacimiento:"));
        this.add(fechaN, "wrap");
        this.add(tfDomicilioP, "span 3,growx");
        this.add(tfCorreoP, "span 2, wrap");
        this.add(genero, "");
        this.add(generoM, "split 2");
        this.add(generoF, "wrap,gapleft 20");
        this.add(cargo, "");
        this.add(cmbCargo, "wrap");
        this.add(btnModificar, "span 2, growx");
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void cargos() {
        cmbCargo.addItem(Personal.DOCENTE);
        cmbCargo.addItem(Personal.CONTADOR);
        cmbCargo.addItem(Personal.RECEPCIONISTA);
    }

}
