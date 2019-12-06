
package org.adsoftware.moduloalumno.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.moduloalumno.interfaces.DMReinscribirAlumno;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Galeria;

public class ManejadorReinscribirAlumno_B extends Manejador implements ActionListener{
    private DMReinscribirAlumno dmReinscribir;
    private Alumno alumno;
    private String mensajeError = "";
    private DefaultTableModel modelo;
    private Grupo grupo;
    private Horario horario;
    private ArrayList<Grupo> listaGrupos;
    
    public ManejadorReinscribirAlumno_B(Alumno alumno) throws SQLException{
        
        this.alumno = alumno;
        this.dmReinscribir = new DMReinscribirAlumno();
        dmReinscribir.reinscribir.addActionListener(this); 
        modelo = new DefaultTableModel(new Object[]{"Clave", "Curso", "Horario"}, 0);
        
        llenarCampos();
        consultarGrupos();
        
        dmReinscribir.setVisible(true);
    }
    private void llenarCampos() {
        String rutaIcono = alumno.genero.equals(Alumno.FEMENINO) ? Galeria.FEMALE_ICON : Galeria.MALE_ICON;
        
        dmReinscribir.icono.setIcon(new ImageIcon(getClass().getResource(rutaIcono)));
        dmReinscribir.nombre.setText(alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA);
        dmReinscribir.telefono.setText(alumno.telefono);
        dmReinscribir.direccion.setText(alumno.domicilioA);
        
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
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
