package org.adsoftware.modulogrupo.manejadores;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.adsoftware.entidades.Alumno;
import org.adsoftware.entidades.Grupo;
import org.adsoftware.entidades.Horario;
import org.adsoftware.entidades.Personal;
import org.adsoftware.entidades.Usuario;
import org.adsoftware.moduloalumno.interfaces.VRegistrarEvaluacion;
import org.adsoftware.moduloalumno.manejadores.ManejadorRegistrarEvaluacion;
import org.adsoftware.modulogrupo.interfaces.VVisualizarGrupos;
import org.adsoftware.superclases.Manejador;
import org.adsoftware.utilidades.Galeria;

public class ManejadorVisualizarGrupos extends Manejador implements ActionListener, ListSelectionListener {

    private VVisualizarGrupos vistaGrupos;

    private DefaultTableModel modelo;
    private Grupo grupo;
    private Personal maestro;
    private Horario horario;
    private Usuario usuario;
    private ArrayList<Grupo> listaGrupos;
    private JPanel pnlPrincipal;
    private int grupoSeleccionado = 0;

    public ManejadorVisualizarGrupos(boolean esCoordinador, JPanel panelPrincipal, Usuario usuario) throws SQLException {
        super(panelPrincipal);
        this.pnlPrincipal = panelPrincipal;
        this.vistaGrupos = new VVisualizarGrupos(esCoordinador);
        this.usuario = usuario;
        modelo = new DefaultTableModel(new Object[]{"Clave", "Curso", "Horario", "NumEstudiantes"}, 0);

        vistaGrupos.btnRegistrarEva.addActionListener(this);
        vistaGrupos.btnModificar.addActionListener(this);
        vistaGrupos.btnEliminar.addActionListener(this);
        vistaGrupos.btnImprimir.addActionListener(this);
        vistaGrupos.tabla.getSelectionModel().addListSelectionListener(this);
        vistaGrupos.datos.setVisible(false);

        consultarGrupos();

        repintarPanelPrincipal(vistaGrupos);
    }

    private void consultarGrupos() throws SQLException {
        listaGrupos = Grupo.todos();

        for (Grupo g : listaGrupos) {
            Horario h = Horario.buscarPrimero("idHorario", g.idHorarioG);
            modelo.addRow(new Object[]{g.idGrupo, g.curso, darHorario(h), g.numEstudiantes});
        }

        vistaGrupos.tabla.setModel(modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vistaGrupos.btnRegistrarEva) {
                manejaEventoRegistrarEvaluacion();
            } else if (e.getSource() == vistaGrupos.btnImprimir) {
                manejaEventoImprimir();
            } else if (e.getSource() == vistaGrupos.btnModificar) {
                manejaEventoModificar();
            } else if (e.getSource() == vistaGrupos.btnEliminar) {
                manejaEventoEliminar();
            }
        } catch (IOException ex) {
            Logger.getLogger(ManejadorVisualizarGrupos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejaEventoImprimir() throws FileNotFoundException, IOException, SQLException {
        ArrayList<Alumno> lista = Alumno.buscar("idGrupoA", "" + grupo.idGrupo);
        //Lineas fundamentales para generar un PDF
        PdfDocument pdf = new PdfDocument(new PdfWriter("archivos/listaAsistencia.pdf"));
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(40, 30, 35, 30);

        Table pdfTable = new Table(new float[]{10, 30,
            5, 5, 5, 5, 5, 5, 5});
        pdfTable.setWidthPercent(100);

        pdfTable.addHeaderCell(new Cell().add("No."));
        pdfTable.addHeaderCell(new Cell().add("Nombre          "));

        for (int i = 0; i < 7; i++) {
            pdfTable.addHeaderCell(new Cell().add("--"));
        }

        for (int i = 0; i < lista.size(); i++) {
            Alumno a = lista.get(i);
            pdfTable.addCell("" + (i + 1));
            pdfTable.addCell(a.apellidoPatA + " " + a.apellidoMatA + " " + a.nombre);
            for (int j = 0; j < 7; j++) {
                pdfTable.addCell("\n");
            }
        }

        document.add(new Paragraph("Grupo: " + grupo.idGrupo));
        document.add(new Paragraph("Horario: " + darHorario(horario)));
        document.add(new Paragraph("Curso: " + grupo.curso));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Maestro asignado: " + maestro.nombreP + " " + maestro.apellidoPatP + " " + maestro.apellidoMatP));
        document.add(new Paragraph("\n"));
        document.add(pdfTable);

        document.close();

        Desktop.getDesktop().open(new File("archivos/listaAsistencia.pdf"));
    }

    private void manejaEventoRegistrarEvaluacion() throws SQLException {
        new ManejadorRegistrarEvaluacion(pnlPrincipal, grupo);
    }

    private void manejaEventoModificar() throws SQLException {
        new ManejadorModificarGrupo(grupo, horario);
        actualizarVista(false);
    }

    private void manejaEventoDatosCompletos(int id) throws SQLException {
        grupo = Grupo.buscarPrimero("idGrupo", "" + id);
        horario = Horario.buscarPrimero("idHorario", grupo.idHorarioG);
        maestro = Personal.buscarPrimero("idPersonal", horario.idPersonalH + "");

        vistaGrupos.lblIcono.setIcon(new ImageIcon(getClass().getResource(Galeria.GRUPO128_ICON)));
        vistaGrupos.lblCurso.setText(grupo.curso);
        vistaGrupos.lblHorario.setText(darHorario(horario));
        vistaGrupos.lblMaestro.setText(maestro.nombreP + " " + maestro.apellidoPatP + " " + maestro.apellidoMatP);

        vistaGrupos.datos.setVisible(true);
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

    @Override
    public void valueChanged(ListSelectionEvent e) {
        grupoSeleccionado = (int) modelo.getValueAt(vistaGrupos.tabla.getSelectedRow(), 0);
        try {
            manejaEventoDatosCompletos(grupoSeleccionado);
        } catch (SQLException ex) {
            Logger.getLogger(ManejadorVisualizarGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarVista(boolean eliminado) throws SQLException {
        vistaGrupos.tabla.getSelectionModel().removeListSelectionListener(this);
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        consultarGrupos();
        vistaGrupos.datos.setVisible(false);
        if (!eliminado) {
            manejaEventoDatosCompletos(grupoSeleccionado);
        }
        vistaGrupos.tabla.getSelectionModel().addListSelectionListener(this);
    }

    private void manejaEventoEliminar() throws SQLException {
        if (grupo.numEstudiantes == 0) {
            new ManejadorBajaGrupo(usuario, grupo, horario);
            actualizarVista(true);
        } else {
            NotificationManager.showNotification(vistaGrupos.btnEliminar,
                    "No sé puede realizar la siguiente opción \n"
                    + "pues el grupo tiene alumnos registrados.", NotificationIcon.warning.getIcon());
        }
    }

}
