package org.adsoftware.modulousuario.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.modulousuario.interfaces.VInicioSesion;
import org.adsoftware.principal.ManejadorPrincipal;
import org.adsoftware.utilidades.Encriptacion;

public class ManejadorValidarUsuario implements ActionListener,KeyListener {

    private VInicioSesion ventana;

    public ManejadorValidarUsuario() {
        ventana = new VInicioSesion();

        ventana.btnIngresar.addActionListener(this);
        ventana.tfNombreUsuario.addKeyListener(this);
        ventana.tfContrasena.addKeyListener(this);

        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventana.btnIngresar) {
            try {
                manejaEventoIngresar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorValidarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void manejaEventoIngresar() throws SQLException {
        if (!ventana.tfNombreUsuario.getText().isEmpty() && !ventana.tfContrasena.isEmpty()) {
            String nomUsu = ventana.tfNombreUsuario.getText();
            String contra = Encriptacion.getMD5(ventana.tfContrasena.getPassword());

            Usuario user = Usuario.buscarPrimero("nombreUsuario", nomUsu);

            if (contra.equals(user.contrasena)) {
                ventana.dispose();
                new ManejadorPrincipal(user.idUsuario);
            } else {
                NotificationManager.showNotification(ventana.btnIngresar,
                        "Usuario o contraseña incorrecta", NotificationIcon.warning.getIcon());
                ventana.tfNombreUsuario.setText("");
                ventana.tfContrasena.setText("");
            }
        } else {
            NotificationManager.showNotification(ventana.btnIngresar,
                    "Campo vacio: por favor, ingrese lo que se le pide.", NotificationIcon.warning.getIcon());
            ventana.tfNombreUsuario.setText("");
            ventana.tfContrasena.setText("");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == ventana.tfNombreUsuario){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                ventana.tfContrasena.requestFocus();
            }
        }else if(e.getSource() == ventana.tfContrasena){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                try {
                    manejaEventoIngresar();
                } catch (SQLException ex) {
                    Logger.getLogger(ManejadorValidarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
