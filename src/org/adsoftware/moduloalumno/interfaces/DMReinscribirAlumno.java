package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class DMReinscribirAlumno extends JDialog {
     public WebLabel icono, nombre, telefono, direccion, curso, horario, mensajeSeleccione;
     public WebTable tabla;
     public JButton reinscribir;

    public DMReinscribirAlumno() {
        this.setModal(true);
        this.setLayout(new MigLayout("wrap 3", "30[]20[]30", "30[]15[]15[]15[]15[]15[]15[]15[]15[]25[]30"));
        
        WebLabel lblTitulo = new WebLabel(StyleId.label, "Reinscribir alumno");
        lblTitulo.setFont(new Font("Arial", 0, 20));
        
        icono = new WebLabel(StyleId.label, "");
        icono.setFont(new Font("Arial", 0, 16));
        nombre = new WebLabel(StyleId.label, "nombre");
        nombre.setFont(new Font("Arial", 0, 16));
        telefono = new WebLabel(StyleId.label, "telefono");
        telefono.setFont(new Font("Arial", 0, 16));
        direccion = new WebLabel(StyleId.label, "direccion");
        direccion.setFont(new Font("Arial", 0, 16));
        curso = new WebLabel(StyleId.label, "InglesB1");
        curso.setFont(new Font("Arial", 0, 16));
        horario = new WebLabel(StyleId.label, "L M Mi J 08:00 - 09:00");
        horario.setFont(new Font("Arial", 0, 16));
        mensajeSeleccione = new WebLabel(StyleId.label, "Seleccione el nuevo grupo del alumno: ");
        mensajeSeleccione.setFont(new Font("Arial", 0, 16));
        tabla = new WebTable();
        reinscribir = new JButton("Reinscribir");
        
        this.add(lblTitulo,"north,gapleft 30");
        this.add(icono,"span 0 5, h 128, w 128");
        this.add(nombre, "cell 1 1");
        this.add(telefono, "cell 1 2");
        this.add(direccion, "cell 1 3");
        this.add(curso, "cell 1 4");
        this.add(horario,"cell 1 5, wrap");
        this.add(mensajeSeleccione, "span");
        this.add(new JScrollPane(tabla),"span,h 250,w 450");
        this.add(reinscribir, " cell 1 8");
        
        this.pack();
        this.setLocationRelativeTo(null);
        
        
    }
     
     
     
    
}
