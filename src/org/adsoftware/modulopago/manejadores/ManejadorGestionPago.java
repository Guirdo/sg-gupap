package org.adsoftware.modulopago.manejadores;

import org.adsoftware.entidades.Pago;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.moduloalumno.manejadores.ManejadorVisualizarAlumno;
import org.adsoftware.modulopago.interfaces.DMRegistrarPago;
import org.adsoftware.modulopago.interfaces.VGestionPagos;
import org.adsoftware.superclases.Manejador;

public class ManejadorGestionPago extends Manejador implements ActionListener, ListSelectionListener {

    private VGestionPagos vista;

    private DefaultTableModel modelo, modeloHistoria;
    private Alumno alumno;
    private Grupo grupo;
    private Horario horario;
    private ArrayList<Alumno> listaAlumnos;
    private ArrayList<Pago> historial;
    private int alumnoSeleccionado;

    public ManejadorGestionPago(JPanel pnl) throws SQLException {
        super(pnl);
        vista = new VGestionPagos();
        modelo = new DefaultTableModel(new Object[]{"No. Matricula", "ApePat", "ApeMat", "Nombres"}, 0);
        modeloHistoria = new DefaultTableModel(new Object[]{"Folio", "Fecha", "Tipo", "($)"}, 0);

        vista.btnRegistrarPago.addActionListener(this);
        vista.tabla.getSelectionModel().addListSelectionListener(this);

        consultarAlumnos();

        repintarPanelPrincipal(vista);
    }

    private void consultarAlumnos() throws SQLException {
        listaAlumnos = Alumno.todos();

        for (Alumno a : listaAlumnos) {
            modelo.addRow(new Object[]{a.idAlumno, a.apellidoPatA, a.apellidoMatA, a.nombre});
        }
        vista.tabla.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrarPago) {
            try {
                manejaEventoRegistrarPago();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorGestionPago.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        alumnoSeleccionado = (int) modelo.getValueAt(vista.tabla.getSelectedRow(), 0);
        try {
            if(e.getValueIsAdjusting()){
                while(modeloHistoria.getRowCount()>0) modeloHistoria.removeRow(0);
                manejaEventoDatosCompletos();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoDatosCompletos() throws SQLException {
        alumno = Alumno.buscarPrimero("idAlumno", alumnoSeleccionado + "");
        grupo = Grupo.buscarPrimero("idGrupo", "" + alumno.idGrupoA);
        horario = Horario.buscarPrimero("idHorario", grupo.idHorarioG);

        consultarHistorial();

        vista.lblNombre.setText(alumno.nombre);
        vista.lblNumero.setText(alumno.telefono);
        vista.lblCurso.setText(grupo.curso);
        vista.lblHorario.setText(darHorario());

        vista.pnlDatos.setVisible(true);
    }

    private String darHorario() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String horarioString = "";

        horarioString += horario.lunes ? "L " : "";
        horarioString += horario.martes ? "M " : "";
        horarioString += horario.miercoles ? "Mi " : "";
        horarioString += horario.jueves ? "J " : "";
        horarioString += horario.viernes ? "V " : "";
        horarioString += horario.sabado ? "S " : "";
        horarioString += horario.domingo ? "D " : "";

        horarioString += df.format(horario.horaInicial) + " - " + df.format(horario.horaFinal);

        return horarioString;
    }

    private void consultarHistorial() throws SQLException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        historial = Pago.buscar("idAlumnoP", alumno.idAlumno + "");

        for (Pago p : historial) {
            modeloHistoria.addRow(new Object[]{p.idPago, df.format(p.fecha), p.concepto, p.monto});
        }

        vista.historial.setModel(modeloHistoria);
    }

    private void manejaEventoRegistrarPago() throws SQLException {
        new ManejadorRegistrarPago(alumno, grupo, darHorario());
        consultarHistorial();
    }

}
