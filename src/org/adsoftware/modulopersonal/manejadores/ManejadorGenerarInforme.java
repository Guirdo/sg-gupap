package org.adsoftware.modulopersonal.manejadores;

import com.alee.laf.button.WebButton;
import com.alee.laf.text.WebTextPane;
import com.alee.managers.style.StyleId;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.adsoftware.entidades.Informe;
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.entidades.Personal;
import org.adsoftware.goodies.Panelito;
import org.adsoftware.modulopersonal.interfaces.VGenerarInforme;
import org.adsoftware.modulopersonal.interfaces.VInformeSemanal;
import org.adsoftware.modulopersonal.interfaces.VLecturaInforme;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Fecha;
import org.adsoftware.utilidades.Galeria;

public class ManejadorGenerarInforme extends Manejador implements ActionListener {

    private VInformeSemanal vistaInforme;
    private VGenerarInforme vistaGenerar;
    private VLecturaInforme vistaLectura;
    private Informe informe;

    private Personal personal;
    private int idUsuario;
    private File archivo;

    private ArrayList<Informe> listaBorradores;
    private ArrayList<Informe> listaEnviados;
    private ArrayList<WebButton> listaBtnBorrador;
    private ArrayList<WebButton> listaBtnEnviado;

    public ManejadorGenerarInforme(int idU, JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        this.idUsuario = idU;
        this.informe = null;

        vistaInforme = new VInformeSemanal();
        vistaInforme.btnGenerar.addActionListener(this);

        consultaPersonal();
        consultarInformes();

        repintarPanelPrincipal(vistaInforme);
    }

    private void consultaPersonal() throws SQLException {
        Object[][] consulta = InterfazBD.consultar("select idPersonalUP from usuarioPersonal where idUsuarioUP = " + idUsuario);
        int idPersonal = (int) consulta[0][0];
        personal = Personal.buscarPrimero("idPersonal", "" + idPersonal);
    }

    private void consultarInformes() throws SQLException {
        if ((listaBorradores = Informe.todos("enviado", false, personal.idPersonal)) != null) {
            listaBtnBorrador = new ArrayList<>();

            for (Informe i : listaBorradores) {
                WebButton btn = new WebButton(StyleId.button, "Editar borrador");
                btn.addActionListener(this);
                listaBtnBorrador.add(btn);
                vistaInforme.pnlBorradores.add(new Panelito(Galeria.BORRADOR24_ICON,
                        Fecha.toString(i.fecha), btn), "growx");
            }
        }

        if ((listaEnviados = Informe.todos("enviado", true, personal.idPersonal)) != null) {
            listaBtnEnviado = new ArrayList<>();

            for (Informe i : listaEnviados) {
                WebButton btn = new WebButton(StyleId.button, "Ver informe");
                btn.addActionListener(this);
                listaBtnEnviado.add(btn);
                vistaInforme.pnlEnviados.add(new Panelito(Galeria.ENVIADO24_ICON,
                        Fecha.toString(i.fecha), btn), "growx");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vistaInforme.btnGenerar) {
                manejaEventoGenerarInforme();
                return;
            }

            if (vistaGenerar != null) {
                if (e.getSource() == vistaGenerar.btnCancelar) {
                    manejaEventoCancelar();
                    return;
                } else if (e.getSource() == vistaGenerar.btnEnviar) {
                    manejaEventoEnviar();
                    return;
                } else if (e.getSource() == vistaGenerar.btnGuardar) {
                    manejaEventoGuardar();
                    return;
                }
            }
            
            if(vistaLectura != null){
                if(e.getSource() == vistaLectura.btnAceptar){
                    manejaEventoAceptar();
                    return;
                }
            }

            for (int i = 0; i < listaBtnBorrador.size(); i++) {
                if (e.getSource() == listaBtnBorrador.get(i)) {
                    informe = listaBorradores.get(i);
                    manejaEventoEditarInforme();
                    return;
                }
            }

            for (int i = 0; i < listaBtnEnviado.size(); i++) {
                if (e.getSource() == listaBtnEnviado.get(i)) {
                    informe = listaEnviados.get(i);
                    manejaEventoVerInforme();
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorGenerarInforme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorGenerarInforme.class.getName()).log(Level.SEVERE, null, ex);
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
        informe = null;
    }

    private void manejaEventoEnviar() throws SQLException {
        if (informe == null) {
            String nombreArchivo = personal.apellidoPatP + personal.apellidoMatP + "_" + Fecha.actual();
            archivo = new File("informes/" + nombreArchivo + ".txt");
            guardarTexto();
            Informe infor = new Informe(archivo.getAbsolutePath(), true, false, personal.idPersonal);
            infor.insertar();
        } else {
            archivo = new File(informe.rutaTexto);
            guardarTexto();
            informe.enviado = true;
            informe.guardar();
        }
        actualizarVista();
    }

    private void manejaEventoGuardar() throws SQLException {
        if (informe == null) {
            String nombreArchivo = personal.apellidoPatP + personal.apellidoMatP + "_" + Fecha.actual();
            archivo = new File("informes/" + nombreArchivo + ".txt");
            guardarTexto();
            Informe infor = new Informe(archivo.getAbsolutePath(), false, false, personal.idPersonal);
            infor.insertar();
        } else {
            archivo = new File(informe.rutaTexto);
            guardarTexto();
        }
        actualizarVista();
    }

    private void actualizarVista() throws SQLException {
        informe = null;
        vistaInforme = new VInformeSemanal();
        consultarInformes();
        repintarPanelPrincipal(vistaInforme);
    }

    private void guardarTexto() {
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);

            pw.print(vistaGenerar.tpInforme.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void leerArchivo(WebTextPane pnl) throws FileNotFoundException {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String texto = "";
        String linea;

        try {
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorGenerarInforme.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(texto);
        pnl.setText(texto);
    }

    private void manejaEventoEditarInforme() throws FileNotFoundException {
        archivo = new File(informe.rutaTexto);
        vistaGenerar = new VGenerarInforme();

        vistaGenerar.btnCancelar.addActionListener(this);
        vistaGenerar.btnGuardar.addActionListener(this);
        vistaGenerar.btnEnviar.addActionListener(this);

        vistaGenerar.lblNombre.setText(personal.nombreP + " " + personal.apellidoPatP);

        leerArchivo(vistaGenerar.tpInforme);

        repintarPanelPrincipal(vistaGenerar);
    }

    private void manejaEventoVerInforme() throws FileNotFoundException {
        archivo = new File(informe.rutaTexto);
        vistaLectura = new VLecturaInforme();

        vistaLectura.btnAceptar.addActionListener(this);

        vistaLectura.lblNombre.setText(personal.nombreP + " " + personal.apellidoPatP);
        vistaLectura.lblFecha.setText(Fecha.formatoHumano(informe.fecha));

        leerArchivo(vistaLectura.tpInforme);

        repintarPanelPrincipal(vistaLectura);
    }

    private void manejaEventoAceptar() {
        repintarPanelPrincipal(vistaInforme);
        informe = null;
    }

}
