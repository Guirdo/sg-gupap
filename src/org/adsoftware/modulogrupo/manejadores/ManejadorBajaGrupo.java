package org.adsoftware.modulogrupo.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.modulopersonal.interfaces.DMConfirmarBaja;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Encriptacion;

public class ManejadorBajaGrupo extends Manejador implements ActionListener {

    private DMConfirmarBaja vista;
    private Usuario usuario;
    private Grupo grupo;
    private Horario horario;

    public ManejadorBajaGrupo(Usuario usuario, Grupo grupo, Horario horario) {
        this.usuario = usuario;
        this.grupo = grupo;
        this.horario = horario;
        vista = new DMConfirmarBaja();

        vista.confirmar.addActionListener(this);
        vista.cancelar.addActionListener(this);

        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.confirmar) {
            try {
                manejaEventoBaja();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorBajaGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == vista.cancelar) {
            vista.dispose();
        }
    }

    private void manejaEventoBaja() throws SQLException {
        String aux = Encriptacion.getMD5(vista.contra.getPassword());
        if (aux.equals(usuario.contrasena)) {
            grupo.eliminar();
            horario.eliminar();
            vista.dispose();
        } else {
            NotificationManager.showNotification(vista.confirmar,
                    "Contraseña incorrecta", NotificationIcon.warning.getIcon());
        }
    }

}
