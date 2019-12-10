package org.adsoftware.moduloalumno.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Evaluacion;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.moduloalumno.interfaces.VRegistrarEvaluacion;
import org.adsoftware.modulogrupo.manejadores.ManejadorVisualizarGrupos;
import org.adsoftware.superclases.Manejador;

public class ManejadorRegistrarEvaluacion extends Manejador implements ActionListener, ListSelectionListener, ChangeListener {

    private VRegistrarEvaluacion vista;

    private DefaultTableModel modelo;
    private Grupo grupo;
    private Alumno alumno;
    private Evaluacion evaluacion;
    private ArrayList<Alumno> lista;
    private JPanel pnlPrincipal;
    private int alumnoSeleccionado;
    private int parcial;

    public ManejadorRegistrarEvaluacion(JPanel pnl, Grupo grupo) throws SQLException {
        super(pnl);
        this.grupo = grupo;
        this.pnlPrincipal = pnl;
        vista = new VRegistrarEvaluacion();
        modelo = new DefaultTableModel(new Object[]{"Matrícula", "ApellidoPat", "ApellidoMat", "Nombres"}, 0);
        alumnoSeleccionado = -1;
        parcial = 1;

        vista.btnConfirmar.addActionListener(this);
        vista.btnTerminar.addActionListener(this);
        vista.tabla.getSelectionModel().addListSelectionListener(this);
        vista.spnParcia.addChangeListener(this);

        consultarAlumnos();

        repintarPanelPrincipal(vista);
    }

    private void consultarAlumnos() throws SQLException {
        lista = Alumno.buscar("idGrupoA", "" + grupo.idGrupo);

        for (Alumno a : lista) {
            modelo.addRow(new Object[]{a.idAlumno, a.apellidoPatA, a.apellidoMatA, a.nombre});
        }

        vista.tabla.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vista.btnConfirmar) {
                if (alumnoSeleccionado != -1) {
                    manejaEventoConfirmar();
                }
            } else if (e.getSource() == vista.btnTerminar) {
                manejaEventoTerminar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorRegistrarEvaluacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        alumnoSeleccionado = vista.tabla.getSelectedRow();
        try {
            manejaEventoDatosCompletos();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        parcial = (int) vista.spnParcia.getValue();
        try {
            manejaEventoDatosCompletos();
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorRegistrarEvaluacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoDatosCompletos() throws SQLException {
        if (alumnoSeleccionado < modelo.getRowCount()) {
            alumno = lista.get(alumnoSeleccionado);
            evaluacion = Evaluacion.buscarPrimero("parcial", "" + parcial, "idAlumnoE", "" + alumno.idAlumno);

            if (evaluacion == null) {
                evaluacion = new Evaluacion(parcial, 0, alumno.idAlumno);
            }

            vista.lblNombre.setText(alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA);
            vista.tfCalificacion.setValue(evaluacion.calificacion);

            vista.pnlDatos.setVisible(true);
        } else {
            vista.pnlDatos.setVisible(false);
        }
    }

    private void manejaEventoConfirmar() throws SQLException {
        evaluacion.calificacion = (double) vista.tfCalificacion.getValue();
        if (evaluacion.idEvaluacion == -1) {
            evaluacion.insertar();
        } else {
            evaluacion.guardar();
        }

        alumnoSeleccionado++;

        manejaEventoDatosCompletos();
    }

    private void manejaEventoTerminar() throws SQLException {
        new ManejadorVisualizarGrupos(true, pnlPrincipal, Usuario.buscarPrimero("idUsuario", Usuario.COORDINADOR + ""));
    }

}
