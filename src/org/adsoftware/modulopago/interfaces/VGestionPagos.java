package org.adsoftware.modulopago.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class VGestionPagos extends JPanel{
    
    public WebTextField tfBuscar;
    public JTable tabla, historial;
    public WebLabel lblNombre,lblNumero,lblCurso,lblHorario;
    public WebButton btnRegistrarPago;
    public JPanel pnlDatos;
    
    public VGestionPagos(){
        this.setLayout(new MigLayout("wrap","30[]30","20[]20[]25[]30"));
        
        WebLabel titulo = new WebLabel("Gestión de pagos");
        WebLabel subTitulo = new WebLabel("Datos del alumno");
        tfBuscar = new WebTextField(18);
        tabla = new JTable();
        historial = new JTable();
        lblNombre = new WebLabel("Guillermo Melendez Francisco");
        lblNumero = new WebLabel("7471717171");
        lblCurso = new WebLabel("InglesA1");
        lblHorario = new WebLabel("L M Mi J 14:00 - 15:00");
        btnRegistrarPago = new WebButton("Registrar pago");
        pnlDatos = new JPanel(new MigLayout("wrap 2","20[]70[]0","20[]15[]15[]15[]20[]0"));
        
        tfBuscar.setInputPrompt("Buscar por matrícula");
        titulo.setFont(new Font("Arail",1,20));
        subTitulo.setFont(new Font("Arail",1,20));
        
        pnlDatos.add(subTitulo,"north");
        pnlDatos.add(lblNombre);
        pnlDatos.add(new WebLabel("Historial de pago"));
        pnlDatos.add(lblNumero);
        pnlDatos.add(new JScrollPane(historial),"w 300,h 200,span 1 4");
        pnlDatos.add(lblCurso);
        pnlDatos.add(lblHorario);
        pnlDatos.add(btnRegistrarPago);
        
        pnlDatos.setVisible(false);
        
        this.add(titulo,"north, gapleft 30");
        this.add(tfBuscar,"right");
        this.add(new JScrollPane(tabla),"w 500,h 250");
        this.add(pnlDatos);
        
    }
    

}
