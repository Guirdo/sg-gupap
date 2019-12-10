package org.adsoftware.modulopago.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Pago;
import org.adsoftware.modulopago.interfaces.DMRegistrarPago;
import org.adsoftware.superclases.Manejador;

public class ManejadorRegistrarPago extends Manejador implements ActionListener, ChangeListener {

    private DMRegistrarPago vista;

    private Alumno alumno;
    private Grupo grupo;
    private Pago pago;
    private double monto;
    private String concepto;
    private int semanas;

    public ManejadorRegistrarPago(Alumno alumno, Grupo grupo, String horario) {
        this.alumno = alumno;
        this.grupo = grupo;
        vista = new DMRegistrarPago();
        monto = 100;
        semanas = 1;
        concepto = Pago.SEMANAL;

        vista.btnRegistrar.addActionListener(this);
        vista.rbSemanal.addActionListener(this);
        vista.rbInscripcion.addActionListener(this);
        vista.rbReinscripcion.addActionListener(this);
        vista.spnSemanas.addChangeListener(this);
        vista.tfMonto.setText(monto+"");

        llenarCampos(horario);

        vista.setVisible(true);
    }

    private void llenarCampos(String horario) {
        vista.lblNombre.setText(alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA);
        vista.lblHorario.setText(horario);
        vista.lblSemCurso.setText(contarSemanas() + "");
        vista.lblSemPagadas.setText(alumno.numSemanas + "");
    }

    private int contarSemanas() {
        Calendar fechaActual = new GregorianCalendar();
        Calendar fecha = new GregorianCalendar();

        fecha.setTime(grupo.fechaInicio);

        return fechaActual.get(Calendar.WEEK_OF_YEAR) - fecha.get(Calendar.WEEK_OF_YEAR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.rbSemanal) {
            vista.spnSemanas.setEnabled(true);
            monto = semanas *100;
            vista.tfMonto.setText(monto+"");
            concepto = Pago.SEMANAL;
        } else if (e.getSource() == vista.rbInscripcion) {
            vista.spnSemanas.setEnabled(false);
            vista.tfMonto.setText("200.0");
            concepto = Pago.INSCRIPCION;
        } else if (e.getSource() == vista.rbReinscripcion) {
            vista.spnSemanas.setEnabled(false);
            vista.tfMonto.setText("200.0");
            concepto = Pago.REINSCRIPCION;
        } else if (e.getSource() == vista.btnRegistrar) {
            try {
                manejaEventoRegistrar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorRegistrarPago.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        semanas = (int) vista.spnSemanas.getValue();
        monto = semanas * 100;
        vista.tfMonto.setText(monto + "");
    }

    private void manejaEventoRegistrar() throws SQLException {

        java.sql.Date fecha = new java.sql.Date(new Date().getTime());

        pago = new Pago(fecha, concepto, monto, alumno.idAlumno);

        pago.insertar();

        if (concepto.equals(Pago.SEMANAL)) {
            alumno.numSemanas = alumno.numSemanas + semanas;
            alumno.actualizar();
        }
        
        vista.dispose();
    }

}
