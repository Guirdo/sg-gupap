package org.adsoftware.modulogrupo.manejadores;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulogrupo.interfaces.DMModificarGrupo;
import org.adsoftware.superclases.Manejador;

public class ManejadorModificarGrupo extends Manejador implements ActionListener, ListSelectionListener {

    private DMModificarGrupo vista;

    private Grupo grupo;
    private Horario horario;
    private ArrayList<Personal> listaMaestros;
    private DefaultTableModel modelo;
    private int contador = 0, maestroSeleccionado = -1, conDias = 0;
    private String mensajeError = "";

    public ManejadorModificarGrupo(Grupo grupo, Horario horario) throws SQLException {
        this.grupo = grupo;
        this.horario = horario;
        this.vista = new DMModificarGrupo();
        this.modelo = new DefaultTableModel(new Object[]{"Clave empleado", "Maestro"}, 0);

        vista.btnModificar.addActionListener(this);
        vista.tablaMaestros.getSelectionModel().addListSelectionListener(this);
        for (WebCheckBox c : vista.chDiasSemana) {
            c.addActionListener(this);
        }

        consultarMaestros();
        llenarCampos();

        vista.setVisible(true);
    }

    private void consultarMaestros() throws SQLException {
        listaMaestros = Personal.buscar("cargo", Personal.DOCENTE);
        for (Personal p : listaMaestros) {
            modelo.addRow(new Object[]{p.idPersonal, p.apellidoPatP + " " + p.apellidoMatP + " " + p.nombreP});
            contador = horario.idPersonalH == p.idPersonal ? modelo.getRowCount()-1 : 0;
        }

        vista.tablaMaestros.setModel(modelo);
    }

    private void llenarCampos() {
        //Horario
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        vista.chDiasSemana[0].setSelected(horario.lunes);
        vista.chDiasSemana[1].setSelected(horario.martes);
        vista.chDiasSemana[2].setSelected(horario.miercoles);
        vista.chDiasSemana[3].setSelected(horario.jueves);
        vista.chDiasSemana[4].setSelected(horario.viernes);
        vista.chDiasSemana[5].setSelected(horario.sabado);
        vista.chDiasSemana[6].setSelected(horario.domingo);

        vista.cbHoraInicio.setSelectedItem(df.format(horario.horaInicial));
        vista.cbHoraFinal.setSelectedItem(df.format(horario.horaFinal));

        //Grupo
        vista.dfInicioCurso.setDate(grupo.fechaInicio);
        vista.cbCurso.setSelectedItem(grupo.curso);
        vista.tablaMaestros.setSelectedRow(contador);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnModificar) {
            try {
                manejaEventoModificar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorModificarGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            for (int i = 0; i < vista.chDiasSemana.length; i++) {
                if (e.getSource() == vista.chDiasSemana[i]) {
                    if (vista.chDiasSemana[i].isSelected()) {
                        conDias++;
                    } else {
                        if (conDias == 4) {
                            for (WebCheckBox cb : vista.chDiasSemana) {
                                cb.setEnabled(true);
                            }
                        }
                        conDias--;
                    }

                    if (conDias == 4) {
                        for (WebCheckBox cb : vista.chDiasSemana) {
                            if (!cb.isSelected()) {
                                cb.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void contarDias() {
        conDias = 0;
        for (int i = 0; i < vista.chDiasSemana.length; i++) {
            if (vista.chDiasSemana[i].isSelected()) {
                conDias++;
            }
        }
    }

    private void manejaEventoModificar() throws SQLException {
        mensajeError = "";
        if (verificarCampos()) {

            horario.actualizar();

            java.sql.Date fecha = new java.sql.Date(vista.dfInicioCurso.getDate().getTime());

            grupo.fechaInicio = fecha;
            grupo.curso = vista.cbCurso.getSelectedItem().toString();

            grupo.actualizar();

            NotificationManager.showNotification(vista.btnModificar,
                    "Grupo registrado con éxito", NotificationIcon.plus.getIcon());

            vista.dispose();
        } else {
            NotificationManager.showNotification(vista.btnModificar,
                    "Error al ingresar un dato: " + mensajeError, NotificationIcon.warning.getIcon());
        }
    }

    private boolean verificarCampos() throws SQLException {
        boolean fechaCorrecta = verificarFecha();
        boolean maestroCorreto = verificarMaestro();

        return fechaCorrecta && maestroCorreto;
    }

    private boolean verificarHorario(Time horaInicial, Time horaFinal) {
        contarDias();
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
        if (vista.dfInicioCurso.getDate() == null) {
            mensajeError += "\n- Fecha nula.";
            return false;
        } else {
            Calendar fechaActual = new GregorianCalendar();
            Calendar fechaInicio = new GregorianCalendar();
            fechaInicio.setTime(vista.dfInicioCurso.getDate());

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
            Time horaInicial = Time.valueOf(vista.cbHoraInicio.getSelectedItem().toString() + ":00");
            Time horaFinal = Time.valueOf(vista.cbHoraFinal.getSelectedItem().toString() + ":00");
            if (verificarHorario(horaInicial, horaFinal)) {
                if (!verificarTraslape(horaInicial, horaFinal)) {
                    horario.horaInicial = horaInicial;
                    horario.horaFinal = horaFinal;
                    horario.lunes = vista.chDiasSemana[0].isSelected();
                    horario.martes = vista.chDiasSemana[1].isSelected();
                    horario.miercoles = vista.chDiasSemana[2].isSelected();
                    horario.jueves = vista.chDiasSemana[3].isSelected();
                    horario.viernes = vista.chDiasSemana[4].isSelected();
                    horario.sabado = vista.chDiasSemana[5].isSelected();
                    horario.domingo = vista.chDiasSemana[6].isSelected();
                    horario.idPersonalH = maestroSeleccionado;

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
            if (h.lunes == vista.chDiasSemana[0].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.martes == vista.chDiasSemana[1].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.miercoles == vista.chDiasSemana[2].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.jueves == vista.chDiasSemana[3].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.viernes == vista.chDiasSemana[4].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.sabado == vista.chDiasSemana[5].isSelected()) {
                if (!compararHoras(horaIni.getTime(), h.horaInicial.getTime(), h.horaFinal.getTime())) {
                    return true;
                }
            } else if (h.domingo == vista.chDiasSemana[6].isSelected()) {
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
        maestroSeleccionado = (int) modelo.getValueAt(vista.tablaMaestros.getSelectedRow(), 0);
    }

}
