package org.adsoftware.moduloalumno.manejadores;

import com.alee.laf.filechooser.WebFileChooser;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.BadElementException;
import com.itextpdf.layout.element.Image;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.moduloalumno.interfaces.DMCredencial;
import org.adsoftware.superclases.Manejador;

public class ManejadorGenerarCredencial extends Manejador implements ActionListener {

    private DMCredencial vista;

    private Alumno alumno;
    private Grupo grupo;
    private Horario horario;

    public ManejadorGenerarCredencial(Alumno alum, Grupo grupo, Horario horario) {
        vista = new DMCredencial();
        this.alumno = alum;
        this.grupo = grupo;
        this.horario = horario;

        vista.btnAgregar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.btnGenerar.addActionListener(this);

        rellenarCampos();

        vista.setVisible(true);
    }

    private void rellenarCampos() {
        vista.lblNombre.setText(alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA);
        vista.lblCurso.setText(grupo.curso);
        vista.lblHorario.setText(darHorario());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgregar) {
            manejaEventoAgregar();
        } else if (e.getSource() == vista.btnGenerar) {
            try {
                manejaEventoGenerar();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorGenerarCredencial.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadElementException ex) {
                Logger.getLogger(ManejadorGenerarCredencial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String darHorario() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String horarioString = "";

        horarioString += horario.lunes ? "L " : "";
        horarioString += horario.martes ? "M " : "";
        horarioString += horario.miercoles ? "Mi " : "";
        horarioString += horario.jueves ? "J " : "";
        horarioString += horario.viernes ? "V " : "";
        horarioString += horario.sabado ? "S " : "";
        horarioString += horario.domingo ? "D " : "";

        horarioString += df.format(horario.horaInicial) + " - " + df.format(horario.horaFinal);

        return horarioString;
    }

    private void manejaEventoAgregar() {
        WebFileChooser fc = new WebFileChooser("\\\\app\\C$\\");

        int seleccion = fc.showOpenDialog(vista);

        if (seleccion == WebFileChooser.APPROVE_OPTION) {//Aceptado
            File foto = fc.getSelectedFile();

            vista.foto.setImagen(foto.getAbsolutePath());
            vista.foto.repaint();
        }
    }

    private void generarCredencial() {
        try {
            ImageIO.write(vista.pnlCredencial.crearImagen(), "png", new File("archivos/credencial.png"));
        } catch (IOException ex) {
            
        }
    }

    private void manejaEventoGenerar() throws FileNotFoundException, IOException, BadElementException {
        generarCredencial();
        PdfDocument pdf = new PdfDocument(new PdfWriter("archivos/credencial.pdf"));
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 30, 35, 30);
        Image image = new Image(ImageDataFactory.create("archivos/credencial.png"));

        document.add(image);

        document.close();

        Desktop.getDesktop().open(new File("archivos/credencial.pdf"));

        vista.dispose();
    }

}
