package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.AsistenciaPersonal;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulopersonal.interfaces.VRegistroES;
import org.adsoftware.superclases.Manejador;

public class ManejadorRegistroES extends Manejador implements ActionListener, KeyListener {

    private VRegistroES vistaRegistro;

    private DefaultTableModel modelo;

    public ManejadorRegistroES(JPanel pnl) throws SQLException {
        super(pnl);

        vistaRegistro = new VRegistroES();

        vistaRegistro.btnEntrada.addActionListener(this);
        vistaRegistro.btnSalida.addActionListener(this);
        vistaRegistro.tfClave.addKeyListener(this);

        consultarAsistencia();

        repintarPanelPrincipal(vistaRegistro);
    }

    private void consultarAsistencia() throws SQLException {
        SimpleDateFormat ff = new SimpleDateFormat("hh:mm");
        Date d = new Date();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(d);
        modelo = new DefaultTableModel(new Object[]{"Clave empleado", "Nombre", "Hora"}, 0);
        vistaRegistro.tabla.setModel(modelo);

        ArrayList<AsistenciaPersonal> listaAsistencia = AsistenciaPersonal.todos("fecha",
                c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));

        for (AsistenciaPersonal a : listaAsistencia) {
            Personal p = Personal.buscarPrimero("idPersonal", "" + a.idPersonlaA);
            modelo.addRow(new Object[]{a.idPersonlaA, p.nombreP, ff.format(a.hora)});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRegistro.btnEntrada) {
            manejaEventoEntrada();
        } else if (e.getSource() == vistaRegistro.btnSalida) {
            manejaEventoSalida();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                manejaEventoBuscar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorRegistroES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void manejaEventoEntrada() {
    }

    private void manejaEventoSalida() {
    }

    private void manejaEventoBuscar() throws SQLException {
        if (!vistaRegistro.tfClave.isEmpty()) {
            String clave = vistaRegistro.tfClave.getText();
            Personal p = Personal.buscarPrimero("idPersonal", clave);
            vistaRegistro.lblNombre.setText(p.nombreP+" "+p.apellidoPatP);
            vistaRegistro.lblCargo.setText(p.cargo);
        } else {
            System.out.println("Hola, soy un error");
        }
    }

}
