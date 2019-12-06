
package org.adsoftware.moduloalumno.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.moduloalumno.interfaces.DMModificarAlumno;
import org.adsoftware.superclases.Manejador;

public class ManejadorModificarAlumno extends Manejador implements ActionListener {

     private DMModificarAlumno dmAlumno;
     private Alumno alumno;
     private String mensajeError = "";

    public ManejadorModificarAlumno(Alumno alumno) throws SQLException{
        this.alumno = alumno;
        this.dmAlumno = new DMModificarAlumno();
        dmAlumno.confirmar.addActionListener(this);      
        
        llenarCampos();
        
        dmAlumno.setVisible(true);
    }
     
    public void llenarCampos(){
        dmAlumno.tfNombreA.setText(alumno.nombre);
        dmAlumno.tfApellidoPatA.setText(alumno.apellidoPatA);
        dmAlumno.tfApellidoMatA.setText(alumno.apellidoMatA);
        dmAlumno.fechaN.setDate(alumno.fechaNacimiento);
        if (alumno.genero.equals(Alumno.FEMENINO)) 
            dmAlumno.generoF.setSelected(true);
         else 
            dmAlumno.generoM.setSelected(true);
        dmAlumno.tfDomicilioA.setText(alumno.domicilioA);
        dmAlumno.tfTelefono.setText(alumno.telefono);
        
    }
    
    private boolean verificarNombre() {
        String nombre = dmAlumno.tfNombreA.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(nombre);

        if (!coincidor.matches()) {
            //pnlAlta.tfNombreP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Nombre(s) incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarApellidoPat() {
        String apellidoP = dmAlumno.tfApellidoPatA.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoP);

        if (!coincidor.matches()) {
            //pnlAlta.tfApellidoPatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido Paterno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarApellidoMat() {
        String apellidoM = dmAlumno.tfApellidoMatA.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoM);

        if (!coincidor.matches()) {
            //pnlAlta.tfApellidoMatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido Materno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarFecha() {
        if (dmAlumno.fechaN.getDate() == null) {
            mensajeError += "\n- Fecha nula.";
            return false;
        } else {
            Calendar fechaActual = new GregorianCalendar();
            Calendar fechaNacimiento = new GregorianCalendar();
            fechaNacimiento.setTime(dmAlumno.fechaN.getDate());

            int anoNacimiento = fechaNacimiento.get(Calendar.YEAR);
            int anoActual = fechaActual.get(Calendar.YEAR);

            if (anoActual - anoNacimiento >= 6) {
                if (fechaNacimiento.get(Calendar.MONTH) <= fechaActual.get(Calendar.MONTH)) {
                    return true;
                } else {
                    mensajeError += "\n- Fecha incorrecta: el alumno debe ser mayor a 6 años de edad.";
                    return false;
                }
            } else {
                mensajeError += "\n- Fecha incorrecta: el alumno debe ser mayor a 6 años de edad";
                return false;
            }
        }
    }//vf
     
    private boolean verificarTelefono() {
        String telefono = dmAlumno.tfTelefono.getText();
        Pattern patron = Pattern.compile("[1-9]{3}[0-9]{7}");
        Matcher coincidor = patron.matcher(telefono);

        if (!coincidor.matches()) {
            mensajeError += "\n- Número telefónico incorrecto.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarDomicilio() {
        mensajeError += dmAlumno.tfDomicilioA.isEmpty() ? "\n- Domicilio nulo." : "";
        return !dmAlumno.tfDomicilioA.isEmpty();
    }
    
    private boolean verificarCampos() {
        boolean nombreCorrecto = verificarNombre();
        boolean apellidoPatCorrecto = verificarApellidoPat();
        boolean apellidoMatCorrecto = verificarApellidoMat();
        boolean fechaCorrecta = verificarFecha();
        boolean domicilioCorrecto = verificarDomicilio();
        boolean telefonoCorrecto = verificarTelefono();

        return nombreCorrecto && apellidoPatCorrecto && apellidoMatCorrecto && domicilioCorrecto && telefonoCorrecto && fechaCorrecta;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == dmAlumno.confirmar){
            try {
                manejaEventoModificar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorModificarAlumno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private void manejaEventoModificar() throws SQLException{
        mensajeError="";
        NotificationManager.hideAllNotifications();
        java.sql.Date fecha = new java.sql.Date(dmAlumno.fechaN.getDate().getTime());
        if(verificarCampos()){
            alumno.nombre = dmAlumno.tfNombreA.getText();
            alumno.apellidoPatA = dmAlumno.tfApellidoPatA.getText();
            alumno.apellidoMatA = dmAlumno.tfApellidoMatA.getText();
            alumno.fechaNacimiento = fecha;
            alumno.genero = dmAlumno.generoF.isSelected() ? Alumno.FEMENINO : Alumno.MASCULINO;
            alumno.domicilioA = dmAlumno.tfDomicilioA.getText();
            alumno.telefono = dmAlumno.tfTelefono.getText();
            
            alumno.actualizar();
            dmAlumno.dispose();
        }else{
            NotificationManager.showNotification(dmAlumno.confirmar,
                    "Error al ingresar un dato: "+mensajeError, NotificationIcon.warning.getIcon());
        }
    }
    
    
    
}
