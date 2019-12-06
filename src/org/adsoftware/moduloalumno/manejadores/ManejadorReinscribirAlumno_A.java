
package org.adsoftware.moduloalumno.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.adsoftware.moduloalumno.interfaces.VReinscribirAlumno;
import org.adsoftware.superclases.Manejador;

public class ManejadorReinscribirAlumno_A extends Manejador implements ActionListener, ListSelectionListener, KeyListener{
    
    public JPanel panelPrincipal;
    public VReinscribirAlumno pnlVisualizarReinscribir;
    private DefaultTableModel modelo;
    int alumnoSeleccionado=0;
    public Grupo grupo;
    public Horario horario;
    public Alumno alumno;
    
    public ManejadorReinscribirAlumno_A(JPanel panelPrin) throws SQLException {
    super(panelPrin);
    this.panelPrincipal = panelPrin;

    modelo = new DefaultTableModel(new Object[]{"Matrícula", "Apellido paterno", "Apellido materno", "Nombre"}, 0);
    pnlVisualizarReinscribir = new VReinscribirAlumno();
    pnlVisualizarReinscribir.tabla.getSelectionModel().addListSelectionListener(this);
    pnlVisualizarReinscribir.tfBuscar.addKeyListener(this);
    pnlVisualizarReinscribir.datos.setVisible(false);
    pnlVisualizarReinscribir.btnReinscribir.addActionListener(this);
    consultarAlumnos();
        
    repintarPanelPrincipal(pnlVisualizarReinscribir);
    }
    
    private void consultarAlumnos() throws SQLException {
        Object[][] datos = InterfazBD.consultar("select idAlumno, apellidoPatA, apellidoMatA, nombre "
                + "from alumno, grupo where numSemanas = 23 and idGrupo = idGrupoA;");
        
        pnlVisualizarReinscribir.tabla.setModel(new DefaultTableModel(datos, new Object[]{"Matricula", "Apellido paterno", "Apellido materno", "Nombre"}));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== pnlVisualizarReinscribir.btnReinscribir){
            try {
                manejaEventoReinscribir();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorReinscribirAlumno_A.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        alumnoSeleccionado = (int) pnlVisualizarReinscribir.tabla.getValueAt(pnlVisualizarReinscribir.tabla.getSelectedRow(), 0);
        try {
            manejaEventoDatosCompletos(alumnoSeleccionado);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorReinscribirAlumno_A.class.getName()).log(Level.SEVERE, null, ex);
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
        pnlVisualizarReinscribir.lblNombre.setText(nombre);
        pnlVisualizarReinscribir.lblTelefono.setText((String) datos[0][3]);
        pnlVisualizarReinscribir.lblCurso.setText((String) datos[0][4]);
        pnlVisualizarReinscribir.lblHorario.setText(darHorario(horario));
        
        pnlVisualizarReinscribir.datos.setVisible(true);
    }
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            //Si el textfield no esta vacio entonces..
            if (!pnlVisualizarReinscribir.tfBuscar.getText().isEmpty()) {
                String busqueda = pnlVisualizarReinscribir.tfBuscar.getText();

                try {
                    if (busqueda.matches("[A-Za-z]+")) {
                        NotificationManager.showNotification(pnlVisualizarReinscribir.tfBuscar,
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
    
    private void buscarAlumnoPorMatricula() throws SQLException {
        Object[][] datos = InterfazBD.consultar("select idAlumno, apellidoPatA, apellidoMatA, nombre "
                + "from alumno, grupo where idAlumno = "+pnlVisualizarReinscribir.tfBuscar.getText()+" and idGrupo = idGrupoA;");
        
        pnlVisualizarReinscribir.tabla.setModel(new DefaultTableModel(datos, new Object[]{"Matricula", "Apellido paterno", "Apellido materno", "Nombre"}));
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    private void actualizarVista() throws SQLException {
        pnlVisualizarReinscribir.tabla.getSelectionModel().removeListSelectionListener(this);
        while(modelo.getRowCount()>0) modelo.removeRow(0);
        consultarAlumnos();
        pnlVisualizarReinscribir.datos.setVisible(false);
        manejaEventoDatosCompletos(alumnoSeleccionado);
        pnlVisualizarReinscribir.tabla.getSelectionModel().addListSelectionListener(this);
    }

    private void manejaEventoReinscribir() throws SQLException {
    new ManejadorReinscribirAlumno_B(alumno);
        actualizarVista();
    }
}
