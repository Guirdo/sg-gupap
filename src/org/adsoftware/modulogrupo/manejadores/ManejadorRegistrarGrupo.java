package org.adsoftware.modulogrupo.manejadores;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.entidades.Personal;
import org.adsoftware.entidades.Usuario;
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
    private String mensajeError = "";
    private Horario horario;
    private Usuario usuario;

    public ManejadorRegistrarGrupo(JPanel panelPrincipal,Usuario usuario) throws SQLException {
        super(panelPrincipal);
        this.pnlPrincipal = panelPrincipal;
        this.usuario = usuario;
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

    private boolean verificarCampos() throws SQLException {
        boolean fechaCorrecta = verificarFecha();
        boolean maestroCorreto = verificarMaestro();

        return fechaCorrecta && maestroCorreto;
    }

    private void manejaEventoRegistrarGrupo() throws SQLException {
        mensajeError = "";
        NotificationManager.hideAllNotifications();
        if (verificarCampos()) {
            //Horario
            horario.insertar();
            horario = Horario.buscarUltimo();

            //Curso
            String curso = vistaRegistrar.cbCurso.getSelectedItem().toString();

            //Fecha inicio
            java.sql.Date fecha = new java.sql.Date(vistaRegistrar.dfInicioCurso.getDate().getTime());

            grupoNuevo = new Grupo(curso, fecha, horario.idHorario);
            grupoNuevo.insertar();

            NotificationManager.showNotification(vistaRegistrar.btnRegistrar,
                    "Grupo registrado con éxito", NotificationIcon.plus.getIcon());
            new ManejadorVisualizarGrupos(false, pnlPrincipal,usuario);
        } else {
            NotificationManager.showNotification(vistaRegistrar.btnRegistrar,
                    "Error al ingresar un dato: " + mensajeError, NotificationIcon.warning.getIcon());
        }
    }

    private boolean verificarHorario(Time horaInicial, Time horaFinal) {
        if (conDias == 1 && (horaFinal.getTime() - horaInicial.getTime()) == 14400000) {
            return true;
        } else if (conDias == 2 && (horaFinal.getTime() - horaInicial.getTime()) == 7200000) {
            return true;
        } else if (conDias == 3 && (horaFinal.getTime() - horaInicial.getTime()) >= 3600000 && (horaFinal.getTime() - horaInicial.getTime()) < 10800000) {
            return true;
        } else if (conDias == 4 && (horaFinal.getTime() - horaInicial.getTime()) == 3600000) {
            return true;
        } else {
            mensajeError += conDias > 0 ? "\n- Horario incorrecto." : "\n- Días invalidos.";
            return false;
        }
    }

    private boolean verificarFecha() {
        if (vistaRegistrar.dfInicioCurso.getDate() == null) {
            mensajeError += "\n- Fecha nula.";
            return false;
        } else {
            Calendar fechaActual = new GregorianCalendar();
            Calendar fechaInicio = new GregorianCalendar();
            fechaInicio.setTime(vistaRegistrar.dfInicioCurso.getDate());

            int anoInicio = fechaInicio.get(Calendar.YEAR);
            int anoActual = fechaActual.get(Calendar.YEAR);

            if (anoActual == anoInicio) {
                if (fechaInicio.get(Calendar.MONTH) - fechaActual.get(Calendar.MONTH) <= 1) {
                    return true;
                } else {
                    mensajeError += "\n- Fecha incorrecta: solo se permite un mes.";
                    return false;
                }
            } else {
                mensajeError += "\n- Fecha incorrecta: debe ser en el año actual.";
                return false;
            }
        }
    }

    private boolean verificarMaestro() throws SQLException {
        if (maestroSeleccionado != -1) {
            Time horaInicial = Time.valueOf(vistaRegistrar.cbHoraInicio.getSelectedItem().toString() + ":00");
            Time horaFinal = Time.valueOf(vistaRegistrar.cbHoraFinal.getSelectedItem().toString() + ":00");
            if (verificarHorario(horaInicial, horaFinal)) {
                if (!verificarTraslape(horaInicial, horaFinal)) {
                    horario = new Horario(maestroSeleccionado, horaInicial, horaFinal,
                            vistaRegistrar.chDiasSemana[0].isSelected(), vistaRegistrar.chDiasSemana[1].isSelected(),
                            vistaRegistrar.chDiasSemana[2].isSelected(), vistaRegistrar.chDiasSemana[3].isSelected(), vistaRegistrar.chDiasSemana[4].isSelected(),
                            vistaRegistrar.chDiasSemana[5].isSelected(), vistaRegistrar.chDiasSemana[6].isSelected());
                    return true;
                } else {
                    mensajeError += "\n- El maestro tiene traslape.";
                    return false;
                }
            } else {
                return false;
            }
        } else {
            mensajeError += "\n- Maestro no seleccionado.";
            return false;
        }
    }

    private boolean verificarTraslape(Time horaIni, Time horaFin) throws SQLException {
        ArrayList<Horario> lista = Horario.buscar("idPersonalH", maestroSeleccionado);

        for (Horario h : lista) {
            if (h.lunes == vistaRegistrar.chDiasSemana[0].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.martes == vistaRegistrar.chDiasSemana[1].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.miercoles == vistaRegistrar.chDiasSemana[2].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.jueves == vistaRegistrar.chDiasSemana[3].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.viernes == vistaRegistrar.chDiasSemana[4].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.sabado == vistaRegistrar.chDiasSemana[5].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.domingo == vistaRegistrar.chDiasSemana[6].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean compararHoras(long horaIni, long horaIni1, long horaFin1) {
        if (horaIni == horaIni1) {
            return false;
        } else if (horaIni > horaIni1 && horaIni < horaFin1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        maestroSeleccionado = (int) modelo.getValueAt(vistaRegistrar.tablaMaestros.getSelectedRow(), 0);
    }

}
