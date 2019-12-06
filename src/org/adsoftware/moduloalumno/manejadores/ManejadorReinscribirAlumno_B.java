
package org.adsoftware.moduloalumno.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.moduloalumno.interfaces.DMReinscribirAlumno;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Galeria;

public class ManejadorReinscribirAlumno_B extends Manejador implements ActionListener{
    private DMReinscribirAlumno dmReinscribir;
    private Alumno alumno;
    private String mensajeError = "";
    
    public ManejadorReinscribirAlumno_B(Alumno alumno) throws SQLException{
        
        this.alumno = alumno;
        this.dmReinscribir = new DMReinscribirAlumno();
        dmReinscribir.reinscribir.addActionListener(this);      
        
        llenarCampos();
        
        dmReinscribir.setVisible(true);
    }
    private void llenarCampos() {
        String rutaIcono = alumno.genero.equals(Alumno.FEMENINO) ? Galeria.FEMALE_ICON : Galeria.MALE_ICON;
        
        dmReinscribir.icono.setIcon(new ImageIcon(getClass().getResource(rutaIcono)));
        dmReinscribir.nombre.setText(alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA);
        dmReinscribir.telefono.setText(alumno.telefono);
        dmReinscribir.direccion.setText(alumno.domicilioA);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
