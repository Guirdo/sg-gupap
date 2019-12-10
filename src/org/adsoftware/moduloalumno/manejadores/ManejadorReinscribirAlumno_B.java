
package org.adsoftware.moduloalumno.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.moduloalumno.interfaces.DMReinscribirAlumno;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Galeria;

public class ManejadorReinscribirAlumno_B extends Manejador implements ActionListener{
    private DMReinscribirAlumno dmReinscribir;
    private Alumno alumno;
    private String mensajeError = "", horarioString;
    private DefaultTableModel modelo;
    private Grupo grupo;
    private Horario horario;
    private ArrayList<Grupo> listaGrupos;
    private InterfazBD interfaz;
    int h;
    
    public ManejadorReinscribirAlumno_B(Alumno alumno) throws SQLException{
        
        this.alumno = alumno;
        this.dmReinscribir = new DMReinscribirAlumno();
        dmReinscribir.reinscribir.addActionListener(this); 
        modelo = new DefaultTableModel(new Object[]{"Clave", "Curso", "Horario"}, 0);
        
        llenarCampos();
        consultarGrupos();
        
        dmReinscribir.setVisible(true);
    }
    private void llenarCampos() throws SQLException {
       int a = alumno.idAlumno;
        Object[][] datos = InterfazBD.consultar("select curso "
                + "from alumno, grupo where idAlumno = "+a+" and idGrupo = idGrupoA;");
        
        alumno= Alumno.buscarPrimero("idAlumno", "" + a);
        grupo = Grupo.buscarPrimero("idGrupo", "" + alumno.idGrupoA);
        horario = Horario.buscarPrimero("idHorario", grupo.idHorarioG);
        
        String rutaIcono = alumno.genero.equals(Alumno.FEMENINO) ? Galeria.FEMALE_ICON : Galeria.MALE_ICON;
        
        dmReinscribir.icono.setIcon(new ImageIcon(getClass().getResource(rutaIcono)));
        dmReinscribir.nombre.setText(alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA);
        dmReinscribir.telefono.setText(alumno.telefono);
        dmReinscribir.direccion.setText(alumno.domicilioA);
        dmReinscribir.curso.setText((String) datos[0][0]);
        dmReinscribir.horario.setText(darHorario(horario));
        
        
    }
    private void consultarGrupos() throws SQLException {
        listaGrupos = Grupo.todos();
        for (Grupo g : listaGrupos) {
            Horario h = Horario.buscarPrimero("idHorario", g.idHorarioG);
            modelo.addRow(new Object[]{g.idGrupo, g.curso, darHorario(h)});
        }
        dmReinscribir.tabla.setModel(modelo);
    }
    private String darHorario(Horario horario) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        horarioString = "";

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
        if(e.getSource()== dmReinscribir.reinscribir){
            try {
                manejaEventoReinscribir();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorReinscribirAlumno_B.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void manejaEventoReinscribir() throws SQLException {
        if(verificarCurso()){
            alumno.idGrupoA=grupoA();
            alumno.reinscribir();
            
            NotificationManager.showNotification(dmReinscribir.reinscribir,
                    "Alumno reinscrito con éxito", NotificationIcon.plus.getIcon());
            
        dmReinscribir.dispose();
        }
        
        
        
    }
    
    private boolean verificarCurso(){
        if(dmReinscribir.tabla.getSelectedRow()<0){
            mensajeError += "\n- Selecciona un curso.";
            return false;
        }else
        return dmReinscribir.tabla.getSelectedRow()>=0;
    }
    
    private int grupoA(){
        int cursoA = (int) dmReinscribir.tabla.getValueAt(dmReinscribir.tabla.getSelectedRow(), 0);
        return cursoA;
    }
}
