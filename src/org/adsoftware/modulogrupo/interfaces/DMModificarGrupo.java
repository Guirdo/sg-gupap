package org.adsoftware.modulogrupo.interfaces;

import com.alee.extended.date.WebDateField;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import com.alee.managers.style.StyleId;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class DMModificarGrupo extends JDialog{
    
    public WebCheckBox[] chDiasSemana;
    public WebComboBox cbHoraInicio, cbHoraFinal;
    public WebComboBox cbCurso;
    public WebDateField dfInicioCurso;
    public WebTable tablaMaestros;
    public WebButton btnModificar;

    private JPanel pnlDias;
    private JPanel pnlHorario;
    private int conDias = 0;

    public DMModificarGrupo() {
        this.setModal(true);
        this.setLayout(new MigLayout("wrap 3", "30[]20[]20[]30", "30[]15[]15[]15[]15[]25[]30"));

        WebLabel lblTitulo = new WebLabel(StyleId.label, "Registrar grupo");
        chDiasSemana = new WebCheckBox[7];
        cbHoraInicio = new WebComboBox(StyleId.combobox);
        cbHoraFinal = new WebComboBox(StyleId.combobox);
        cbCurso = new WebComboBox(StyleId.combobox);
        dfInicioCurso = new WebDateField(StyleId.datefield);
        tablaMaestros = new WebTable(new Object[][]{{}, {}, {}, {}}, new String[]{"Clave empleado", "Maestro"});
        btnModificar = new WebButton(StyleId.button, "Modificar");
        pnlDias = new JPanel(new MigLayout("", "0[]20[]20[]20[]20[]20[]20[]0", "0[]0"));
        pnlHorario = new JPanel(new MigLayout("", "0[]20[]20[]0", "0[]0"));

        lblTitulo.setFont(new Font("Arial", 1, 20));
        generarDiasSemanas();
        generarHoras();
        generarCursos();

        this.add(lblTitulo, "north,growx");
        this.add(new WebLabel("Dia(s) de la semana: "));
        this.add(pnlDias, "span 2,growx");
        this.add(new WebLabel("Horario: "));
        this.add(pnlHorario, "wrap");
        this.add(new WebLabel("Curso: "));
        this.add(cbCurso, "wrap");
        this.add(new WebLabel("Fecha de inicio: "));
        this.add(dfInicioCurso, "wrap");
        this.add(new WebLabel("Maestro asignado: "));
        this.add(new JScrollPane(tablaMaestros), "span 2,growx");
        this.add(btnModificar, "span 3, right");
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void generarDiasSemanas() {
        chDiasSemana[0] = new WebCheckBox(StyleId.checkbox, "L");
        chDiasSemana[1] = new WebCheckBox(StyleId.checkbox, "M");
        chDiasSemana[2] = new WebCheckBox(StyleId.checkbox, "X");
        chDiasSemana[3] = new WebCheckBox(StyleId.checkbox, "J");
        chDiasSemana[4] = new WebCheckBox(StyleId.checkbox, "V");
        chDiasSemana[5] = new WebCheckBox(StyleId.checkbox, "S");
        chDiasSemana[6] = new WebCheckBox(StyleId.checkbox, "D");

        for (int i = 0; i < chDiasSemana.length; i++) {
            pnlDias.add(chDiasSemana[i]);

        }
    }

    private void generarHoras() {
        for (int i = 8; i < 18; i++) {
            cbHoraInicio.addItem(i + ":00");
            cbHoraInicio.addItem(i + ":30");
            cbHoraFinal.addItem((i + 1) + ":00");
            cbHoraFinal.addItem((i + 1) + ":30");
        }

        pnlHorario.add(cbHoraInicio);
        pnlHorario.add(new WebLabel(" - "));
        pnlHorario.add(cbHoraFinal);
    }

    private void generarCursos() {
        cbCurso.addItem("InglesA1");
        cbCurso.addItem("InglesA2");
        cbCurso.addItem("InglesB1");
        cbCurso.addItem("InglesB2");
    }

}
