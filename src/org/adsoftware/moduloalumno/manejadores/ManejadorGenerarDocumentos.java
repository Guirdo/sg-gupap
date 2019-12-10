package org.adsoftware.moduloalumno.manejadores;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.moduloalumno.interfaces.VGenerarDocumentos;
import org.adsoftware.superclases.Manejador;

public class ManejadorGenerarDocumentos extends Manejador implements ActionListener, ListSelectionListener {

    private VGenerarDocumentos vista;

    private DefaultTableModel modelo;
    private ArrayList<Alumno> lista;
    private int alumnoSeleccionado;
    private Alumno alumno;
    private Grupo grupo;
    private Horario horario;

    public ManejadorGenerarDocumentos(JPanel pnlPrincipal) throws SQLException {
        super(pnlPrincipal);
        vista = new VGenerarDocumentos();
        modelo = new DefaultTableModel(new String[]{"No. Control", "ApePat", "ApeMat", "Nombres"}, 0);

        vista.btnGenerar.addActionListener(this);
        vista.tabla.getSelectionModel().addListSelectionListener(this);

        consultarAlumnos();
        repintarPanelPrincipal(vista);
    }

    private void consultarAlumnos() throws SQLException {
        lista = Alumno.todos();

        for (Alumno a : lista) {
            modelo.addRow(new Object[]{a.idAlumno, a.apellidoPatA, a.apellidoMatA, a.nombre});
        }

        vista.tabla.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGenerar) {
            try {
                manejaEventoGenerar();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorGenerarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        alumnoSeleccionado = (int) modelo.getValueAt(vista.tabla.getSelectedRow(), 0);
        try {
            manejaEventoDatosCompletos(alumnoSeleccionado);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoGenerar() throws IOException {
        if (vista.cbCertificado.isSelected()) {
            generarCertificado();
        }

        if (vista.cbDiploma.isSelected()) {
            generarDiploma();
        }
    }

    private void manejaEventoDatosCompletos(int alumnoSeleccionado) throws SQLException {
        alumno = Alumno.buscarPrimero("idAlumno", "" + alumnoSeleccionado);
        grupo = Grupo.buscarPrimero("idGrupo", "" + alumno.idGrupoA);
        horario = Horario.buscarPrimero("idHorario", grupo.idHorarioG);

        vista.lblNombre.setText(alumno.nombre);
        vista.lblNumero.setText(alumno.telefono);
        vista.lblCurso.setText(grupo.curso);
        vista.lblHorario.setText(darHorario(horario));

        vista.pnlDatos.setVisible(true);
    }

    private String darHorario(Horario horario) {
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

    private void generarDiploma() throws FileNotFoundException, IOException {
        Calendar fecha = new GregorianCalendar();
        String fechaStr = fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR);
        PdfDocument pdf = new PdfDocument(new PdfWriter("archivos/diploma.pdf"));
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 30, 35, 30);

        document.add(new Paragraph("INFOCOMING\n"
                + "\n"
                + "\n"
                + "DIPLOMA\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "A " + alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA + "\n"
                + "Por haber concluido con éxito el curso de" + grupo.curso + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "ATentamente\n"
                + "EL DIRECTOR DEL INFOCOMING"));
        document.close();
        Desktop.getDesktop().open(new File("archivos/diploma.pdf"));
    }

    private void generarCertificado() throws IOException {

        Calendar fecha = new GregorianCalendar();
        String fechaStr = fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR);
        PdfDocument pdf = new PdfDocument(new PdfWriter("archivos/certificado.pdf"));
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 30, 35, 30);

        document.add(new Paragraph("\n\n\n\n\nINFOCOMING\n"
                + "\n"
                + "CONSTANCIA DE ESTUDIOS\n"
                + "\n"
                + "\n"
                + "\n"
                + "A QUIEN CORRESPONDA:\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "El INFOCOMING hace CONSTAR que el ciudadano " + alumno.nombre + " " + alumno.apellidoPatA + " " + alumno.apellidoMatA+ ",con matricula " + alumno.idAlumno + " ,se encuentra estudiando el nivel " + grupo.curso + "\n"
                + "\n"
                + "Para los fines que al interesado le convengan, se extiende la presente el día    " + fechaStr + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "ATentamente\n"
                + "EL DIRECTOR DEL INFOCOMING"));
        document.close();
        Desktop.getDesktop().open(new File("archivos/certificado.pdf"));
    }

}
