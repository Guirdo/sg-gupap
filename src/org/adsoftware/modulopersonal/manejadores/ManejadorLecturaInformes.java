package org.adsoftware.modulopersonal.manejadores;

import com.alee.laf.button.WebButton;
import com.alee.managers.style.StyleId;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.adsoftware.entidades.Informe;
import org.adsoftware.entidades.InterfazBD;
import org.adsoftware.entidades.Personal;
import org.adsoftware.goodies.Panelito;
import org.adsoftware.modulopersonal.interfaces.VInformes;
import org.adsoftware.modulopersonal.interfaces.VLecturaInforme;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Fecha;
import org.adsoftware.utilidades.Galeria;

public class ManejadorLecturaInformes extends Manejador implements ActionListener {

    private VInformes vistaInformes;
    private VLecturaInforme vistaLectura = null;

    private Personal personal;
    private Informe informe;
    private File archivo;
    private int idUsuario;

    private ArrayList<Informe> listaRecibidos;
    private ArrayList<Informe> listaLeidos;
    private ArrayList<WebButton> listaBtnRecibidos;
    private ArrayList<WebButton> listaBtnLeidos;

    public ManejadorLecturaInformes(JPanel panelPrincipal) throws SQLException {
        super(panelPrincipal);
        this.vistaInformes = new VInformes();

        consultarInformes();

        repintarPanelPrincipal(vistaInformes);
    }

    private void consultarInformes() throws SQLException {
        if ((listaRecibidos = Informe.todos("enviado", true)) != null) {
            listaBtnRecibidos = new ArrayList<>();

            for (Informe i : listaRecibidos) {
                if (!i.leido) {
                    WebButton btn = new WebButton(StyleId.button, "Leer informe");
                    btn.addActionListener(this);
                    listaBtnRecibidos.add(btn);
                    vistaInformes.pnlRecibidos.add(new Panelito(Galeria.BORRADOR24_ICON,
                            Fecha.toString(i.fecha), btn), "growx");
                }
            }
        }

        if ((listaLeidos = Informe.todos("leido", true)) != null) {
            listaBtnLeidos = new ArrayList<>();

            for (Informe i : listaLeidos) {
                WebButton btn = new WebButton(StyleId.button, "Leer informe");
                btn.addActionListener(this);
                listaBtnLeidos.add(btn);
                vistaInformes.pnlLeidos.add(new Panelito(Galeria.ENVIADO24_ICON,
                        Fecha.toString(i.fecha), btn), "growx");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            if (vistaLectura != null) {
                if (e.getSource() == vistaLectura.btnAceptar) {
                    manejaEventoAceptar();
                    return;
                }
            }

            for (int i = 0; i < listaBtnRecibidos.size(); i++) {
                if (e.getSource() == listaBtnRecibidos.get(i)) {
                    informe = listaRecibidos.get(i);
                    archivo = new File(informe.rutaTexto);
                    manejaEventoLeerInforme();
                    return;
                }
            }

            for (int i = 0; i < listaBtnLeidos.size(); i++) {
                if (e.getSource() == listaBtnLeidos.get(i)) {
                    informe = listaLeidos.get(i);
                    archivo = new File(informe.rutaTexto);
                    manejaEventoLeerInforme();
                    return;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejadorLecturaInformes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorLecturaInformes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoLeerInforme() throws FileNotFoundException, SQLException {
        archivo = new File(informe.rutaTexto);
        personal = Personal.buscarPrimero("idPersonal", "" + informe.idPersonalI);
        vistaLectura = new VLecturaInforme();

        vistaLectura.btnAceptar.addActionListener(this);

        vistaLectura.lblNombre.setText(personal.nombreP + " " + personal.apellidoPatP);
        vistaLectura.lblFecha.setText(Fecha.formatoHumano(informe.fecha));

        leerArchivo();

        repintarPanelPrincipal(vistaLectura);
    }

    private void leerArchivo() throws FileNotFoundException {
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

        vistaLectura.tpInforme.setText(texto);
    }

    private void manejaEventoAceptar() throws SQLException {
        System.out.println(informe.leido);
        if (!informe.leido) {
            informe.leido = true;
            informe.guardar();

            vistaInformes = new VInformes();
            consultarInformes();
        }
        repintarPanelPrincipal(vistaInformes);
    }

}
