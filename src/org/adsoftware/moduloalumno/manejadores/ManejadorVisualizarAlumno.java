
package org.adsoftware.moduloalumno.manejadores;

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
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.moduloalumno.interfaces.VVisualizarAlumnos;
import org.adsoftware.superclases.Manejador;

public class ManejadorVisualizarAlumno extends Manejador implements ActionListener, ListSelectionListener{
    public JPanel panelPrincipal;
    public VVisualizarAlumnos pnlVisualizar;
    private DefaultTableModel modelo;
    private ArrayList<Alumno> listaAlumnos;
    int alumnoSeleccionado=0;
    public Grupo grupo;
    public Horario horario;
    public Alumno alumno;
    
    
     public ManejadorVisualizarAlumno(JPanel panelPrin) throws SQLException {
        super(panelPrin);
        this.panelPrincipal = panelPrin;

        modelo = new DefaultTableModel(new Object[]{"Matrícula", "Apellido paterno", "Apellido materno", "Nombre"}, 0);
        pnlVisualizar = new VVisualizarAlumnos();
        pnlVisualizar.tabla.getSelectionModel().addListSelectionListener(this);

        pnlVisualizar.datos.setVisible(false);
//        pnlVisualizar.registrar.addActionListener(this);
        
        consultarAlumnos();
        
        repintarPanelPrincipal(pnlVisualizar);
    }
     
    private void consultarAlumnos() throws SQLException {
        listaAlumnos = Alumno.todos();
        for (Alumno a : listaAlumnos) {
            modelo.addRow(new Object[]{a.idAlumno, a.apellidoPatA, a.apellidoMatA, a.nombre});
        }
        pnlVisualizar.tabla.setModel(modelo);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        alumnoSeleccionado = (int) modelo.getValueAt(pnlVisualizar.tabla.getSelectedRow(), 0);
        try {
            manejaEventoDatosCompletos(alumnoSeleccionado);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoDatosCompletos(int alumnoSeleccionado) throws SQLException {
        Object[][] datos = InterfazBD.consultar("select nombre, apellidoPatA, apellidoMatA, telefono, curso "
                + "from alumno, grupo where idAlumno = "+alumnoSeleccionado+" and idGrupo = idGrupoA;");
        
        System.out.println(alumnoSeleccionado);
        alumno= Alumno.buscarPrimero("idAlumno", "" + alumnoSeleccionado);
        grupo = Grupo.buscarPrimero("idGrupo", "" + alumno.idGrupoA);
        horario = Horario.buscarPrimero("idHorario", grupo.idHorarioG);
        
        String nombre = ((String) datos[0][0]) + " " + ((String) datos[0][1]) + " " + ((String) datos[0][2]);
        pnlVisualizar.lblNombre.setText(nombre);
        pnlVisualizar.lblTelefono.setText((String) datos[0][3]);
        pnlVisualizar.lblCurso.setText((String) datos[0][4]);
        pnlVisualizar.lblHorario.setText(darHorario(horario));
        
        pnlVisualizar.datos.setVisible(true);
    }
    
    private void actualizarVista() throws SQLException {
        pnlVisualizar.tabla.getSelectionModel().removeListSelectionListener(this);
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        consultarAlumnos();
        pnlVisualizar.datos.setVisible(false);
        manejaEventoDatosCompletos(alumnoSeleccionado);
        pnlVisualizar.tabla.getSelectionModel().addListSelectionListener(this);
    }
    
    private String darHorario(Horario horario) {
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
}
