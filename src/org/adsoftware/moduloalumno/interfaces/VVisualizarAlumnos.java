package org.adsoftware.moduloalumno.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import org.adsoftware.goodies.TextPrompt;

public class VVisualizarAlumnos extends JPanel{
    
    public WebTable tabla;
    public WebLabel lblNombre, lblTelefono, lblCurso, lblHorario;
    public WebButton btnExpulsar, btnModificar, btnCredencial;
    public JTextField tfBuscar;
    public JPanel datos;

    public VVisualizarAlumnos() {
        this.setLayout(new MigLayout("wrap", "30[]30", "30[]25[]25[]25[]25[]30"));
        WebLabel lblTitulo = new WebLabel(StyleId.label, "Gestión de alumnos");
        lblTitulo.setFont(new Font("Arial", 0, 20));
        tabla = new WebTable();        
        lblNombre = new WebLabel(StyleId.label, "nombre");
        lblNombre.setFont(new Font("Arial", 0, 16));
        lblTelefono = new WebLabel(StyleId.label, "telefono");
        lblTelefono.setFont(new Font("Arial", 0, 16));
        lblCurso = new WebLabel(StyleId.label, "curso");
        lblCurso.setFont(new Font("Arial", 0, 16));
        lblHorario = new WebLabel(StyleId.label, "horario");
        lblHorario.setFont(new Font("Arial", 0, 16));
        tfBuscar = new JTextField(15);
        tfBuscar = new WebTextField(StyleId.textfield,18);
        TextPrompt ph = new TextPrompt("Buscar por matrícula", tfBuscar);
        tfBuscar.setFont(new Font("Arial",0,15));
        
        btnModificar = new WebButton("Modificar");
        btnExpulsar = new WebButton("Expulsar");
        btnCredencial = new WebButton("Credecial");
        
        
        panelDatos();
        this.add(lblTitulo, "north,gapleft 30");
        this.add(tfBuscar,"span 3, right");
        this.add(new JScrollPane(tabla),"h 250,w 450");
        this.add(datos, "growx, pushx");
         
    }
    
    void panelDatos(){
        datos = new JPanel(new MigLayout("wrap 3", "20[]20[]20", "0[]15[]15[]15[]0"));
        datos.add(lblNombre,"cell 0 0");
        datos.add(lblTelefono, "cell 0 1");
        datos.add(lblCurso,"cell 0 2");
        datos.add(lblHorario, "cell 0 3");
        datos.add(btnModificar, "cell 1 0");
        datos.add(btnCredencial, "cell 1 1, wrap");
        datos.add(btnExpulsar, "cell 1 2");
    }
    
    
    
}
