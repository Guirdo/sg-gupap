package org.adsoftware.modulogrupo.manejadores;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulogrupo.interfaces.VRegistroGrupo;
import org.adsoftware.superclases.Manejador;

public class ManejadorRegistrarGrupo extends Manejador implements ActionListener, ListSelectionListener {

    private VRegistroGrupo vistaRegistrar;
    private JPanel pnlPrincipal;

    private ArrayList<Personal> listaMaestros;
    private DefaultTableModel modelo;
    private Grupo grupoNuevo;
    private int maestroSeleccionado = -1;
    private int conDias = 0;

    public ManejadorRegistrarGrupo(JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        this.pnlPrincipal = panelPrincipal;
        vistaRegistrar = new VRegistroGrupo();
        modelo = new DefaultTableModel(new Object[]{"Clave empleado", "Maestro"}, 0);

        vistaRegistrar.btnRegistrar.addActionListener(this);
        vistaRegistrar.tablaMaestros.getSelectionModel().addListSelectionListener(this);
        for (WebCheckBox c : vistaRegistrar.chDiasSemana) {
            c.addActionListener(this);
        }

        consultarMaestros();

        repintarPanelPrincipal(vistaRegistrar);

    }

    private void consultarMaestros() throws SQLException {
        listaMaestros = Personal.buscar("cargo", Personal.DOCENTE);
        for (Personal p : listaMaestros) {
            modelo.addRow(new Object[]{p.idPersonal, p.apellidoPatP + " " + p.apellidoMatP + " " + p.nombreP});
        }

        vistaRegistrar.tablaMaestros.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRegistrar.btnRegistrar) {
            try {
                manejaEventoRegistrarGrupo();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorRegistrarGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (int i = 0; i < vistaRegistrar.chDiasSemana.length; i++) {
                if (e.getSource() == vistaRegistrar.chDiasSemana[i]) {
                    if (vistaRegistrar.chDiasSemana[i].isSelected()) {
                        conDias++;
                    } else {
                        if (conDias == 4) {
                            for (WebCheckBox cb : vistaRegistrar.chDiasSemana) {
                                cb.setEnabled(true);
                            }
                        }
                        conDias--;
                    }

                    if (conDias == 4) {
                        for (WebCheckBox cb : vistaRegistrar.chDiasSemana) {
                            if (!cb.isSelected()) {
                                cb.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void manejaEventoRegistrarGrupo() throws SQLException {
        String diasSemana, horario, curso;
        if (conDias > 0 && verificarHorario() && vistaRegistrar.dfInicioCurso.getDate() != null && maestroSeleccionado != -1) {
            //Dias de la semana
            diasSemana = "";

            for (WebCheckBox c : vistaRegistrar.chDiasSemana) {
                if (c.isSelected()) {
                    if (diasSemana.isEmpty()) {
                        diasSemana += c.getText();
                    } else {
                        diasSemana += "-" + c.getText();
                    }
                }
            }

            //Horario
            horario = vistaRegistrar.cbHoraInicio.getSelectedItem().toString() + "-" + vistaRegistrar.cbHoraFinal.getSelectedItem().toString();

            //Curso
            curso = vistaRegistrar.cbCurso.getSelectedItem().toString();

            //Fecha inicio
            java.sql.Date fecha = new java.sql.Date(vistaRegistrar.dfInicioCurso.getDate().getTime());

            grupoNuevo = new Grupo(diasSemana, horario, curso, fecha, maestroSeleccionado);
            grupoNuevo.insertar();

            NotificationManager.showNotification(vistaRegistrar.btnRegistrar,
                    "Grupo registrado con éxito", NotificationIcon.warning.getIcon());
            new ManejadorVisualizarGrupos(false,pnlPrincipal);
        } else {
            NotificationManager.showNotification(vistaRegistrar.btnRegistrar,
                    "Error al ingresar un dato", NotificationIcon.warning.getIcon());
        }
    }

    private boolean verificarHorario() {
        String horaInicio = vistaRegistrar.cbHoraInicio.getSelectedItem().toString();
        String horaFinal = vistaRegistrar.cbHoraFinal.getSelectedItem().toString();

        String[] hoI = horaInicio.split(":");
        String[] hoF = horaFinal.split(":");

        String horarioSeleccionado;

        return Integer.parseInt(hoF[0]) > Integer.parseInt(hoI[0]);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        maestroSeleccionado = (int) modelo.getValueAt(vistaRegistrar.tablaMaestros.getSelectedRow(), 0);
    }

}
