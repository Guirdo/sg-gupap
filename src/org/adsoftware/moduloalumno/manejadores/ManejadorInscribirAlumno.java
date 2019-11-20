/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adsoftware.moduloalumno.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.adsoftware.moduloalumno.interfaces.VInscribirAlumno;
import org.adsoftware.superclases.Manejador;

/**
 *
 * @author diann
 */
public class ManejadorInscribirAlumno extends Manejador implements ActionListener {
    
    public JPanel panelPrincipal;
    public VInscribirAlumno pnlInscribir;

    private String mensajeError = "";
    
    public ManejadorInscribirAlumno(JPanel panelPrin) throws SQLException {
        super(panelPrin);
        this.panelPrincipal = panelPrin;

        pnlInscribir = new VInscribirAlumno();
        pnlInscribir.registrar.addActionListener(this);

        repintarPanelPrincipal(pnlInscribir);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
