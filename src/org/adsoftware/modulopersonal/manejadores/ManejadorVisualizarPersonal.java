package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JPanel;
import com.alee.managers.style.StyleId;
import com.alee.laf.button.WebButton;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.adsoftware.entidades.Personal;
import org.adsoftware.goodies.Panelito;
import org.adsoftware.modulopersonal.interfaces.DMModificarPersonal;
import org.adsoftware.modulopersonal.interfaces.VVisualizarPersonal;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Galeria;

public class ManejadorVisualizarPersonal extends Manejador implements ActionListener {

    private VVisualizarPersonal pnlV;
    private Personal p;

    private ArrayList<Personal> listaP;

    private ArrayList<WebButton> listaBotones;

    public ManejadorVisualizarPersonal(JPanel pnlPrin) throws SQLException {
        super(pnlPrin);

        pnlV = new VVisualizarPersonal();
        pnlV.pnlDatos.setVisible(false);
        pnlV.btnModificar.addActionListener(this);
        pnlV.baja.addActionListener(this);
        consultarPersonal();

        repintarPanelPrincipal(pnlV);
    }

    private void consultarPersonal() throws SQLException {
        listaP = Personal.todos();
        listaBotones = new ArrayList<>();

        for (int i = 0; i < listaP.size(); i++) {
            listaBotones.add(new WebButton(StyleId.buttonHover, "Datos completos"));
            listaBotones.get(i).addActionListener(this);
            pnlV.este.add(new Panelito("/org/adsoftware/iconos/usuario32.png",
                    listaP.get(i).nombreP, listaBotones.get(i)), "growx, pushx");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < listaBotones.size(); i++) {
            if (e.getSource() == listaBotones.get(i)) {
                p = listaP.get(i);
                manejaEventoDatosCompletos();
            }

        }
        try {
            if (e.getSource() == pnlV.baja) {
                manejaEventoBaja();
            } else if (e.getSource() == pnlV.btnModificar) {
                manejaEventoModificar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarPersonal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoDatosCompletos() {
        String rutaIcono = p.genero.equals(Personal.FEMENINO) ? Galeria.FEMALE_ICON : Galeria.MALE_ICON;

        pnlV.icono.setIcon(new ImageIcon(getClass().getResource(rutaIcono)));
        pnlV.nombre.setText(p.nombreP + " " + p.apellidoPatP + " " + p.apellidoMatP);
        pnlV.correo.setText(p.correo);
        pnlV.cargo.setText(p.cargo);
        pnlV.domicilio.setText(p.domicilioP);
        pnlV.pnlDatos.setVisible(true);
    }

    private void manejaEventoBaja() throws SQLException {
        new ManejadorBajaPersonal(p);
    }

    private void manejaEventoModificar() throws SQLException {
        new ManejadorModificarPersonal(p);
        pnlV.este.removeAll();
        consultarPersonal();
        repintarPanelPrincipal(pnlV);
        manejaEventoDatosCompletos();
    }
}
