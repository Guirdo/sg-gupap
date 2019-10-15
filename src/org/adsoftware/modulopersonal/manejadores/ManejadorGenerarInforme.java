package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulopersonal.interfaces.VInformeSemanal;
import org.adsoftware.superclases.Manejador;

public class ManejadorGenerarInforme extends Manejador implements ActionListener {

    private VInformeSemanal vistaInforme;

    private Personal personal;
    private int idUsuario;

    public ManejadorGenerarInforme(int idU, JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        this.idUsuario = idU;

        vistaInforme = new VInformeSemanal();

        consultaPersonal();

        repintarPanelPrincipal(vistaInforme);
    }

    private void consultaPersonal() throws SQLException {
        Object[][] consulta = InterfazBD.consultar("select idPersonalUP from usuarioPersonal where idUsuarioUP = " + idUsuario);
        int idPersonal = (int) consulta[0][0];
        personal = Personal.buscarPrimero("idPersonal", "" + idPersonal);
    }

    private void consultarInformes() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaInforme.btnGenerar) {
            manejaEventoGenerarInforme();
        }
    }

    private void manejaEventoGenerarInforme() {
        
    }

}
