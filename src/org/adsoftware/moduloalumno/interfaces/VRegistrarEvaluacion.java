package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class VRegistrarEvaluacion extends JPanel{
    
    public WebLabel lblTitulo,lblNombre;
    public WebSpinner spnParcia,tfCalificacion;
    public WebButton btnConfirmar,btnTerminar;
    public JTable tabla;
    public JPanel pnlDatos;
    
    public VRegistrarEvaluacion(){
        this.setLayout(new MigLayout("wrap 2","30[][]30","30[]20[]30[]30"));
        
        lblTitulo = new WebLabel("Registro evaluación - L M Mi J 14:00 - 15:00");
        lblNombre = new WebLabel("Guillermo Melendez Francisco");
        spnParcia = new WebSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        tfCalificacion = new WebSpinner(new SpinnerNumberModel(0, 0, 10, 0.1));
        btnConfirmar = new WebButton("Confirmar");
        btnTerminar = new WebButton("Terminar registro");
        tabla = new JTable(new DefaultTableModel(new String[]{"No. Control","ApePat","ApeMat","Nombres"}, 0));
        pnlDatos = new JPanel(new MigLayout("wrap 2","0[]20[]0","20[]20[]25[]10"));
        WebLabel subTitulo = new WebLabel("Registre la evaluación del alumno");
        
        lblTitulo.setFont(new Font("Arial",1,20));
        subTitulo.setFont(new Font("Arial",1,18));
        lblNombre.setFont(new Font("Arial",1,14));
        
        pnlDatos.add(subTitulo,"north");
        pnlDatos.add(lblNombre,"span 2");
        pnlDatos.add(new WebLabel("Calificación: "),"split 2");
        pnlDatos.add(tfCalificacion,"w 55");
        pnlDatos.add(btnConfirmar);
        
        pnlDatos.setVisible(false);
        
        this.add(lblTitulo,"north, gapleft 30");
        this.add(new WebLabel("Parcial: "),"split 2");
        this.add(spnParcia,"wrap");
        this.add(new JScrollPane(tabla),"h 200, w 450,span 2");
        this.add(pnlDatos);
        this.add(btnTerminar,"center");
    }

}
