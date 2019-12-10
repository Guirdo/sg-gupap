package org.adsoftware.modulopago.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.spinner.WebSpinner;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;
import net.miginfocom.swing.MigLayout;

public class DMRegistrarPago extends JDialog{
    
    public WebLabel lblNombre, lblHorario,lblSemCurso,lblSemPagadas;
    public WebRadioButton rbSemanal,rbInscripcion,rbReinscripcion;
    public WebSpinner spnSemanas;
    public WebLabel tfMonto;
    public WebButton btnRegistrar;
    
    public DMRegistrarPago(){
        this.setModal(true);
        this.setTitle("Registrar pago");
        this.setLayout(new MigLayout("wrap 2","15[]25[]15","20[]15[]15[]15[]20[]15[]15[]15[]15[]20"));
        
        lblNombre = new WebLabel("Guillermina Santigo Fernandez");
        lblHorario = new WebLabel("L M Mi J 15:00 - 16:00");
        lblSemCurso = new WebLabel("1");
        lblSemPagadas = new WebLabel("0");
        rbSemanal = new WebRadioButton("Semanal");
        rbInscripcion = new WebRadioButton("Inscripción");
        rbReinscripcion = new WebRadioButton("Reinscripción");
        spnSemanas = new WebSpinner(new SpinnerNumberModel(1,1,5,1));
        tfMonto = new WebLabel();
        btnRegistrar = new WebButton("Registrar");
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSemanal);
        bg.add(rbInscripcion);
        bg.add(rbReinscripcion);
        
        rbSemanal.setSelected(true);
        
        this.add(new WebLabel("Nombre: "));
        this.add(lblNombre);
        this.add(new WebLabel("Horario: "));
        this.add(lblHorario);
        this.add(new WebLabel("Semanas del curso: "));
        this.add(lblSemCurso);
        this.add(new WebLabel("Semanas pagadas: "));
        this.add(lblSemPagadas);
        
        this.add(new WebLabel("Concepto: "),"span 1 3,top");
        this.add(rbSemanal,"split 2");
        this.add(spnSemanas);
        this.add(rbInscripcion);
        this.add(rbReinscripcion);
        
        this.add(new WebLabel("Monto ($): "));
        this.add(tfMonto,"right");
        
        this.add(btnRegistrar,"span 2,right");
        
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
