package org.adsoftware.modulogrupo.interfaces;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class VVisualizarGrupos extends JPanel {

    public WebTable tabla;
    public WebLabel lblCurso, lblHorario, lblMaestro, lblIcono;
    public WebButton btnRegistrarEva, btnModificar, btnImprimir;
    public JPanel datos;

    public VVisualizarGrupos(boolean esCoordinador) {
        this.setLayout(new MigLayout("wrap", "30[]30", "30[]25[]30"));

        WebLabel lblTitulo = new WebLabel(StyleId.label, "Visualizar grupos");
        tabla = new WebTable();
        lblCurso = new WebLabel(StyleId.label);
        lblHorario = new WebLabel(StyleId.label);
        lblMaestro = new WebLabel(StyleId.label);
        lblIcono = new WebLabel(StyleId.label);
        btnRegistrarEva = new WebButton(StyleId.button, "Registrar evaluación");
        btnModificar = new WebButton(StyleId.button, "Modificar");
        btnImprimir = new WebButton(StyleId.button, "Imprimir lista");

        lblTitulo.setFont(new Font("Arial", 1, 20));
        if (esCoordinador) {
            btnModificar.setVisible(false);
        } else {
            btnRegistrarEva.setVisible(false);
        }

        panelGrupo();

        this.add(lblTitulo, "north, gapleft 30");
        this.add(new JScrollPane(tabla), "h 250,w 450");
        this.add(datos, "");
    }

    private void panelGrupo() {
        datos = new JPanel(new MigLayout("wrap 3", "0[]25[]30[]0", "0[]15[]15[]0"));

        datos.add(lblIcono, "span 1 3, w 128,h 128");
        datos.add(new WebLabel("Curso: "), "split 2");
        datos.add(lblCurso);
        datos.add(btnRegistrarEva);
        datos.add(new WebLabel("Horario: "), "split 2");
        datos.add(lblHorario);
        datos.add(btnModificar);
        datos.add(new WebLabel("Maestro: "), "split 2");
        datos.add(lblMaestro);
        datos.add(btnImprimir);
    }

}
