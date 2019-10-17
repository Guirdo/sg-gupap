package org.adsoftware.modulopersonal.manejadores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.entidades.Personal;
import org.adsoftware.modulopersonal.interfaces.VGenerarInforme;
import org.adsoftware.modulopersonal.interfaces.VInformeSemanal;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Fecha;

public class ManejadorGenerarInforme extends Manejador implements ActionListener {

    private VInformeSemanal vistaInforme;
    private VGenerarInforme vistaGenerar;

    private Personal personal;
    private int idUsuario;
    private String nombreArchivo;

    public ManejadorGenerarInforme(int idU, JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        this.idUsuario = idU;

        vistaInforme = new VInformeSemanal();
        vistaInforme.btnGenerar.addActionListener(this);

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
        } else if (e.getSource() == vistaGenerar.btnCancelar) {
            manejaEventoCancelar();
        } else if (e.getSource() == vistaGenerar.btnEnviar) {
            manejaEventoEnviar();
        }else if(e.getSource() == vistaGenerar.btnGuardar){
            manejaEventoGuardar();
        }
    }

    private void manejaEventoGenerarInforme() {
        vistaGenerar = new VGenerarInforme();

        vistaGenerar.btnCancelar.addActionListener(this);
        vistaGenerar.btnGuardar.addActionListener(this);
        vistaGenerar.btnEnviar.addActionListener(this);

        vistaGenerar.lblNombre.setText(personal.nombreP + " " + personal.apellidoPatP);

        repintarPanelPrincipal(vistaGenerar);
    }

    private void manejaEventoCancelar() {
        repintarPanelPrincipal(vistaInforme);
    }

    private void manejaEventoEnviar() {
        guardarTexto();
    }

    private void manejaEventoGuardar() {
        guardarTexto();
        
    }
    
    private void guardarTexto(){
        FileWriter fichero = null;
        PrintWriter pw = null;
        nombreArchivo = personal.apellidoPatP+personal.apellidoMatP+"_"+Fecha.actual();
        try
        {
            fichero = new FileWriter("informes/"+nombreArchivo+".txt");
            pw = new PrintWriter(fichero);
            
            pw.print(vistaGenerar.tpInforme.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

}
