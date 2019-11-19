package org.adsoftware.modulopersonal.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.adsoftware.entidades.Personal;
import javax.swing.JPanel;
import org.adsoftware.modulopersonal.interfaces.VAltaPersonal;
import org.adsoftware.superclases.Manejador;

public class ManejadorAltaPersonal extends Manejador implements ActionListener {

    public JPanel panelPrincipal;
    public VAltaPersonal pnlAlta;

    private String mensajeError = "";

    public ManejadorAltaPersonal(JPanel panelPrin) throws SQLException {
        super(panelPrin);
        this.panelPrincipal = panelPrin;

        pnlAlta = new VAltaPersonal();
        pnlAlta.registrar.addActionListener(this);

        repintarPanelPrincipal(pnlAlta);
    }

    private boolean verificarNombre() {
        String nombre = pnlAlta.tfNombreP.getText();
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
        String apellidoP = pnlAlta.tfApellidoPatP.getText();
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
        String apellidoM = pnlAlta.tfApellidoMatP.getText();
        Pattern patron = Pattern.compile("([A-Za-z]+\\s?){1,3}");
        Matcher coincidor = patron.matcher(apellidoM);

        if (!coincidor.matches()) {
            //pnlAlta.tfApellidoMatP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Apellido Materno incorrecto.";
            return false;
        }
        return coincidor.matches();
    }

    private boolean verificarCorreo() {
        String correo = pnlAlta.tfCorreoP.getText();
        Pattern patron = Pattern.compile("[a-zA-Z_.0-9\\-]+[^.]@[a-zA-Z]+(.[a-z]{2,4})+");
        Matcher coincidor = patron.matcher(correo);

        if (!coincidor.matches()) {
            //pnlAlta.tfCorreoP.setBorder(BorderFactory.createLineBorder(Color.red));
            mensajeError += "\n- Correo electrónico incorrecto.";
            return false;
        }
        return coincidor.matches();
    }

    private boolean verificarFecha() {
        if (pnlAlta.fechaN.getDate() == null) {
            mensajeError += "\n- Fecha nula.";
            return false;
        } else {
            Calendar fechaActual = new GregorianCalendar();
            Calendar fechaNacimiento = new GregorianCalendar();
            fechaNacimiento.setTime(pnlAlta.fechaN.getDate());

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
        mensajeError += pnlAlta.tfDomicilioP.isEmpty() ? "\n- Domicilio nulo." : "";
        return !pnlAlta.tfDomicilioP.isEmpty();
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
        try {
            if (e.getSource() == pnlAlta.registrar) {
                manejaEventoRegistrar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorAltaPersonal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoRegistrar() throws SQLException {
        mensajeError = "";
        NotificationManager.hideAllNotifications();
        if (verificarCampos()) {
            String genero = "", cargo = "";
            //Género
            if (pnlAlta.generoF.isSelected()) {
                genero = Personal.FEMENINO;
            } else {
                genero = Personal.MASCULINO;
            }

            java.sql.Date fecha = new java.sql.Date(pnlAlta.fechaN.getDate().getTime());

            Personal nuevoP = new Personal(pnlAlta.tfNombreP.getText(), pnlAlta.tfApellidoPatP.getText(), pnlAlta.tfApellidoMatP.getText(),
                    fecha, pnlAlta.tfDomicilioP.getText(),
                    pnlAlta.cmbCargo.getSelectedItem().toString(), pnlAlta.tfCorreoP.getText(), genero);
            nuevoP.guardar();
            NotificationManager.showNotification(pnlAlta.registrar,
<<<<<<< HEAD
                    "Personal registrado con éxito", NotificationIcon.warning.getIcon());
=======
                    "Personal registrado con éxito", NotificationIcon.plus.getIcon());
>>>>>>> cf04655bdefb20b96531b4b6490baf09c98119ae
            new ManejadorVisualizarPersonal(panelPrincipal);
        } else {
            NotificationManager.showNotification(pnlAlta.registrar,
                    "Error al ingresar un dato: " + mensajeError, NotificationIcon.warning.getIcon());
        }

    }

}
