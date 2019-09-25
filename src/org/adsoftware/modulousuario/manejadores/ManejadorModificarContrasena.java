package org.adsoftware.modulousuario.manejadores;

import com.alee.laf.button.WebButton;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.style.StyleId;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.goodies.Panelito;
import org.adsoftware.modulousuario.interfaces.VGestionPersonal;
import org.adsoftware.modulousuario.interfaces.VModificarContrasena;
import org.adsoftware.utilidades.Encriptacion;

public class ManejadorModificarContrasena implements ActionListener {

    private JPanel panelPrincipal;
    private VGestionPersonal pnlGestion;
    private VModificarContrasena pnlModificar;

    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<WebButton> listaBotones;
    private Usuario userSelect;

    public ManejadorModificarContrasena(JPanel pnlPrin) throws SQLException {
        this.panelPrincipal = pnlPrin;

        pnlGestion = new VGestionPersonal();

        consultarUsuarios();

        repintarPanelPrincipal(pnlGestion);
    }

    private void consultarUsuarios() throws SQLException {
        listaUsuarios = Usuario.todos();
        listaBotones = new ArrayList<>();

        for (int i = 0; i < listaUsuarios.size(); i++) {
            listaBotones.add(new WebButton(StyleId.buttonHover, "Modificar contraseña"));
            listaBotones.get(i).addActionListener(this);
            pnlGestion.add(new Panelito("/org/adsoftware/iconos/usuario32.png",
                    listaUsuarios.get(i).nombreUsuario, listaBotones.get(i)), "growx");
        }
    }

    private void repintarPanelPrincipal(JPanel panel) {
        panelPrincipal.removeAll();
        panelPrincipal.add(panel, "growx,growy,pushx,pushy");
        panelPrincipal.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (e.getSource() == listaBotones.get(i)) {
                userSelect = listaUsuarios.get(i);
                manejaEventoModificarContrasena();
                break;
            }
        }

        try {
            if (e.getSource() == pnlModificar.btnModificar) {
                manejaEventoConfirmar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorModificarContrasena.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoModificarContrasena() {
        pnlModificar = new VModificarContrasena(userSelect.nombreUsuario);
        pnlModificar.btnModificar.addActionListener(this);

        repintarPanelPrincipal(pnlModificar);
    }

    private void manejaEventoConfirmar() throws SQLException {
        String nueva = Encriptacion.getMD5(pnlModificar.tfNueva.getPassword());
        String confirmacion = Encriptacion.getMD5(pnlModificar.tfConfirmar.getPassword());

        if (!nueva.equals(confirmacion)) {
            NotificationManager.showNotification(pnlModificar.btnModificar, "Las contresañas no coinciden", NotificationIcon.warning.getIcon());
            pnlModificar.tfNueva.setText("");
            pnlModificar.tfConfirmar.setText("");
        } else {
            userSelect.contrasena = confirmacion;
            userSelect.guardar();
            repintarPanelPrincipal(pnlGestion);
            NotificationManager.showNotification(pnlModificar.btnModificar,
                    "La contraseña del " + userSelect.nombreUsuario + " ha sido modificada!", NotificationIcon.information.getIcon());
        }
    }

}
