package org.adsoftware.modulousuario.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.modulousuario.interfaces.VInicioSesion;
import org.adsoftware.principal.ManejadorPrincipal;

public class ManejadorValidarUsuario implements ActionListener {

    private VInicioSesion ventana;

    public ManejadorValidarUsuario() {
        ventana = new VInicioSesion();

        ventana.btnIngresar.addActionListener(this);

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
        String nomUsu = ventana.tfNombreUsuario.getText();
        String contra = getMD5(ventana.tfContrasena.getPassword());
        
        Usuario user = Usuario.buscarPrimero("nombreUsuario", nomUsu);
        
        if(contra.equals(user.contrasena)){
            ventana.dispose();
            new ManejadorPrincipal(user.idUsuario);
        }
    }

    private String getMD5(char[] inp) {
        String input = String.valueOf(inp);
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
