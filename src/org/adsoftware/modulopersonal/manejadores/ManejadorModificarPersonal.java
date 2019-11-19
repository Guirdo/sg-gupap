package org.adsoftware.modulopersonal.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulopersonal.interfaces.DMModificarPersonal;
import org.adsoftware.superclases.Manejador;

public class ManejadorModificarPersonal extends Manejador implements ActionListener {

    private DMModificarPersonal vista;
    private Personal personal;
    
    private String mensajeError = "";

    public ManejadorModificarPersonal(Personal p) {
        vista = new DMModificarPersonal();
        personal = p;

        vista.btnModificar.addActionListener(this);

        llenarCampos();

        vista.setVisible(true);
    }

    private boolean verificarNombre() {
        String nombre = vista.tfNombreP.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(nombre);

        if (!coincidor.matches()) {
            //vista.tfNombreP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Nombre(s) incorrectos.";
            return false;
        }
        return coincidor.matches();
    }

    private boolean verificarApellidoPat() {
        String apellidoP = vista.tfApellidoPatP.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoP);

        if (!coincidor.matches()) {
            //vista.tfApellidoPatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido paterno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }

    private boolean verificarApellidoMat() {
        String apellidoM = vista.tfApellidoMatP.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoM);

        if (!coincidor.matches()) {
            //vista.tfApellidoMatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido materno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }

    private boolean verificarCorreo() {
        String correo = vista.tfCorreoP.getText();
        Pattern patron = Pattern.compile("[a-zA-Z_.0-9\\-]+[^.]@[a-zA-Z]+.[a-z]{2,4}");
        Matcher coincidor = patron.matcher(correo);

        if (!coincidor.matches()) {
            mensajeError += "\n- Correo electrónico invalido.";
            return false;
        }
        return coincidor.matches();
    }
    
    private boolean verificarFecha() {
        if (vista.fechaN.getDate() == null) {
            mensajeError += "\n- Fecha nula.";
            return false;
        } else {
            Calendar fechaActual = new GregorianCalendar();
            Calendar fechaNacimiento = new GregorianCalendar();
            fechaNacimiento.setTime(vista.fechaN.getDate());

            int anoNacimiento = fechaNacimiento.get(Calendar.YEAR);
            int anoActual = fechaActual.get(Calendar.YEAR);

            if (anoActual - anoNacimiento >= 18) {
                if (fechaNacimiento.get(Calendar.MONTH) <= fechaActual.get(Calendar.MONTH)) {
                    return true;
                } else {
                    mensajeError += "\n- Fecha incorrecta: el empleado \ndebe ser mayor de edad.";
                    return false;
                }
            } else {
                mensajeError += "\n- Fecha incorrecta: el empleado \ndebe ser mayor de edad.";
                return false;
            }
        }
    }

    private boolean verificarDomicilio() {
        mensajeError += vista.tfDomicilioP.isEmpty() ? "\n- Domicilio nulo." : "";
        return !vista.tfDomicilioP.isEmpty();
    }

    private boolean verificarCampos() {
        boolean nombreCorrecto = verificarNombre();
        boolean apellidoPatCorrecto = verificarApellidoPat();
        boolean apellidoMatCorrecto = verificarApellidoMat();
        boolean fechCorrecta = verificarFecha();
        boolean domicilioCorrecto = verificarDomicilio();
        boolean correoCorrecto = verificarCorreo();

        return nombreCorrecto && apellidoPatCorrecto && apellidoMatCorrecto && domicilioCorrecto && correoCorrecto && fechCorrecta;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnModificar) {
            try {
                manejaEventoModificar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorModificarPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void llenarCampos() {
        vista.tfNombreP.setText(personal.nombreP);
        vista.tfApellidoPatP.setText(personal.apellidoPatP);
        vista.tfApellidoMatP.setText(personal.apellidoMatP);
        vista.tfDomicilioP.setText(personal.domicilioP);
        vista.tfCorreoP.setText(personal.correo);
        vista.fechaN.setDate(personal.fechaNacimiento);
        vista.cmbCargo.setSelectedItem(personal.cargo);

        if (personal.genero.equals(Personal.FEMENINO)) 
            vista.generoF.setSelected(true);
         else 
            vista.generoM.setSelected(true);    
    }

    private void manejaEventoModificar() throws SQLException {
        mensajeError="";
        NotificationManager.hideAllNotifications();
        if (verificarCampos()) {
            personal.nombreP = vista.tfNombreP.getText();
            personal.apellidoPatP = vista.tfApellidoPatP.getText();
            personal.apellidoMatP = vista.tfApellidoMatP.getText();
            personal.domicilioP = vista.tfDomicilioP.getText();
            personal.correo = vista.tfCorreoP.getText();
            personal.cargo = vista.cmbCargo.getSelectedItem().toString();
            personal.genero = vista.generoF.isSelected() ? Personal.FEMENINO : Personal.MASCULINO;

            personal.actualizar();
            vista.dispose();
        }else{
            NotificationManager.showNotification(vista.btnModificar,
                    "Error al ingresar un dato: "+mensajeError, NotificationIcon.warning.getIcon());
        }
    }
}
