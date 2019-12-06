
package org.adsoftware.moduloalumno.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import org.adsoftware.moduloalumno.interfaces.DMCredencial;
import org.adsoftware.moduloalumno.interfaces.VVisualizarAlumnos;
import org.adsoftware.superclases.Manejador;

public class ManejadorVisualizarAlumno extends Manejador implements ActionListener, ListSelectionListener, KeyListener{
    public JPanel panelPrincipal;
    public VVisualizarAlumnos pnlVisualizar;
    private DefaultTableModel modelo;
    private ArrayList<Alumno> listaAlumnos, a;
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
        pnlVisualizar.tfBuscar.addKeyListener(this);
        pnlVisualizar.datos.setVisible(false);
        pnlVisualizar.btnModificar.addActionListener(this);
        pnlVisualizar.btnExpulsar.addActionListener(this);
        pnlVisualizar.btnCredencial.addActionListener(this);
        
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
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== pnlVisualizar.btnModificar){
            try {
                manejaEventoModificar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == pnlVisualizar.btnExpulsar){
            try {
                manejaEventoExpulsar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
<<<<<<< HEAD
            try {
                manejaEventoModificar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == pnlVisualizar.btnCredencial){
            new DMCredencial().setVisible(true);

=======
>>>>>>> 86245f337d87803e092260b284c9c16b0fbbf8c1
        }
        
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
        Object[][] datos = InterfazBD.consultar("select nombreA, apellidoPatA, apellidoMatA, telefono, curso "
                + "from alumno, grupo where idAlumno = "+alumnoSeleccionado+" and idGrupo = idGrupoA;");
        
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
    
    private void actualizarVista(boolean expulsado) throws SQLException {
        pnlVisualizar.tabla.getSelectionModel().removeListSelectionListener(this);
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        consultarAlumnos();
        pnlVisualizar.datos.setVisible(false);
        if(!expulsado){
            manejaEventoDatosCompletos(alumnoSeleccionado);
        }
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

    private void manejaEventoModificar() throws SQLException {
        new ManejadorModificarAlumno(alumno);
        actualizarVista(false);
    }

    private void manejaEventoExpulsar() throws SQLException{
        new ManejadorExpulsarAlumno(alumno);
        actualizarVista(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //Si el textfield no esta vacio entonces..
            if (!pnlVisualizar.tfBuscar.getText().isEmpty()) {
                String busqueda = pnlVisualizar.tfBuscar.getText();

                try {
                    if (busqueda.matches("[A-Za-z]+")) {
                        NotificationManager.showNotification(pnlVisualizar.tfBuscar,
                    "Debe ingresar la matrícula del alumno", NotificationIcon.warning.getIcon());
                    } else if (busqueda.matches("[0-9]+")) {
                        this.buscarAlumnoPorMatricula();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    private void buscarAlumnoPorMatricula() throws SQLException {
        Object[][] datos = InterfazBD.consultar("select idAlumno, apellidoPatA, apellidoMatA, nombre "
                + "from alumno, grupo where idAlumno = "+pnlVisualizar.tfBuscar.getText()+" and idGrupo = idGrupoA;");
        
        pnlVisualizar.tabla.setModel(new DefaultTableModel(datos, new Object[]{"Matricula", "Apellido paterno", "Apellido materno", "Nombre"}));
    }
    
}
