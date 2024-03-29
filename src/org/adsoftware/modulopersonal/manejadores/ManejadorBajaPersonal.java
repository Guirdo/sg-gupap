
package org.adsoftware.modulopersonal.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.adsoftware.entidades.Personal;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.modulopersonal.interfaces.DMConfirmarBaja;
import org.adsoftware.utilidades.Encriptacion;

public class ManejadorBajaPersonal implements ActionListener {

    DMConfirmarBaja dmBaja;
    Usuario direc;
    Personal p;

    public ManejadorBajaPersonal(Personal p) throws SQLException {
        dmBaja = new DMConfirmarBaja();

        dmBaja.confirmar.addActionListener(this);
        dmBaja.cancelar.addActionListener(this);
        this.p = p;

        direc = Usuario.buscarPrimero("idUsuario", "" + 1);

        dmBaja.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dmBaja.confirmar) {
            try {
                manejaEventoConfirmar();
            } catch (SQLException ex) {
                Logger.getLogger(ManejadorBajaPersonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == dmBaja.cancelar) {
            dmBaja.dispose();
        }
    }

    private void manejaEventoConfirmar() throws SQLException {
        String aux = Encriptacion.getMD5(dmBaja.contra.getPassword());
        if (aux.equals(direc.contrasena)) {
            p.baja();
            dmBaja.dispose();

        } else {
            NotificationManager.showNotification(dmBaja.confirmar,
                    "Contraseña incorrecta", NotificationIcon.warning.getIcon());
        }
    }

}
